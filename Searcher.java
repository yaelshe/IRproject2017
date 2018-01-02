package SearchEngine;


import java.util.HashMap;
import java.util.Map;

public class Searcher {

    ReadFile r;
    HashMap<String,SearchEngine.Term> queryTerms;

    public Searcher(String query,boolean stemming, boolean expand,boolean isDoc) {
        Parse p= new Parse(r.stopword, true);//stemming instead of true
        p.parseDoc(query);
        queryTerms=  new HashMap<>(p.m_terms);
        Ranker rank = new Ranker();
    }
}
