package SearchEngine;

import java.util.HashMap;
import java.util.Map;


public class ParserSecondTry
{
    private static HashMap<String,String> m_StopWords;
    public Map<String,Term> m_terms;//**
    private static Map<String,String> m_stem=new HashMap<>();// beforeStem,afterStem
    private Map<String,Document>m_documents;
    private boolean doStem;
    private Stemmer stemmer;
    int maxfrequency;

    private static Map <String,String> Months=new HashMap<String, String>(){{
        put("january","01"); put("february","02"); put("march","03");put("april","04");put("may","05");
        put("june","06");put("july","07");put("august","08");put("september","09");put("october","10");
        put("november","11");put("december","12");put("jan", "01");put("feb","02");put("mar","03");
        put("apr","04");put("may","05");put("jun","06");put("jul","07");put("aug","08");put("sep","09");
        put("oct","10");put("nov","11");put("dec","03");}};
    String currDoc;


    public ParserSecondTry(Map<String,String> m_StopWords,boolean doStemming) {
        if(this.m_StopWords==null)
            this.m_StopWords = new HashMap<>(m_StopWords);//added new need tot check time to run
        this.m_terms = new HashMap<>();
        doStem=true;
    }
    public void ParseAll(Map<String,Document>documents)
    {
        //int i=0;
        /**for (Map.Entry<String,Document> entry : m_documents.entrySet()) {
         //  if(i<1){
         Document value = entry.getValue();
         currDoc = entry.getKey();
         //System.out.println(currDoc+"curr doc in ParseAll");
         parseDoc(value);
         //    i++;
         // do stuff
         //  }
         //  else
         //    break;
         */
        m_documents=new HashMap<>(documents);
        for (Document duc: m_documents.values())
        {
            currDoc=duc.getId();
            parseDoc(duc);
        }
        m_documents.clear();
        //}
    }

