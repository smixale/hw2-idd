package idd.util;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

    public void createIndex() {
        try {
            System.out.println("Inizio indicizzazione...");

            long start = System.currentTimeMillis();

            // Directory dove verranno salvati gli indici Lucene
            Directory directory = FSDirectory.open(Paths.get("lucene-index"));

            // Analyzer personalizzato per gestire tokenizzazione e minuscole
            Analyzer analyzerCustom = CustomAnalyzer.builder()
                    .withTokenizer(WhitespaceTokenizerFactory.class)
                    .addTokenFilter(LowerCaseFilterFactory.class)
                    .addTokenFilter(WordDelimiterGraphFilterFactory.class)
                    .build();

            // Associazione campo → analyzer specifico
            Map<String, Analyzer> perFieldAnalyzers = new HashMap<>();
            perFieldAnalyzers.put("title", analyzerCustom);
            perFieldAnalyzers.put("content", new StandardAnalyzer(new Stopwords().getStopWords()));

            // Wrapper per gestire analyzer diversi per campo
            Analyzer perFieldAnalyzer = new PerFieldAnalyzerWrapper(analyzerCustom, perFieldAnalyzers);

            // Configurazione dell’IndexWriter
            IndexWriterConfig config = new IndexWriterConfig(perFieldAnalyzer);
            config.setCodec(new SimpleTextCodec()); // formato leggibile a testo (per debugging)

            IndexWriter writer = new IndexWriter(directory, config);

            // Recupera i documenti (parsing dei file TXT)
            List<Document> docs = Docs.analizzaFile("D:\\workspace\\ingegneria_dei_dati\\motore_di_ricerca_lucene\\motore\\targetFiles");

            System.out.println("Indicizzazione dei file trovati...");

            for (Document doc : docs) {
                writer.addDocument(doc);
            }

            writer.commit();
            writer.close();

            long end = System.currentTimeMillis();
            long elapsed = end - start;

            System.out.println("Indicizzazione completata!");
            System.out.println("Tempo impiegato: " + elapsed + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
