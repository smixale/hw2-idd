package idd.util;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Stats {

    public void statsIndex() {
        String indexPath = "lucene-index"; // directory dove è stato salvato l’indice

        try {
            Directory directory = FSDirectory.open(Paths.get(indexPath));
            IndexReader reader = DirectoryReader.open(directory);

            // Numero totale di documenti indicizzati
            int numDocs = reader.numDocs();
            System.out.println("Numero di documenti indicizzati: " + numDocs);

            // Conteggio termini nei campi principali
            System.out.println("\nConteggio dei termini per ciascun campo:");

            for (LeafReaderContext leafContext : reader.leaves()) {
                LeafReader leafReader = leafContext.reader();

                for (FieldInfo fieldInfo : leafReader.getFieldInfos()) {
                    String fieldName = fieldInfo.name;

                    // Considera solo i campi che ci interessano
                    if (!fieldName.equals("title") && !fieldName.equals("content")) continue;

                    Terms terms = leafReader.terms(fieldName);
                    if (terms != null) {
                        TermsEnum termsEnum = terms.iterator();
                        int termCount = 0;

                        while (termsEnum.next() != null) {
                            termCount++;
                        }

                        System.out.println("Campo: " + fieldName + " → " + termCount + " termini indicizzati");
                    } else {
                        System.out.println("Campo: " + fieldName + " → Nessun termine trovato.");
                    }
                }
            }

            reader.close();
            directory.close();

        } catch (IOException e) {
            System.err.println("Errore durante la lettura dell’indice Lucene:");
            e.printStackTrace();
        }
    }
}

