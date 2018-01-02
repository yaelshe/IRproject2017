package SearchEngine;

import java.util.*;
        import java.io.BufferedReader;
        import java.lang.*;
        import java.io.InputStreamReader;

/**
 * This class creates an object for a Document from the corpus
 */
public class Document
{
    public String text;
    public int max_tf;//number of appearances most frequent term
    private final int docLength;
    public String mostCommWord;
    public final String id ;
    //String date ;

    /**
     * this is the constructor that initialize the fields for the document object.
     * @param id- name of document
     * @param text- text of the document
     * @param max_tf- number of appearances most ferquent term
     * @param docLength- the length of document by string of the text's size
     * @param mostCommWord- the most common string in the text
     */
    public Document(String id,String text, int max_tf, int docLength, String mostCommWord) {
        this.text = text;
        this.max_tf = max_tf;
        this.docLength = docLength;
        this.mostCommWord = mostCommWord;
        this.id=id;
        //this.title = title;
        //this.date=date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMax_tf() {
        return max_tf;
    }

    public void setMax_tf(int max_tf) {
        this.max_tf = max_tf;
    }

    public int getDocLength() {
        return docLength;
    }

    public String getMostCommWord() {
        return mostCommWord;
    }

    public void setMostCommWord(String mostCommWord) {
        this.mostCommWord = mostCommWord;
    }

    public String getId() {
        return id;
    }
}
