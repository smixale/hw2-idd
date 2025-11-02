package idd.util.Oggetti;

import java.util.ArrayList;
import java.util.List;

public class Output {
    String input;
    List <Risposta> outTitolo = new ArrayList<>();;
    List <Risposta> outContenuto = new ArrayList<>();;
    double precisionTitolo = 0;
    double precisionContenuto = 0;

    public Output (String input){
        this.input = input;
    }

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

    public String getInput(){
        return this.input;
    }

    public double getPrecisionTitolo(){
        return this.precisionTitolo;
    }

    public double getPrecisionContenuto(){
        return this.precisionContenuto;
    }
}