    public void parseDoc(Document doc)
    {
        String text=doc.getText().replaceAll("<(.*?)>","");
        //doc.getText().replaceAll("-"," ");
        String []termsDoc=text.split("\\s");
        //System.out.println(currDoc+"i split the text of it");
        int count;
        String curTerm;
        text=null;
        doc.setText("");
        System.gc();
        for(int i=0;i<termsDoc.length;i++)
        {
            curTerm=termsDoc[i];
            //if(termsDoc[i].length()<1||(termsDoc[i].toUpperCase()).matches("^(?=.[A-Z])(?=.[0-9])[A-Z0-9]+$"))
            //   continue;
            count=0;
            //String SSS=termsDoc[i].replaceAll("-","");
            if((curTerm.length()==0)|| ((curTerm.trim().length()) == 0))
                //||SSS.trim().length()==0)
                continue;
            curTerm= removeExtra(curTerm);
            if (curTerm.length()>0)
            //&&)
            {
                if (!m_StopWords.containsKey(curTerm))
                {//maybe remove ***??????
                    /** if(termsDoc[i].indexOf('-')!=-1&&termsDoc[i].length()>1)
                     {
                     termsDoc[i]=handleMakaf(termsDoc[i]);
                     addToTerm(termsDoc[i]);
                     continue;
                     }
                     */
                   /** if(curTerm.charAt(0)=='$')
                    {
                        while(curTerm.length()>0&&curTerm.charAt(0)=='$')
                            curTerm=curTerm.substring(1);
                        if(curTerm.equals("")||curTerm.length()==0){
                            addToTerm("dollar");;
                            continue;
                        }
                        addToTerm(curTerm+" dollar");
                        continue;
                    }
*/
                    if(isNumber(curTerm))
                    {
                        curTerm = numbersHandler(curTerm);// numb 25-27,21/05/1991,29-word done
                        if(curTerm.length()>0)
                        {
                            if((!curTerm.contains("percent"))&&((i+1<termsDoc.length && ((removeExtra(termsDoc[i + 1])).toLowerCase()).equals("percentage"))))
                            {//check if percent
                                curTerm=curTerm+ " percent";
                                addToTerm(curTerm);
                                i++;
                                continue;
                            }
                            //addToTerm(curTerm);
                            else
                            {
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
                                if ((i+1<curTerm.length()||i-1>0)&&(isDate(s1, s3) && !curTerm.contains(".")))
                                {//216
                                    String s4="";
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
                                    //if(mydate.charAt(0)==' ')
                                    //mydate=mydate.substring(1);
                                    addToTerm(mydate);// to update i ....
                                    continue;
                                }
                            }
                            //termsDoc[i]=termsDoc[i].replaceAll("[<>%^\\\\]","");
                            addToTerm(curTerm);
                            continue;
                        }
                    }//not a number

                    else if(curTerm.length()>0){
                        //if((curTerm.toUpperCase()).matches("^(?=.[A-Z])(?=.[0-9])[A-Z0-9]+$"))
                        //  continue;
                        curTerm=curTerm.replaceAll("[$%\\.// \\\\\\s]","");
                        if (curTerm.length()>0&&Character.isUpperCase(curTerm.charAt(0)))
                        {//check if the term capital letter up to phrase of 4 words.
                            String str1 = "", str2 = "", total = "";
                            if (i + 1 < termsDoc.length) {
                                str1 = termsDoc[i + 1];
                                if (i + 2 < termsDoc.length) {
                                    str2 = termsDoc[i + 2];
                                }
                            }
                            count=capitalTerm(curTerm, removeExtra(str1), removeExtra(str2));
                            i = i + count - 1;
                            continue;
                        }
                        else
                        {
                            if(curTerm.length()>0) {
                                // termsDoc[i] = termsDoc[i].replaceAll("[\\s % \\.////]", "");
                                // termsDoc[i]=termsDoc[i].replaceAll(".")
                                if ((!m_StopWords.containsKey(curTerm.toLowerCase())) && curTerm.contains("\'"))
                                    curTerm = handleApostrophe(curTerm);
                                addToTerm(curTerm);
                                continue;
                            }
                        }
                    }
                }
            }}}
    private void addToTerm(String str)
    {
        String strafter;
        maxfrequency=m_documents.get(currDoc).getMax_tf();
        str=str.replaceAll("[\\']","");
        if((!str.equals(" "))&&str.length()>0&&!str.equals(null)) {
            str=str.toLowerCase();
            if (!m_StopWords.containsKey(str)) {
                //System.out.println(str);
                //System.out.println(str+" add to term");
                if(doStem)
                {
                    if(m_stem.containsKey(str))
                        str=m_stem.get(str);
                    else {
                        stemmer = new Stemmer();
                        stemmer.add(str.toCharArray(), str.length());
                        stemmer.stem();
                        strafter = stemmer.toString();
                        m_stem.put(str,strafter);
                        str=strafter;
                    }
                }
                // if(str.equals("illumin"))
                //   System.out.print("ilumin add to term");
                if (m_terms.containsKey(str))
                {
                    //think what have to update
                    m_terms.get(str).setTotalApperance(1);//add 1 to total number of appernces in the entire magar
                    if (m_terms.get(str).docs.containsKey(currDoc))//if i have the doc in the map of docs
                    {
                        m_terms.get(str).docs.put(currDoc, m_terms.get(str).docs.get(currDoc) + 1);//update
                        if(maxfrequency< m_terms.get(str).docs.get(currDoc))
                        {
                            maxfrequency= m_terms.get(str).docs.get(currDoc);
                            m_documents.get(currDoc).setMostCommWord(str);
                        }
                    } else
                    {//currDoc isn't in the list of files for the term- first time doc
                        m_terms.get(str).docs.put(currDoc, 1);
                        m_terms.get(str).numOfDocIDF++;
                    }
                } else
                {// first time term
                    Map<String, Integer> docss = new HashMap<>();
                    docss.put(currDoc, 1);
                    Term newterm = new Term(str, docss);
                    m_terms.put(str, newterm);
                    //m_documents.get(currDoc).max_tf = m_terms.get(str).docs.get(currDoc);
                    m_documents.get(currDoc).setMax_tf( m_terms.get(str).docs.get(currDoc));

                    //newterm.docs.put(currDoc, 1);//update the list of docs the term is in

                }
            }
        }

    }
    private String handleMakaf (String str) {
        StringBuilder total = new StringBuilder();
        //System.out.println(sb.toString());
        while (str.indexOf("--") != -1)
            str = str.replaceAll("--", "-");
        if (str.indexOf("-") != -1) {
            int makaf = str.indexOf("-");
            String part1 = (str.toString()).substring(0, makaf);
            if (part1.length() > 0) {
                //part1 = removeExtra((part1)); maybe not necessary
                if (isNumber(part1)) {
                    part1 = numbersHandler(part1);
                } else if (part1.contains("\'"))
                    part1 = handleApostrophe(part1);
                total.append(part1 + " ");
                //System.out.println(part1);
                addToTerm(part1);
            }
            String part2 = removeExtra(str.substring(makaf + 1, str.length()));//not sure if need removeExtra
            if (part2.contains("-")) {
                String part3 = part2.substring(0, part2.indexOf('-'));
                if (part3.length()>0)
                {
                    if(isNumber(part3))
                        part3=numbersHandler(part3);
                    if(part3.contains("\'"))
                        part3=handleApostrophe(part3);
                }
                String part4 = part2.substring(part2.indexOf('-') + 1, part2.length());
                if(part4.length()>0) {
                    if (isNumber(part4))
                        part4=numbersHandler(part4);
                    else if(part4.contains("-"))
                        part4=handleApostrophe(part4);
                    part4 = part4.replaceAll("-", " ");

                }
                if(part3.length()>0) {
                    total.append(part3);
                    //System.out.println(part3);
                    addToTerm(part3);
                    if(part4.length()>0) {
                        total.append(" "+part4);
                        //System.out.println(part4);
                        addToTerm(part4);
                    }
                }
            } else if (part2.length() > 0) {
                if (isNumber(part2))
                    part2 = numbersHandler(part2);
                else if (part2.contains(("\'")))
                    part2 = handleApostrophe(part2);
                //System.out.println(part2);
                addToTerm(part2);
                total.append(part2);
            }
        }
        return total.toString();
    }

