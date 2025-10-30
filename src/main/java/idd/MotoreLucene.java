package idd;

import java.util.Scanner;

import idd.util.Indexer;
import idd.util.Searcher;
import idd.util.Stats;

public class MotoreLucene {

    private static Indexer indicizzatore = new Indexer();
    private static Searcher searcher = new Searcher();

    public static void main(String[] args) {

        //crea l’indice a partire dai file TXT
        indicizzatore.createIndex();

        //mostra statistiche di base sull’indice
        new Stats().statsIndex();

        //loop di ricerca interattivo
        Scanner scanner = new Scanner(System.in);

        while (true) {
            searcher.searchIndex();

            System.out.print("Vuoi continuare la ricerca? Y/N: ");
            String exit = scanner.nextLine();

            if (exit.equalsIgnoreCase("N")) {
                System.out.println("Fine ricerca.");
                break;
            }
        }

        scanner.close();
    }
}
