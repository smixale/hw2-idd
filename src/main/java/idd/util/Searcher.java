package idd.util;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import idd.util.Oggetti.File;
import idd.util.Oggetti.Output;

public class
Searcher {

    private long totalDocs;

    /*crea un QueryParser per il campo specificato (title o content)*/
    private QueryParser getQueryParser(String field) throws IOException {
        Analyzer analyzerCustom = CustomAnalyzer.builder()
                .withTokenizer(WhitespaceTokenizerFactory.class)
                .addTokenFilter(LowerCaseFilterFactory.class)
                .addTokenFilter(WordDelimiterGraphFilterFactory.class)
                .build();

        if (field.equals("titolo") || field.equals("t")) {
            // Analyzer semplice per i titoli
            return new QueryParser("content", analyzerCustom);
        }else{
            if (field.equals("contenuto") || field.equals("c")) {
                // Analyzer con stopwords per il testo completo
                return new QueryParser("content", new StandardAnalyzer(new Stopwords().getStopWords()));
            } else {
                throw new IllegalArgumentException("Campo non valido. Usa 'title' o 'content'.");
            }
        }

    }

    /*funzione che prende in input tutto il necessario per eseguire il parsing in base al campo p fornito sull'indice, restituisce in output una lista di oggetti risposta rappresentabile tutti i risultati ottenuti dal parsing in questione*/
    private void eseguiParsing(String p, IndexSearcher searcher, String q, Output o) throws Exception {
        int relevantDocumentsFound = 0;
        QueryParser parser = getQueryParser(p);                 //scelgo il tipo di parser

        Query query = parser.parse(q);                          //eseguo il parsing della query fornita dall'utente

        long startTime = System.nanoTime();                     //prendo il tempo iniziale

        /*eseguo il parsing e raccolgo i risultati*/
        TopDocs results = searcher.search(query, 10);
        ScoreDoc[] hits = results.scoreDocs;

        long endTime = System.nanoTime();                   //prendo il tempo finale
        double end = (endTime - startTime) / 1_000_000.0;

        System.out.println("====================================================");

        /*itero per ogni risultato ottenuto dal parsing*/
        for (ScoreDoc hit : hits) {

            Document doc = searcher.doc(hit.doc);

            System.out.println("Titolo file: " + doc.get("title"));

            String content = doc.get("content");
            if (content != null) {
                /*mostra solo un estratto*/
                System.out.println("Contenuto: " + content.substring(0, Math.min(300, content.length())) + "....");

                /*considero rilevanti tutti e soli i file che hanno nel proprio contenuto 3 o più occorrenze della query cercata*/
                if (this.contaOccorrenze(content, q) >= 3) {
                    relevantDocumentsFound++;
                }
            }else{
                content = "Nessun contenuto trovato";
            }

                if(p.equals("titolo")){
                    o.getOutTitoli().add(new File(doc.get("title"), content,hit.score));
                }else{
                    o.getOutContenuto().add(new File(doc.get("title"), content,hit.score));
                }

                System.out.println("\nScore: " + hit.score);
                System.out.println("====================================================");
            }

        //calcolo precision/recall
        double precision = (hits.length == 0) ? 0 : (double) relevantDocumentsFound / hits.length;

        if(p.equals("titolo")){
            o.setTempoTitolo(end);
            o.setPrecisionTitolo(precision);
        }else{
            o.setTempoContenuto(end);
            o.setPrecisionContenuto(precision);
        }

        System.out.println("\nStatistiche:");

        System.out.println("documenti rilevanti trovati: " + relevantDocumentsFound);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: (non calcolabile senza conoscere a priori il numero di documenti rilevanti esistenti nella pool)");
    }


    public Output searchIndex(String q) {
            Output out = new Output(q);                  //variabile che contiene le due colonne ottenute da la doppia ricerca
            
        try 
        {
            String indexPath = "lucene-index";  // directory dove è stato creato l’indice
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);

            totalDocs = reader.numDocs();
            
            /*eseguoi il parsing dei file in base al titolo e salvo il risultato nel mio output*/
            this.eseguiParsing("titolo", searcher, q, out);

            /*eseguoi il parsing dei file in base al contenuto e salvo il risultato nel mio output*/
            this.eseguiParsing("contenuto", searcher, q, out);

            out.setValido(true);         //contrassegno l'output come valido
            return out;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    private static int contaOccorrenze(String content, String s) {
        int occorrenze = 0;
        int i = 0;

        while ((i = content.indexOf(s, i)) != -1) {
            occorrenze++;
            i += s.length();
        }

        return occorrenze;
    }
}

