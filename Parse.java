package SearchEngine;

import javax.print.attribute.standard.DocumentName;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;


public class Parse
{
    private HashSet<String> m_StopWords;
    public Map<String,Term> m_terms;//******
    //private ArrayList<String> beforeTerms;
    private Map<String,Document>m_documents;
    private boolean doStem=true;
    private Stemmer stemmer;
    int maxfrequency;

    Map <String,String> Months=new HashMap<String, String>(){{
        put("january","01"); put("february","02"); put("march","03");put("april","04");put("may","05");
        put("june","06");put("july","07");put("august","08");put("september","09");put("october","10");
        put("november","11");put("december","12");put("jan", "01");put("feb","02");put("mar","03");
        put("apr","04");put("may","05");put("jun","06");put("jul","07");put("aug","08");put("sep","09");
        put("oct","10");put("nov","11");put("dec","03");}};
    String currDoc;


    public Parse(HashSet<String> m_StopWords, Map<String,Document>documents,boolean doStemming) {
        this.m_StopWords = m_StopWords;
        this.m_terms = new TreeMap<>();
        m_documents=new HashMap<>(documents);
        doStem=doStemming;
    }
    public void ParseAll()
    {
        //int i=0;
        for (Map.Entry<String,Document> entry : m_documents.entrySet()) {
            //  if(i<1){
            Document value = entry.getValue();
            currDoc = entry.getKey();
            //    System.out.println(currDoc+"curr doc in ParseAll");
            parseDoc(value);
            //       i++;
            // do stuff
            //    }
            //  else
            //   break;
        }

    }
    public void parseDoc(Document doc)
    {
        doc.getText().replaceAll("<(.*?)>"," ");
        //doc.getText().replaceAll("-"," ");
        String []termsDoc=doc.getText().split("\\s+");
        //System.out.println(currDoc+"i split the text of it");
        boolean flagCapital=false;
        int count;
        //for (int i=0;i<termsDoc.length;i++)
        for(int i=0;i<termsDoc.length;i++)
        {
            if(termsDoc[i].length()<1)
                continue;
            count=0;
            String SSS=termsDoc[i].replaceAll("-","");
            String curTerm= removeExtra(termsDoc[i]);
            StringBuilder sb=new StringBuilder(curTerm);
            if (curTerm.length()>0&&SSS.length()>0){
                if (!m_StopWords.contains(curTerm)){
                    if (isNumber(curTerm))/////
                    {
                        curTerm = numbersHandler(curTerm);// numb 25-27,21/05/1991,29-word done
                        if(curTerm.length()==0)
                            continue;
                        if (curTerm.charAt(curTerm.length()-1) == '%'|| (i+1<termsDoc.length && isPercent(removeExtra(termsDoc[i + 1])))) {
                            String mypercent = percent(curTerm);
                            if(mypercent.charAt(0)==' ')
                                mypercent.substring(1);
                            addToTerm(mypercent);
                            if (i+1<termsDoc.length){
                                if (isPercent(removeExtra(termsDoc[i + 1]))) {
                                    i++;
                                    //System.out.println(1);
                                }}
                        } else {
                            //System.out.println(isDate(termsDoc[i - 1], termsDoc[i + 1]));
                            String s1="";
                            String s3="";
                            if (i-1>=0)
                            {
                                s1 = removeExtra(termsDoc[i -1]);
                            }
                            if (i+1<termsDoc.length)
                            {
                                s3 = removeExtra(termsDoc[i + 1]);
                            }
                            if ((i+1<curTerm.length()||i-1>0)&&(isDate(s1, s3) && !curTerm.contains("."))) {//216
                                String s4="";
                                //System.out.println(termsDoc[i]);

                                if (i+2<termsDoc.length)
                                {
                                    s4 = removeExtra(termsDoc[i + 2]);
                                }
                                String mydate = dateHandler(s1, curTerm, s3,s4 );
                                if (mydate.length()>7)
                                {
                                    if (Months.containsKey(s1))
                                    {
                                        i++;
                                    }else
                                    {
                                        i=i+2;
                                    }
                                }else
                                {
                                    if (!Months.containsKey(s1))
                                    {
                                        i++;
                                    }
                                }
                                //System.out.println(mydate + "--2");
                                if(mydate.charAt(0)==' ')
                                    mydate.substring(1);
                                addToTerm(mydate);// to update i ....
                            } else {
                                //System.out.println(termsDoc[i] + "--3");
                                if(curTerm.charAt(0)==' ')
                                    curTerm.substring(1);
                                addToTerm(curTerm);
                            }

                        }

                    } else if(curTerm.length()>0){
                        //if (curTerm.contains("-")) {
                        //    //checks if the word has an - in the middle and
                        //    // save the word before and after it and also for twice -
                        //    curTerm=handleMakaf(curTerm);
                       // if(curTerm.charAt(0)==' ')
                         //   curTerm.substring(1);
                        //    addToTerm(curTerm);
                        //}
                        //System.out.println(termsDoc[i]+1);
                        if (Character.isUpperCase(curTerm.charAt(0))) {
                            //System.out.println(termsDoc[i]+1);
                            //check if the term capital letter up to phrase of 4 words.
                            String str1 = "", str2 = "", total = "";
                            if (i + 1 < termsDoc.length) {
                                str1 = termsDoc[i + 1];
                                if (i + 2 < termsDoc.length) {
                                    str2 = termsDoc[i + 2];
                                }
                            }
                            count=capitalTerm(curTerm, removeExtra(str1), removeExtra(str2));
                            //List<String> ph = capitalTerm(curTerm, removeExtra(str1), removeExtra(str2));
                            //flagCapital = true;// so we won't check again the same words !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            //count = ph.size();
                            /**if (ph.size() == 1) {
                             total = (ph.get(0).toLowerCase());
                             } else {
                             for (int j = 0; j < ph.size(); j++) {
                             addToTerm(ph.get(j).toLowerCase());
                             total = total + (ph.get(j).toLowerCase()) + " ";

                             }
                             }
                             ph.clear();
                             curTerm = total;
                             //  if (flagCapital) {
                             */
                            i = i + count - 1;
                            //count = 0;
                            //addToTerm(total);
                            // flagCapital = false;
                            // }
                        } else if (!m_StopWords.contains(curTerm.toLowerCase())) {
                            if (curTerm.contains("\'")) {
                                //checks if the word has an apostrphe in the middle and
                                // save the word without it and the part before it
                                curTerm.replaceAll(" ","");
                                curTerm = handleApostrophe(curTerm);
                            }

                            //System.out.println(termsDoc[i] + "--7");
                            // if (curTerm!= " "&&curTerm.length()>0) {
                            curTerm.replaceAll(" ","");

                            addToTerm(curTerm);
                            // }
                        }
                    }
                }}
        }
    }

