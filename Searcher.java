package SearchEngine;


import java.util.HashMap;
import java.util.Map;

/**
 * This class perfroms the search query in the corpus process
 */
public class Searcher {

    ReadFile r;
    Parse p;
    HashMap<String,SearchEngine.Term> queryTerms;
    Ranker rank;
    String pathToDocFile="D:\\PartB+"+"DOCS.txt";
    public static Map<String,TermDic> m_Dictionary;
    //TODO need to change the path to a file inside the project

    /**
     * this is the constructor get the query and perform parse on it to insert to queryTerms
     * @param query- the string received from the user
     * @param stemming to perform stemming or not
     * @param expand to perform expansion to the query or not
     * @param isDoc to perform doc analyze or not
     */
    public Searcher(String query,boolean stemming, boolean expand,boolean isDoc) {
        p= new Parse(r.stopword, stemming);//stemming instead of true
        p.parseDoc(query,true);
        queryTerms=  new HashMap<>(p.m_terms);
        rank = new Ranker();
    }
    private void getDocs(String term){

    }

    /**
     * this method compute the weight of each term in the query
     * @return the weight of the term in the query
     */
    private double computeWiQ(String term)
    {

        return 0.0;
    }

    /**
     * this method compute the weight of the term in a document
     * @return
     */
    private double computeWiD(String term, String doc)
    {

        return 0.0;
    }










}
