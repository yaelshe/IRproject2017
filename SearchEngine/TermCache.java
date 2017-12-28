package SearchEngine;

import java.io.Serializable;

public class TermCache implements Serializable {
    String term;
    String favDocs;
    int pointer;

    public TermCache(String term, String favDocs, int pointer) {
        this.term = term;
        this.favDocs = favDocs;
        this.pointer = pointer;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getFavDocs() {
        return favDocs;
    }

    public void setFavDocs(String favDocs) {
        this.favDocs = favDocs;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }


}
