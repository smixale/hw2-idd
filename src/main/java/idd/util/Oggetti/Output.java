package idd.util.Oggetti;

import java.util.ArrayList;
import java.util.List;

import idd.util.string;

public class Output {
    string input;
    List <Risposta> outTitolo = new ArrayList<>();;
    List <Risposta> outContenuto = new ArrayList<>();;
    double precisionTitolo = 0;
    double precisionContenuto = 0;

    public List <Risposta> getOutTitoli(){
        return outTitolo;
    }

    public void addOutTitoli (Risposta t){
        outTitolo.add(t);
    }

    public List <Risposta> getOutContenuto(){
        return outContenuto;
    }

    public void addOutContenuto (Risposta c){
        outContenuto.add(c);
    }
}
