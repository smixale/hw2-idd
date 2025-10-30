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

public class Searcher {

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

        if (field.equals("title") || field.equals("t")) {
            // Analyzer semplice per i titoli
            return new QueryParser("content", analyzerCustom);
        }else{
            if (field.equals("content") || field.equals("c")) {
                // Analyzer con stopwords per il testo completo
                return new QueryParser("content", new StandardAnalyzer(new Stopwords().getStopWords()));
            } else {
                throw new IllegalArgumentException("Campo non valido. Usa 'title' o 'content'.");
            }
        }

    }

    public void searchIndex() {
        try {
            String indexPath = "lucene-index";  // directory dove è stato creato l’indice
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);

            totalDocs = reader.numDocs();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Su quale campo vuoi eseguire la ricerca (title/content)? ");
            String field = scanner.nextLine().trim();

            // Ottieni il parser per il campo scelto
            QueryParser parser = getQueryParser(field);

            // Input della query utente
            System.out.print("Inserisci la tua query: ");
            String q = scanner.nextLine().trim();

            // Parsing della query e ricerca
            Query query = parser.parse(q);

            System.out.println("\nEsecuzione della ricerca...");
            long startTime = System.nanoTime();

            TopDocs results = searcher.search(query, 10);
            ScoreDoc[] hits = results.scoreDocs;

            long endTime = System.nanoTime();
            double durationMs = (endTime - startTime) / 1_000_000.0;

            System.out.println("Trovati " + hits.length + " documenti in " + durationMs + " ms.\n");

            int relevantDocumentsFound = 0;

            int contatore = 0;              //contatore utile a visualizzare il numero di risultati ottenuti
            System.out.println("====================================================");
            // Mostra risultati
            for (ScoreDoc hit : hits) {
                contatore++;
                System.out.println("Risulttato: " + contatore);
                Document doc = searcher.doc(hit.doc);

                System.out.println("Titolo file: " + doc.get("title"));

                String content = doc.get("content");
                if (content != null) {
                    // Mostra solo un estratto
                    System.out.println("Contenuto: " + content.substring(0, Math.min(300, content.length())) + "...");
                }

                System.out.println("Score: " + hit.score);
                System.out.println("====================================================");

                //se i documenti avessero un campo 'relevant' si potrebbe calcolare precision e recall
                String relevantValue = doc.get("relevant");
                boolean isRelevant = relevantValue != null && Boolean.parseBoolean(relevantValue);
                if (isRelevant) {
                    relevantDocumentsFound++;
                }
            }

            //calcolo precision/recall
//            double precision = (hits.length == 0) ? 0 : (double) relevantDocumentsFound / hits.length;
//            double recall = (totalDocs == 0) ? 0 : (double) relevantDocumentsFound / totalDocs;

            double precision = (double) relevantDocumentsFound/((double) hits.length + (double) relevantDocumentsFound);
            double recall = (double) relevantDocumentsFound/ (double) hits.length;

            System.out.println("\nStatistiche:");

            System.out.println("relevantDocumentsFound: " + relevantDocumentsFound);
            System.out.println("hits.length: " + hits.length);

            System.out.println("Precision: " + precision);
            System.out.println("Recall: " + recall);

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

