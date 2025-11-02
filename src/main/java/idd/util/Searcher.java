package idd.util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

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

import idd.util.Oggetti.Output;
import idd.util.Oggetti.Risposta;

public class
Searcher {

    private long totalDocs;

    /** 
     * Crea un QueryParser per il campo specificato (title o content)
     */
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

    public Risposta searchIndex(String quer) {
        try 
        {
            String indexPath = "lucene-index";  // directory dove è stato creato l’indice
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);

            totalDocs = reader.numDocs();

            Scanner scanner = new Scanner(System.in);               //inizializzazione scanner

            
            QueryParser parser = getQueryParser("titolo");         //scelgo il tipo di parser
            
            System.out.print("Inserisci la tua query: ");
            String q = scanner.nextLine().trim();               //leggo l'input dell'utente
            Output out = new Output(q);                  //variabile che contiene le due colonne ottenute da la doppia ricerca
            Query query = parser.parse(q);              //eseguo il parsing della query fornita dall'utente

            /*inizio la ricerca e prendo il tempo iniziale*/
            System.out.println("\nEsecuzione della ricerca...");
            long startTime = System.nanoTime();

            TopDocs results = searcher.search(query, 10);
            ScoreDoc[] hits = results.scoreDocs;

            /*verifico e stampo il tempo impiegato per la ricerca*/
            long endTime = System.nanoTime();
            double durationMs = (endTime - startTime) / 1_000_000.0;
            System.out.println("Trovati " + hits.length + " documenti in " + durationMs + " ms.\n");

            int relevantDocumentsFound = 0;
            int contatore = 0;                                      //contatore utile a visualizzare il numero di risultati ottenuti

            System.out.println("====================================================");
            // Mostra risultati
            for (ScoreDoc hit : hits) {
                contatore++;
                System.out.println("Risulttato: " + contatore);
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
                }

                System.out.println("\nScore: " + hit.score);
                System.out.println("====================================================");
            }

            //calcolo precision/recall
            double precision = (hits.length == 0) ? 0 : (double) relevantDocumentsFound / hits.length;


            System.out.println("\nStatistiche:");

            System.out.println("documenti rilevanti trovati: " + relevantDocumentsFound);
            System.out.println("Precision: " + precision);
            System.out.println("Recall: (non calcolabile senza conoscere a priori il numero di documenti rilevanti esistenti nella pool)");

            reader.close();

            return new Risposta(null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    private void eseguiParsing(Output o, QueryParser parser ){

    }
}

