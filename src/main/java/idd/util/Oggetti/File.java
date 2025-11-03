package idd.util.Oggetti;

/*classe che immagazzina titolo e contenuto di un file*/
public class File {
    private String titolo;
    private String contenuto;


    public File (String t, String c){
        this.titolo = t;
        this.contenuto = c;
    }

    public String getTitolo(){
        return titolo;
    }

    public String getContenuto(){
        return contenuto;
    }

    public void setTitolo(String t){
        this.titolo = t;
    }

    public void setContenuto(String c){
        this.contenuto = c;
    }
}


