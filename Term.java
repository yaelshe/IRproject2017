package SearchEngine;

import java.util.Map;
import java.util.*;

public class Term
{
    public String _term;
    public int numOfDocIDF;//amount of docs the term appear in
    public Map<String,Integer> docs;//list for the numbers of the
    // documents the word is in and number of appearances in each
    private int totalApperance;


    public Term( String term, Map<String, Integer> docs) {
        _term=term;
        this.docs = new HashMap<>(docs);
        this.numOfDocIDF =1;
        totalApperance=1;
    }
    @Override
    public String toString()
    {//term #numberofDocs &docname-number docname-number....
        String termStr="";
        termStr=this._term+" ";
        termStr=termStr+"#"+this.getnumOfDocIDFString()+" ";
        termStr=termStr+"&"+this.get_docs();
        termStr=termStr+"["+this.totalApperance+"]";
        return termStr;
    }

    public String get_term() {
        return _term;
    }

    public String getnumOfDocIDFString()
    {
       // String num="";
        //List sortedKeys=new ArrayList(docs.values());
        //Collections.sort(sortedKeys);
        String num=Integer.toString(numOfDocIDF);
        return num;
    }

    public int getNumOfDocIDF() {
        return numOfDocIDF;
    }

    public int getTotalApperance() {
        return totalApperance;
    }

    public String get_docs(){
        String str="";
        for (String docnum: docs.keySet())
        {
            String key =docnum;
            String value =docs.get(docnum).toString();
            str=str+"{"+key + "-" + value+"} ";
            //System.out.print(key + "-" + value+" ");
        }

        return str;
    }

    public void setTotalApperance(int totalApperance) {
        this.totalApperance = this.totalApperance+totalApperance;
    }
    public void setNumOfDocIDF(int num){
        this.numOfDocIDF=this.numOfDocIDF+num;
    }
    public void addToDocs(String docnum)
    {
        if(docs.containsKey(docnum))
        {
            //docs.put(currDoc, m_terms.get(str).docs.get(currDoc) + 1);
        }
        else{

        }
    }
}
