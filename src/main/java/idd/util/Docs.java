package idd.util;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

public class Docs {

    public static List<Document> analizzaFile(String directoryPath) throws IOException {
        List<Document> docs = new ArrayList<>();

        /*carica tutti i file .txt dalla directory specificata */
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null) {
            throw new IOException("La directory non è accessibile o non contiene file di tipo .txt.");
        }

        for (File file : files) {

            String contenuto = Files.readString(file.toPath(), StandardCharsets.UTF_8);           //leggo tutto il contenuto del file come stringa
            String nomeFile = file.getName();                                                     //usa il nome del file (senza estensione) come titolo

            String title = nomeFile.endsWith(".txt")
                    ? nomeFile.substring(0, nomeFile.length() - 4)
                    : nomeFile;

            Document document = new Document();                                                     //crea un nuovo documento Lucene
            document.add(new TextField("title", title, Field.Store.YES));                           //titolo del file
            document.add(new TextField("content", contenuto, Field.Store.YES));                     //contenuto testuale del file
            docs.add(document);                                                                     //aggiunge il documento alla lista
        }

        return docs;
    }
}
