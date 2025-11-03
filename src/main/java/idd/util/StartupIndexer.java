package idd.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupIndexer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Indexer indexer = new Indexer();
        indexer.createIndex();

        Stats stats = new Stats();
        stats.statsIndex();

        System.out.println("Indicizzazione completata e spring boot avviato");
    }
}
