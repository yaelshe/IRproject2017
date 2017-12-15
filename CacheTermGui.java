package SearchEngine;

public class CacheTermGui {
    String term;
    String docsApper;



    public CacheTermGui(String term,String favsdocss) {

        this.term = term;
        this.docsApper=favsdocss;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