    private String handleApostrophe (String str)
    {
        if (str.contains("\'")&&!m_StopWords.containsKey(str.toLowerCase())) {
            //checks if the word has an apostrphe in the middle and
            // save the word without it and the part before it
            int makaf = str.indexOf("\'");
            String part1 = str.substring(0, makaf);
            //System.out.println(part1 + "--6");
            part1=part1.trim();
            if(part1.length()>1)
                addToTerm(part1);
            str = str.replace("\'", "");
            //str=str.replaceAll(" ","");
        }
        return str;
    }
    public boolean isNumber(String str) {
        // a function to check if the term is a number
        // System.out.println(str);
        if (str.length() == 0 ) {
            return false;
        }
        if (Character.isAlphabetic(str.charAt(0)))
            return false;
        if (str.length() > 2 && str.substring(str.length() - 2).equals("th")) {
            str = str.substring(0, str.length() - 2);
        }
        if(str.endsWith("f")||str.endsWith("d"))
            return false;
        try {
            double d;
            if (str.length() > 1 && str.charAt(str.length() - 1) == '%') {
                d = Double.parseDouble(str.substring(0, str.length() - 1));
            }
            else
                d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    private boolean isDate(String s1,String s2)
    {
        // a function to check if the term is part of a date
        if (Months.containsKey(s1.toLowerCase())|| Months.containsKey(s2.toLowerCase()))
        {
            return  true;
        }
        return false;
    }
    private String numbersHandler(String s) {
        //change number from 83.333333 to 83.33
        //String tt = "3.5555";
        boolean isPercent=false;
        //StringBuilder percent=new StringBuilder();
        if (s.indexOf("th")!=-1)
        {
            //percent= percent.append("th");
            s=s.substring(0,s.length()-2);
        }
        else if (s.indexOf('%')!=-1)
        {
            isPercent=true;
            s=s.substring(0,s.length()-1);
        }
        if (s.indexOf(".")!=-1)
        {
            int y = s.indexOf(".");
            String ttt = (s.substring(y + 1));
            if (ttt.length() > 2) {
                s = s.substring(0, y + 3);
            }
        }
        if(isPercent)
            s=s+" percent";
        return  s;
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
                //System.out.println("s2 is "+s2);
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
        if(s1.contains("\'"))
            s1=handleApostrophe(s1);
        //s1=handleApostrophe(s1);
        StringBuilder phrase=new StringBuilder(s1);
        int count=1;
        //s1=s1.replaceAll(" ","");
        addToTerm(s1);
        // phrase.add(s1);
        if (s2.length()>0&&Character.isUpperCase(s2.charAt(0))&&!Character.isDigit(s2.charAt(0)))
        {
            if(s2.contains("\'"))
                s2=handleApostrophe(s2);
            //s2=s2.replaceAll(" ","");
            addToTerm(s2);
            phrase=phrase.append(" "+s2);
            count++;
          /**  if (s3.length()>0&&Character.isUpperCase(s3.charAt(0))&&!Character.isDigit(s3.charAt(0)))
            {
                if(s3.contains("\'"))
                    s3=handleApostrophe(s3);
                //s3=handleApostrophe(s3);
                //s3=s3.replaceAll(" ","");
                phrase=phrase.append(" "+s3);
                addToTerm(s3);
                count++;
            }*/
        }
        if(count>1)
            addToTerm(phrase.toString());
        phrase=null;
        System.gc();
        return count;
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
    public String removeExtra(String str)
    {
        str=str.replaceAll("[,#!&?*()<>^{}\\\":;+|\\[\\]\\s\\\\]","");
        //str=str.replaceAll([symbols.keySet().contains()],"");
        StringBuilder sdot= new StringBuilder();
        if (str.length()>0) {
            char last = str.charAt(str.length() - 1);
            char first = str.charAt(0);
            if (first=='%'||first=='\''||first=='.')
                str = str.substring(1);
            if(str.length()>0) {
                if (last=='\''||last=='.')
                    str = str.substring(0, str.length() - 1);
            }
            if (str.indexOf('.') != str.lastIndexOf('.')) {
                int dot = str.indexOf('.');
                sdot.append(str.substring(0, dot + 1));
                str = str.substring(dot + 1, str.length());
                str = str.replaceAll("\\.", "");
                sdot.append(str);
                str = sdot.toString();
            }
            sdot=null;
            System.gc();
            //while(str.indexOf("-")==0)
            //  str=str.substring(1);
            //while(str.length()>0&&str.lastIndexOf('-')==str.length()-1)
            // str = str.substring(0, str.length()-1);
        }
        return str;
    }

}