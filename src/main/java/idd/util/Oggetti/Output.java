package idd.util.Oggetti;

import java.util.ArrayList;
import java.util.List;


/*classe che immagazzina l'output ottenuto dal parsing su titolo e contenuto*/
public class Output {

    String input;                                               //variabile che salva l'input fornito dall'utente   
    List <File> outTitolo = new ArrayList<>();;                 //lista di file ottenuta dal parsing sul titolo
    List <File> outContenuto = new ArrayList<>();;              //lista di file ottenuta dal parsing sul contenuto
    double tempoTitolo = 0;                                     //tempo impiegato per il parsing sul titolo
    double tempoContenuto = 0;                                  //tempo impiegato per il parsing sul contenuto
    double precisionTitolo = 0;                                 //precisione ottenuta dal parsing sul titolo
    double precisionContenuto = 0;                              //precisione ottenuta dal parsing sul contenuto
    Boolean valido = false;                                     //variabile che indica se l'output è valido (se la ricerca è andata a buon fine)

    public Output (String input){
        this.input = input;
    }

    public List <File> getOutTitoli(){
        return outTitolo;
    }

    public void addOutTitoli (File t){
        outTitolo.add(t);
    }

    public List <File> getOutContenuto(){
        return outContenuto;
    }

    public void addOutContenuto (File c){
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

    public void setValido(Boolean b){
        this.valido = b;
    }
}
