package idd.util;

import java.util.Arrays;

import org.apache.lucene.analysis.CharArraySet;

public class Stopwords {

    /*definisco tutte le possibili stopwords per file txt*/
String[] stopwords = {
    "","il", "lo", "la", "i", "gli", "le", "un", "una", "uno", "l'",
    "di", "a", "da", "in", "su", "con", "per", "tra", "fra",
    "e", "ed", "o", "oppure", "ma", "anche", "come", "se", "perché", "mentre", "dove", "quando", "dunque",
    "che", "chi", "cui", "quale", "quelli", "quelle", "questo", "questa", "quello", "quella",
    "noi", "voi", "loro", "io", "tu", "egli", "ella", "esso", "essa", "essi", "esse",
    "mi", "ti", "si", "ci", "vi", "ne", "li", "le", "gli",
    "essere", "è", "sono", "era", "erano", "stato", "avere", "ha", "hanno", "può", "puoi",
    "possono", "deve", "devono", "fare", "fatto",
    "molto", "poco", "bene", "male", "sempre", "mai", "oggi", "ieri", "domani",
    "qui", "lì", "là", "così", "allora", "ancora", "solo", "già", "proprio"
};


    private CharArraySet stopWords = new CharArraySet(Arrays.asList(stopwords), true);

    public CharArraySet getStopWords() {
        return stopWords;
    }

    public void setStopWords(CharArraySet stopWords) {
        this.stopWords = stopWords;
    }
}
