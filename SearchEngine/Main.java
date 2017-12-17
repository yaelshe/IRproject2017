package SearchEngine;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import java.util.*;


public class Main {

    //private static Regex r=new Regex((new char[]{'!','?','#','$',',','&','=','*','+','<','>','^','(',')','{','}',
    //'[',']','\"',';',':','|',}));
            //(,$#!&=?*<>^(){}\":;+|\\[\\]]);

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();

        ReadFile r= new ReadFile("C:\\Users\\sheinbey\\Downloads\\");
        Indexer indexer=null;
        //C:\Users\sheinbey\Downloads\corpus
        int i = 0;
        while (r.nextFile<r.filesPaths.size())
        {
            System.out.println(i);
            Runtime instance=Runtime.getRuntime();
            System.out.println((instance.totalMemory())/(1024*1024)+"fd");
            r.breakToFiles();
            System.out.println((instance.totalMemory())/(1024*1024)+"fdd");
            Parser P= new Parser(r.stopword,r.documents,true);
            P.ParseAll();

            //indexer=new Indexer(P.m_terms,0,pathToPosting);
            try {
                 indexer =new Indexer(P.m_terms,i,"");//changed to i
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("here dosnt");
            }
            i++;
            //indexer=new Indexer();//add the m_terms and the path for posting files
        }
        try {
            indexer.mergAllFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime/1000/60);

        // String []strArray=s1.split("\\s+");
        //for(int i=0; i<strArray.length;i++ )
        //     {
        //strArray[i] = strArray[i].substring(0, strArray[i].length() - 1);
        //        System.out.println(strArray[i]);
        //    }

