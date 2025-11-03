package idd.util.Oggetti;

/*classe che immagazzina titolo e contenuto di un file*/
public class File {
    private String titolo;
    private String contenuto;
    private float score = 0;


    public File (String t, String c, float s){
        this.titolo = t;
        this.contenuto = c;
        this.score = s;
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

    public float getScore(){
        return this.score;
    }

    public void setScore(float s){
        this.score = s;
    }
}


