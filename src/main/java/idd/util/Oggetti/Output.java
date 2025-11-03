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
    int fileRilevantiTitolo = 0;
    int fileRilevantiContenuto = 0;
    Boolean valido = false;                                     //variabile che indica se l'output è valido (se la ricerca è andata a buon fine)

    public Output (String input){
        this.input = input;
    }

    public List <File> getOutTitolo(){
        return this.outTitolo;
    }

    public void setOutTitolo(List<File> l){
        this.outTitolo = l;
    }

    public void addOutTitolo (File t){
        this.outTitolo.add(t);
    }

    public List <File> getOutContenuto(){
        return this.outContenuto;
    }

    public void setOutContenuto(List<File> l){
        this.outContenuto = l;
    }

    public void addOutContenuto (File c){
        this.outContenuto.add(c);
    }

    public String getInput(){
        return this.input;
    }

    public double getPrecisionTitolo(){
        return this.precisionTitolo;
    }

    public void setPrecisionTitolo(double p){
        this.precisionTitolo = p;
    }

    public double getPrecisionContenuto(){
        return this.precisionContenuto;
    }

    public void setPrecisionContenuto(double p){
        this.precisionContenuto = p;
    }

    public double getTempoTitolo(){
        return this.tempoTitolo;
    }

    public void setTempoTitolo(double t){
        this.tempoTitolo = t;
    }

    public double getTempoContenuto(){
        return this.tempoContenuto;
    }

    public void setTempoContenuto(double t){
        this.tempoContenuto = t;
    }

    public int getFileRilevantiTitolo (){
        return this.fileRilevantiTitolo;
    }

    public void setFileRilevantiTitolo (int f){
        this.fileRilevantiTitolo = f;
    }

    public int getFileRilevantiContenuto (){
        return this.fileRilevantiContenuto;
    }

    public void setFileRilevantiContenuto (int f){
        this.fileRilevantiContenuto = f;
    }

    public boolean getValido(){
        return this.valido;
    }

    public void setValido(Boolean b){
        this.valido = b;
    }
}
