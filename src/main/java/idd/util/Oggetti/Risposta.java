package idd.util.Oggetti;

import idd.util.string;

public class Risposta {
    private string titolo;
    private string contenuto;



    public Risposta (string t, string c){
        this.titolo = t;
        this.contenuto = c;
    }

    public string getTitolo(){
        return titolo;
    }

    public string getContenuto(){
        return contenuto;
    }

    public void setTitolo(string t){
        this.titolo = t;
    }

    public void setContenuto(string c){
        this.contenuto = c;
    }
}