        //List<String> ph=capitalTerm(strArray[0],strArray[1],strArray[2],strArray[3]);
        //System.out.println(ph.toString());
        //System.out.println(s);
        /**for(int g=0; g<strArray.length;g++)
         {
         System.out.println("old "+strArray[g]);
         strArray[g]=removeExtra(strArray[g]);
         System.out.println("new "+strArray[g]);
         }*/
        //}
        //  if(!(n.equals(n.toLowerCase())))
        //    capitalTerm(n);
        //else
        //  System.out.println(n.toLowerCase());
        //System.out.println(n.toLowerCase());
        /*if(n.endsWith("\'nt")) {
            System.out.println(n.substring(0, n.indexOf('\'')));
            System.out.println(n.substring(0,n.indexOf('\''))+"nt");
        }*/
        //term.add(n.n.substring(0,n.indexOf('\'')));
        //terms.add(n.substring(0,n.indexOf('\''))+"nt");
        //String n= ".13";
        //if(isNumeric(n))
        //    System.out.print("is number");
        //else
        //    System.out.print("not");
        //System.out.println(str.charAt(str.length()));
        //System.out.println(str.charAt(str.length() - 1));
        //if ((str.charAt(str.length() - 1) == '\"'))
        //    System.out.println("have slash");
        //else
        //    System.out.println("no have");
        //System.out.println(str.length);
    }

    private static String handleMakaf (String str) {
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
                System.out.println(part1);
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
                    System.out.println(part3);
                    if(part4.length()>0) {
                        total.append(" "+part4);
                        System.out.println(part4);
                    }
                }
            } else if (part2.length() > 0) {
                if (isNumber(part2))
                    part2 = numbersHandler(part2);
                else if (part2.contains(("\'")))
                    part2 = handleApostrophe(part2);
                System.out.println(part2);
                total.append(part2);
            }
        }
        return total.toString();
    }
    private static String numbersHandler(String s) {
        //change number from 83.333333 to 83.33
        //String tt = "3.5555";
        StringBuilder percent=new StringBuilder();
        if (s.indexOf("th")!=-1)
        {
            //percent.append("th");
            s=s.substring(0,s.length()-2);
        }
        else if (s.indexOf('%')!=-1)
        {
            percent.append("%");
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
        return  s+percent.toString();
    }


    public static boolean isNumber(String str) {
        // a function to check if the term is a number
        // System.out.println(str);
        if (str.length() == 0 || str.equals(" ")) {
            return false;
        }
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

    //

    public static void breakPhrase(LinkedList<String> phrase) {
        if (phrase.size() == 1)
            phrase.get(0).toLowerCase();
            //add to term dictionary
        else {
            for (int j = 0; j < phrase.size() - 1; j++) {
                phrase.get(0).toLowerCase();
                //add term to dictionary
            }
            //add the hole phrase to dictionary
        }
    }



    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    public static String removeExtra(String str)
    {
        //str=str.replaceAll("[,$#!&?*(){}\":;+|\\[\\] \\s\\\\ ]","");
        str=str.replaceAll("[,$#!&?*()<>^{}\\\":;+|\\[\\]\\s\\\\]","");
        StringBuilder sdot= new StringBuilder();
        if (str.length()>0) {
            char last = str.charAt(str.length() - 1);
            char first = str.charAt(0);
            if (first == '<' || first == '\'' || first == '^' || first == '.'||first == '-')
                str = str.substring(1, str.length() - 1);
            if(str.length()>0) {
                if (last == '.' || last == '\'' || last == '^' || last == '-' || last == '>')
                    str = str.substring(0, str.length() - 1);
            }
            if (str.contains(".")) {
                int dot = str.indexOf('.');
                if (dot != str.lastIndexOf('.')) {
                    sdot.append(str.substring(0, dot + 1));
                    str = str.substring(dot + 1, str.length());
                    str = str.replaceAll("\\.", "");
                    sdot.append(str);
                    str = sdot.toString();
                }
            }
        }
        return str;
    }
    public static void breaktoMakaf(String termsDoc)
    {
        if(termsDoc.contains("-"))
        {
            int makaf=termsDoc.indexOf("-");
            String part1=termsDoc.substring(0,makaf);
            //addToTerm(part1);
            System.out.println(part1);
            String part2=termsDoc.substring(makaf+1,termsDoc.length());
            //System.out.println((part2.substring(part2.indexOf('-')+1,part2.length())).length());
            if ((part2.contains("-"))&&((part2.substring(part2.indexOf('-')+1,part2.length())).length()>0))
            {
                String part3=part2.substring(0,part2.indexOf('-'));
                String part4=part2.substring(part2.indexOf('-')+1,part2.length());

                //addToTerm(part3);
                System.out.println(part3);
                System.out.println(part4);
                //addToTerm(part4);
            }
            else {
                //addToTerm(part2);
            }
            //addToTerm(part2);
            //add part1, part 2 part 1 &part 2 together and with - to terms

        }
    }
    public static void breakOPSTROPE(String termsDoc)
    {
        if(termsDoc.contains("\'"))
        {
            int makaf=termsDoc.indexOf("\'");
            String part1=termsDoc.substring(0,makaf);

            //addToTerm(part1);
            String part2=termsDoc.replace("\'","");

            //addToTerm(part2);


            //add part1, part 2 part 1 &part 2 together and with - to terms

        }
    }
    public static void capital(String termsDoc)
    {
        if (Character.isUpperCase(termsDoc.charAt(0))) {
            //cheek if the term capital ...
            String termsDoc2="As", termsDoc3=null, termsDoc4="Dd";
            List<String> ph = capitalTerm(termsDoc, termsDoc2, termsDoc3, termsDoc4);
            //System.out.println("why");
            //List<String> ph = capitalTerm(removeExtra(termsDoc), removeExtra(termsDoc2), removeExtra(termsDoc3)
            // , removeExtra(termsDoc4));
            if (ph.size() == 1) {
                ph.get(0).toLowerCase();
                System.out.println(ph.get(0).toLowerCase());
            }
            //add to term dictionary
            else {
                System.out.println(ph.toString());
                for (int j = 0; j < ph.size(); j++) {
                    ph.get(j).toLowerCase();

                    System.out.println(ph.get(j).toLowerCase());
                }
            }
        }
    }
    public static List<String> capitalTerm(String s1, String s2,String s3,String s4) {
        //ADD 4 STRING TO FUNC RETURN NUMBER OF WORDS IN phrase
        List<String> phrase = new LinkedList<String>();
        phrase.add(s1);
        //System.out.println("1");
        if (s2!=null&&Character.isUpperCase(s2.charAt(0))&&!Character.isDigit(s2.charAt(0))) {
            phrase.add(s2);
            if (s3!=null&&Character.isUpperCase(s3.charAt(0))&&!Character.isDigit(s3.charAt(0))){
                phrase.add(s3);
                if (s4!=null&&Character.isUpperCase(s4.charAt(0))&&!Character.isDigit(s4.charAt(0)))
                    phrase.add(s4);}
        }
        return phrase;
    }
    public static String get_docs(TreeMap<String,Integer> docs){
        String str="";
        for (String docnum: docs.keySet()){

            String key =docnum;
            String value =docs.get(docnum).toString();
            System.out.print(key + "-" + value+" ");


        }
        return str;
    }

    private static String handleApostrophe (String str)
    {
        if (str.contains("\'"))
                //&&!m_StopWords.contains(str.toLowerCase()))
         {
            //checks if the word has an apostrphe in the middle and
            // save the word without it and the part before it
            int makaf = str.indexOf("\'");
            String part1 = str.substring(0, makaf);
            //System.out.println(part1 + "--6");
            part1=part1.replaceAll(" ","");
            //addToTerm(part1);
             System.out.println(part1);
            str = str.replace("\'", "");
            str=str.replaceAll(" ","");
        }
        return str;
    }
}
