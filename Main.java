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
        //System.out.println("Hello World!");
        /** String s1 = "I -- Want.\" H12: Tay-12?  C.C. to\"  my\' dog!";
         System.out.println(s1);
         TreeMap<String,Integer> docs= new TreeMap<>();
         docs.put("yael",34);
         docs.put("t",45);
         docs.put("yagl",67);
         docs.put("ya6l",675);
         String str=get_docs(docs);
         System.out.println(str);*/
        /**
        String str="12%^,. ) \"   ";
        System.out.println(str);
        Regex r=new Regex("");
        //str=str.replaceAll(String.valueOf(r.matcher(new char[]{'!','?','#','$',',','&','=','*','+','<','>','^','(',')','{','}',
          //      '[',']','\"',';',':','|',})),"");
        str=str.replaceAll("[,$#!&?*()<>^{}\\\":;+|\\[\\]\\s+]","");

        //str = str.substring(1, str.length());
        // char last = str.charAt(str.length() - 1);
         System.out.println(str+"end");
         StringBuilder sdot=new StringBuilder("1");
         sdot.append("2");
         System.out.println(sdot.toString());
         */
         //String tt = "-a";
         //String id ="123";
        //Map <String,Integer> map= new HashMap<>();
        //map.put("doc1",1);
        //Term term= new Term("year",map);
        //System.out.println(term);
        String line ="year #1 &doc1-1 [1]";
        String line2="year #2 &doc2-2 [3]";
        String NUM1=line.substring(line.indexOf("#") + 1, line.indexOf("&") - 1);
        NUM1=NUM1.replaceAll(" ","");
        //
        String NUM11=line.substring(line.indexOf("[") + 1, line.indexOf("]") );
        NUM11=NUM11.replaceAll(" ","");
        //
        String NUM2=line2.substring(line2.indexOf("#") + 1, line2.indexOf("&") - 1);
        NUM2=NUM2.replaceAll(" ","");
        //
        String NUM22=line2.substring(line2.indexOf("[") + 1, line2.indexOf("]") );
        NUM22=NUM22.replaceAll(" ","");
        //
        int number = Integer.parseInt(NUM1) + Integer.parseInt(NUM2);
        System.out.println(NUM11);
        System.out.println(NUM22);
        int number2 = Integer.parseInt(NUM11) + Integer.parseInt(NUM22);
        line = "year" + " " + "#" + " " + number + " " +"["+number2+"]";
        System.out.println(line);
         /**Document dd = new Document(id,tt,0,0,"dk");
         ReadFile r = new ReadFile("C:\\");
         r.breakToFiles();
         Parse P = new Parse(r.stopword,r.documents);
         P.parseDoc(dd);
         */
        long startTime = System.currentTimeMillis();
        //ReadFile rf=new ReadFile("C:\\Users\\linoy\\Desktop\\searching project\\corpus","");

        /**ReadFile r = new ReadFile("C:\\");

         int i = 0;
         while (r.nextFile<r.filesPaths.size())
         {
         System.out.println(i);
         r.breakToFiles();
         Parser P = new Parser(r.stopword ,r.documents,true);
         P.ParseAll();
         i++;
         }
         */
        /** Indexer N;
         Map<String,Term>mp_terms;
         mp_terms=new TreeMap<>();

         N= new Indexer(mp_terms,0,"C:\\Users\\ibrahim\\Desktop\\11\\");
         N.mergTwoFileLast("C:\\Users\\yaels\\Desktop\\11\\7\\0.txt","C:\\Users\\yaels\\Desktop\\11\\7\\1.txt","C:\\Users\\yaels\\Desktop\\11\\8\\0");
         //N.mergAllFile();
*/

        /**String text= " fdnjdfndj fdnjfdnjd fdf-fedd cddf-fdd - - -- ds -f- -------------3 4----------------- ";

        String []termsDoc=text.split("[\\s+]");
        for(int j=0;j<termsDoc.length;j++) {
            System.out.println(termsDoc[j]+"orignial");
            termsDoc[j]=handleMakaf(termsDoc[j]);
                System.out.println(termsDoc[j] + " after" + termsDoc[j].length());
        }
*/
        //Map<String,Integer> docs=new HashMap<>();
        //docs.put("doc3",3);
        //docs.put("doc412234",5);
        //docs.put("doc5",4);
        //System.out.println(docs.keySet());
        //List sortedKeys=new ArrayList(docs.keySet());
        //Collections.sort(sortedKeys);
        //System.out.println(sortedKeys);

        /**StringBuilder sb=new StringBuilder("---");
         System.out.println(sb.toString());
         while(sb.indexOf("--")!=-1)
         sb.deleteCharAt((sb.indexOf("--")));
         */
        String s="% 12/1.1/av\\c";
        System.out.println((s));
        s=removeExtra(s);
        System.out.println((s));
        s=s.replaceAll("[%\\.// \\\\\\s]","");
        System.out.println((s));
       // s=handleMakaf(s);
        //s=s.replaceAll(" ","");
       // while(s.indexOf("--")!=-1)
          //  s=s.replaceAll("--","-");
        //StringBuilder sb=new StringBuilder("PART1");
        //sb.append("apr2");
        //System.out.println(sb.toString());
        //System.out.println(Character.isAlphabetic(s.charAt(0)));
       // System.out.println(s);
        //s=removeExtra(s);
        //System.out.println(s);
        //System.out.println(isNumber(s));
        // System.out.println(s+"before");
        //s=numbersHandler(s);
       // String str="<%-%---4556%";
        //System.out.println(str.lastIndexOf('-')+"1");
        //System.out.println((str.length()-1)+"2");
        //char first=str.charAt(0);
        //if (first == '<' || first == '\'' || first == '^' || first == '.'||first == '-'||first=='%')
        //    str = str.substring(1, str.length());
        //System.out.println(str);
        // System.out.println(str+"after");
        //String str="A---12--12A";
        //if(isNumber(str))
          //  System.out.println("is number");
        //else

       // if(str.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$"))
        //    System.out.println("true");
        //else
         //   System.out.println("false");
        //str = str.replaceAll("\\s", "");
        //System.out.println(str);
        //str=str.replaceAll("[<>\\-%^\\.\\/\\\\]","");
        //String str=" h";
        //if (str.trim().length() > 0)
        //    System.out.println(str);
        //else
        //    System.out.println("dosent woek");
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