    private void addToTerm(String str)
    {
        maxfrequency=m_documents.get(currDoc).getMax_tf();
        if((!str.equals(" "))&&str.length()>0) {
            str=str.toLowerCase();
            //System.out.println(str+"add to term");
            if (!m_StopWords.contains(str)) {
                //System.out.println(str);
                if(doStem)
                {
                    stemmer= new Stemmer();
                    stemmer.add(str.toCharArray(),str.length());
                    stemmer.stem();
                    str=stemmer.toString();
                }
                if (m_terms.containsKey(str)) {
                    //think what have to update
                    m_terms.get(str).setTotalApperance(1);//add 1 to total number of appernces in the entire magar
                    //m_documents.get(currDoc).docLength++;
                    if (m_terms.get(str).docs.containsKey(currDoc))//if i have the doc in the map of docs
                    {

                        m_terms.get(str).docs.put(currDoc, m_terms.get(str).docs.get(currDoc) + 1);//update
                        if(maxfrequency< m_terms.get(str).docs.get(currDoc))
                        {
                            maxfrequency= m_terms.get(str).docs.get(currDoc);
                            m_documents.get(currDoc).mostCommWord = str;
                        }

                    } else
                        {//currDoc isn't in the list of files for the term- first time doc
                        m_terms.get(str).docs.put(currDoc, 1);
                        m_terms.get(str).numOfDocIDF++;
                    }
                } else
                    {// first time term
                    Map<String, Integer> docss = new HashMap<>();//jkdj
                    docss.put(currDoc, 1);
                    Term newterm = new Term(str, docss);

                    m_terms.put(str, newterm);
                    m_documents.get(currDoc).max_tf = m_terms.get(str).docs.get(currDoc);
                    newterm.docs.put(currDoc, 1);//update the list of docs the term is in
                }
            }
        }

    }
    private String handleMakaf (String str)
    {
        int makaf = str.indexOf("-");
        String part1 = str.substring(0, makaf);
        part1= checkAgain(part1);
        //System.out.println(part1 + "--5");
        part1.replaceAll(" ","");
        addToTerm(part1);
        String part2 = str.substring(makaf + 1, str.length());
        if ((part2.contains("-")) && ((part2.substring(part2.indexOf('-') + 1, part2.length())).length() > 0)) {
            String part3 = part2.substring(0, part2.indexOf('-'));
            String part4 = part2.substring(part2.indexOf('-') + 1, part2.length());
            if(part3.length()>1)
                part3=checkAgain(part3);
            if(part4.length()>1)
                part4=checkAgain(part4);
            part3.replaceAll(" ","");
            part4.replaceAll(" ","");
            addToTerm(part3);
            addToTerm(part4);
            str = (part1 + " " + part3 + " " + part4);
        } else
        {
            if(part2.length()>1)
                part2=checkAgain(part2);
            //part2=checkAgain(part2);
            //  System.out.println(part2);
            part2.replaceAll(" ","");
            addToTerm(part2);
            str = part1 + " " + part2;

        }
        return str;
    }
    private String handleApostrophe (String str)
    {
        if (str.contains("\'")&&!m_StopWords.contains(str.toLowerCase())) {
            //checks if the word has an apostrphe in the middle and
            // save the word without it and the part before it
            int makaf = str.indexOf("\'");
            String part1 = str.substring(0, makaf);
            //System.out.println(part1 + "--6");
            part1.replaceAll(" ","");
            addToTerm(part1);
            String part2 = str.replace("\'", "");
            part2.replaceAll(" ","");

            str = (part2);

        }
        return str;
    }
    public boolean isNumber(String str){
        // a function to check if the term is a number
        // System.out.println(str);
        if (str.length()==0){
            return false;}
        if(Character.isDigit(str.charAt(0))||(str.length()>=2&&Character.isDigit(str.charAt(1))&&str.charAt(0)=='-')) {
            if (str.substring(1, str.length()).contains("-")) {

                return false;
            }
            try {
                /**
                 * if (i have -) done
                 * 122,222 done
                 */
                str = str.replaceAll(",", "");
                // System.out.println(str+21);
                if (str.length() > 2 && str.substring(str.length() - 2) == "th") {
                    str = str.substring(0, str.length() - 2);
                    //System.out.println(str+3);
                }
                //System.out.println(str.charAt(str.length()-1));
                //str.charAt(str.length()-1)
                if (str.length() > 1 && str.charAt(str.length() - 1) == '%') {
                    //System.out.println(str+222);
                    double d = Double.parseDouble(str.substring(0, str.length() - 1));//klajfd;
                } else {
                    //System.out.println(str+1);
                    if (str.charAt(str.length() - 1) == 'f' || str.charAt(str.length() - 1) == 'd') {
                        return false;
                    }
                    double d = Double.parseDouble(str);
                }
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }
        return false;
    }//
    private boolean isDate(String s1,String s2)
    {
        // a function to check if the term is part of a date
        //System.out.println(Months.containsKey(s1.toLowerCase()));
        //System.out.println(Months.containsKey(s2.toLowerCase()));
        //System.out.println(s2);
        if (Months.containsKey(s1.toLowerCase())|| Months.containsKey(s2.toLowerCase()))
        {
            return  true;
        }
        return false;
    }
    private boolean isPercent(String s)
    {
        if (s.toLowerCase().equals("percent") || s.equals("percentage"))
        {
            return true;
        }
        return false;
    }//

    private String numbersHandler(String s) {
        //change numer from 83.333333 to 83.33
        //String tt = "3.5555";
        String percent="";
        if (s.indexOf('%')!=-1)
        {
            percent="%";
            s=s.substring(0,s.length()-1);
        }
        if (s.indexOf("th")!=-1)
        {
            s=s.substring(0,s.length()-2);
        }
        if (s.indexOf(',')!=-1)
        {
            s=s.replaceAll(",","");
        }
        if (s.indexOf('.')!=-1) {

            int y = s.indexOf('.');

            //System.out.println(l);
            String ttt = s.substring(y + 1);
            if (ttt.length() > 2) {
                String l = s.substring(y + 3, y + 4);
                int xx = Integer.parseInt(l);
                if (xx >= 5) {
                    double x = Double.parseDouble(s);
                    x = x + 0.01;
                    s = Double.toString(x);
                    s = s.substring(0, y + 3);
                    // System.out.println(s);
                } else {
                    //tt = Double.toString(x);
                    s = s.substring(0, y + 3);
                    //System.out.println(s);
                }
            }
        }
        return  s+percent;
    }
    private  String  percent (String s){
        s=s.replaceAll(" ","");
        if(s.indexOf("%") != -1)
        {
            return s.replaceAll("%","")+" percent";
            //System.out.println(s);
        }else
        {
            return (s + " percent");

        }
    }
    public String dateHandler(String s1,String s2,String s3,String s4){//(termsDoc[i - 1], termsDoc[i], termsDoc[i + 1], termsDoc[i + 2])
        //change the format of the date in the text to the rule we have
        String day="";
        String month="";
        String year="";
        if (Months.containsKey(s3.toLowerCase()))
        {
            int intday=0;
            try {
                intday = Integer.parseInt(s2);
            }
            catch(NumberFormatException e){
                System.out.println("s2 is "+s2);
            }
            day = cmpToDay(intday);
            month=Months.get(s3.toLowerCase());
            if (isNumber(s4))
            {
                if (s4.length() == 4)
                {
                    year = s4;
                    return day+"/"+month+"/"+year;
                }
                if (s4.length()==2)
                {
                    year = "19"+s4;
                    return day+"/"+month+"/"+year;
                }

            }
            return day+"/"+month;
        }
        else
        {
            month=Months.get(s1.toLowerCase());
            if (isNumber(s3)&& s3.length()==4)
            {
                year=s3;
                int intday = Integer.parseInt(s2);
                day = cmpToDay(intday);
                return day+"/"+month+"/"+year;
            }
            else
            {
                if (s2.length()<3)
                {
                    int intday = Integer.parseInt(s2);
                    day = cmpToDay(intday);
                    return day+"/"+month;
                }else
                {
                    year = s2;
                    return month+"/"+year;
                }
            }
        }

    }
    public  int capitalTerm(String s1, String s2,String s3) {
        //ADD 4 STRING TO FUNC RETURN NUMBER OF WORDS IN phrase
        //List<String> phrase = new LinkedList<String>();
        s1=checkApo(s1);
        StringBuilder phrase=new StringBuilder(s1);
        int count=1;
        s1.replaceAll(" ","");
        addToTerm(s1);
        // phrase.add(s1);
        if (s2.length()>0&&Character.isUpperCase(s2.charAt(0))&&!Character.isDigit(s2.charAt(0)))
        {
            s2=checkApo(s2);
            s2.replaceAll(" ","");
            addToTerm(s2);
            phrase.append(" "+s2);
            count++;
            if (s3.length()>0&&Character.isUpperCase(s3.charAt(0))&&!Character.isDigit(s3.charAt(0)))
            {
                s3=checkApo(s3);
                s3.replaceAll(" ","");
                phrase.append(" "+s3);
                addToTerm(s3);
                count++;
            }
        }
        if(count>1)
            addToTerm(phrase.toString());
        return count;
    }
    private String checkApo(String str)
    {
        if(str.contains("\'")&&!m_StopWords.contains(str)) {
            int makaf = str.indexOf("\'");
            String part1 = str.substring(0, makaf);
            part1.replaceAll(" ","");
            addToTerm(part1);
            str = str.replace("\'", "");
        }
        return str;
    }
    private String cmpToDay(int d){
        String day = "";
        if (d<10)
        {
            day = "0"+Integer.toString(d);
        }
        else
        {
            day = Integer.toString(d);
        }
        return  day;
    }
    public static String removeExtra(String str)
    {
        str=str.replaceAll("[,$#!&?*(){}\":;+|\\[\\]]","");
        if (str.length()>0) {
            char last = str.charAt(str.length() - 1);
            char first = str.charAt(0);
            if (first == '<' || first == '\'' || first == '^') {
                if (str.length() > 1) {
                    str = str.substring(1, str.length());
                } else {
                    str = "";
                }
            }
            //  str=str.substring(0,str.length()-2);
            if(str.length()>0){

                if (last == '.' || last == '\'' || last == '^' || last == '-' || last == '>')
                    str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }
    public String checkAgain(String str)
    {
        if (str.contains("\'"))
        {
            str=checkApo(str);
        }
        else if(isNumber(str)) {
            str = numbersHandler(str);
            if (str.charAt(str.length() - 1) == '%') {
                str = percent(str);
            }
        }
        return str;
    }

}