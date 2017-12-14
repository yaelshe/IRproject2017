package SearchEngine;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Indexer
{
    private Map<String,Term>mp_terms;
    public Map<String,TermDic> m_Dictionary;
    //private final List sortedTerms;
    public Map<String,TermCache>m_Cache;
    private int mytxt;
    private String mypath;
    private String newLine;


    public Indexer(Map<String,Term> parsedWords,int i,String mypath) throws IOException {//change the i to path ....
        this.mypath="C:\\Users\\yaels\\Desktop\\11\\";
        mp_terms=new TreeMap<>(parsedWords);
        mytxt = i;
        m_Dictionary=new HashMap<>();
        m_Cache=new HashMap<>();
        newLine = System.getProperty("line.separator");
        www();
    }
    public void www() throws IOException
    {
        //NEED TO CHANGE BACK TO GET MYPART !!!!!!!!!!!!!!!!!!!!!!!
         //File logFile=new File("C:\\Users\\yaels\\Desktop\\11\\ibr.txt");
        File logFile=new File(mypath+mytxt+".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
        //System.out.println(logFile.getCanonicalPath());
        //int i = 1;
        System.out.println(logFile.getAbsolutePath());
        for (String termo: mp_terms.keySet()){
            //we go over the words from parser
            //add the words to dictionary
            //for each term addd the number of dcos and ttotal appernces
            if(m_Dictionary.containsKey(termo))
            {
                m_Dictionary.get(termo).setApperances(mp_terms.get(termo).getTotalApperance());
                m_Dictionary.get(termo).setNumOfDocs(mp_terms.get(termo).getNumOfDocIDF());
            }
            else
                m_Dictionary.put(termo, new TermDic(termo,mp_terms.get(termo).getTotalApperance(),
                        mp_terms.get(termo).getNumOfDocIDF(),0));
            String value = mp_terms.get(termo).toString();//get the string that describe the term
            WriteToTxt(value,writer);
        }
        List <TermDic>sortedTerms=new ArrayList(m_Dictionary.values());
        Collections.sort(sortedTerms);
        for(int m=0;m<10000;m++)
        {
            m_Cache.put(sortedTerms.get(sortedTerms.size()-1-m).getName(),
                    new TermCache(sortedTerms.get(sortedTerms.size()-1-m).getName(),"",0));
        }
        writer.close();
    }
    public void WriteToTxt(String s, BufferedWriter writer){
        // Yael removed because unnecessary alreday include in buffer writer - File logFile
        String string =s;
        //System.out.println(s);
        try {
            writer.write (string+newLine);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public  void  mergAllFile() throws IOException {
        File logFile=new File(mypath+"00.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
        File directory = new File(mypath+"\\1");
        directory.mkdirs();
        String path3=directory.getCanonicalPath();
        for (int j=0;j<=181;j=j+2){
            String s =mergTwoFile(mypath+j+".txt",mypath+(j+1)+".txt",path3+"\\"+(j/2));
            System.out.println(j);
        }
        String thepath = directory.getAbsolutePath();
        directory = new File(mypath+"\\2");
        directory.mkdirs();
        path3=mypath+"2";
        for (int j=0;j<90;j=j+2){
            String s =mergTwoFile(thepath+"\\"+j+".txt",thepath+"\\"+(j+1)+".txt",path3+"\\"+(j/2));
            System.out.println(j);
        }
        String t=mergTwoFile(thepath+"\\"+90+".txt",logFile.getAbsolutePath(),path3+"\\45");
        thepath = directory.getAbsolutePath();
        directory=new File(mypath+"\\3");
        directory.mkdirs();
        path3=mypath+"3";
        for (int j=0;j<=45;j=j+2){
            String s =mergTwoFile(thepath+"\\"+j+".txt",thepath+"\\"+(j+1)+".txt",path3+"\\"+(j/2));
            System.out.println(j);
        }
        thepath = directory.getAbsolutePath();
        directory=new File(mypath+"\\4");
        directory.mkdirs();
        path3=mypath+"4";
        for (int j=0;j<22;j=j+2){
            String s =mergTwoFile(thepath+"\\"+j+".txt",thepath+"\\"+(j+1)+".txt",path3+"\\"+(j/2));
            System.out.println(j);
        }
        t=mergTwoFile(thepath+"\\"+22+".txt",logFile.getAbsolutePath(),path3+"\\11");
        thepath = directory.getAbsolutePath();
        directory=new File(mypath+"\\5");
        directory.mkdirs();
        path3=mypath+"5";
        for (int j=0;j<=11;j=j+2){
            String s =mergTwoFile(thepath+"\\"+j+".txt",thepath+"\\"+(j+1)+".txt",path3+"\\"+(j/2));
            System.out.println(j);
        }
        thepath = directory.getAbsolutePath();
        directory=new File(mypath+"\\6");
        directory.mkdirs();
        path3=mypath+"6";
        for (int j=0;j<=5;j=j+2){
            String s =mergTwoFile(thepath+"\\"+j+".txt",thepath+"\\"+(j+1)+".txt",path3+"\\"+(j/2));
            System.out.println(j);
        }
        thepath = directory.getAbsolutePath();
        directory=new File(mypath+"\\7");
        directory.mkdirs();
        path3=mypath+"7";
        for (int j=0;j<2;j=j+2){
            String s =mergTwoFile(thepath+"\\"+j+".txt",thepath+"\\"+(j+1)+".txt",path3+"\\"+(j/2));
            System.out.println(j);
        }
        t=mergTwoFile(thepath+"\\"+2+".txt",logFile.getAbsolutePath(),path3+"\\1");
        thepath = directory.getAbsolutePath();
        directory=new File(mypath+"\\8");
        directory.mkdirs();
        path3=mypath+"8";
        for (int j=0;j<=1;j=j+2){
            String s =mergTwoFileLast(thepath+"\\"+j+".txt",thepath+"\\"+(j+1)+".txt",path3+"\\"+(j/2));
            System.out.println(j);
        }
    }
    public String mergTwoFileLast(String path1,String path2,String path3) throws IOException{
        //the merge for the last tow temporary posting files
        //create the dictionary and cache
        int counterLine=0;
        File finalFile=new File(path3+".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(finalFile));
        BufferedReader br = new BufferedReader(new FileReader(path1));
        BufferedReader bs = new BufferedReader(new FileReader(path2));
        System.out.println(finalFile.getAbsolutePath());
        System.out.println(path1);
        System.out.println(path2);
        boolean write1 = false;
        boolean write2 = false;
        boolean read1=true;
        boolean read2=true;
        String line = br.readLine();
        String line2 = bs.readLine();
        String s1 = line.substring(0, line.indexOf("#") - 1);
        String s2 = "";
        if (line2!=null) {
            s2 = line2.substring(0, line2.indexOf("#") - 1);
        }

        while (line != null && line2 != null &&!line.equals("null") &&!line2.equals("null")) {
            /**try {

             if (write1) {
             line = br.readLine();

             //System.out.println(line);*/
            if (line != null&&!line.equals("null")){
                //System.out.println(line);

                s1 = line.substring(0, line.indexOf("#") - 1);
                //System.out.println(s1);
            }
            /**}

             } catch (IOException e) {
             e.printStackTrace();
             }
             try {
             //try(BufferedReader br = new BufferedReader(new FileReader(path2))) {
             if (write2) {
             line2 = bs.readLine();

             //System.out.println(line2);*/
            if (line2 != null&& !line2.equals("null")){
                // System.out.println(line2);
                //System.out.println(line2.indexOf("#") - 1);
                s2 = line2.substring(0, line2.indexOf("#") - 1);

            }
            /**}
             } catch (IOException e) {
             e.printStackTrace();
             }*/

            if (s1.equals(s2))
            {//if it's the same term

                String docs = line.substring(line.indexOf("&"), line.length()) + line2.substring(line2.indexOf("&") + 1, line2.length());
                String NUM1=line.substring(line.indexOf("#") + 1, line.indexOf("&") - 1);
                NUM1=NUM1.replaceAll(" ","");

                String NUM2=line2.substring(line2.indexOf("#") + 1, line2.indexOf("&") - 1);
                NUM2=NUM2.replaceAll(" ","");
                int number = Integer.parseInt(NUM1) + Integer.parseInt(NUM2);
                line = s1 + " " + "#" + " " + number + " " + docs;
                writer.write(line + System.getProperty("line.separator"));
                //int appercances=getAapperances(line);
                m_Dictionary.get(s1).setPointer(counterLine);
                if(m_Cache.containsKey(s1))
                {
                    m_Cache.get(s1).setPointer(counterLine);
                    m_Cache.get(s1).setFavDocs("()");// need to change in all times
                }
                counterLine++;
                write1 = true;
                write2 = true;
                //writer.close();
            } else {
                if (s1.compareTo(s2) < 0) {

                    //System.out.println(s1);
                    writer.write(line + System.getProperty("line.separator"));
                    //int appercances=getAapperances(line);
                    m_Dictionary.get(s1).setPointer(counterLine);
                    if(m_Cache.containsKey(s1))
                    {
                        m_Cache.get(s1).setPointer(counterLine);
                        m_Cache.get(s1).setFavDocs("()");// need to change in all times
                    }
                    counterLine++;
                    write1 = true;
                    write2 = false;
                    //writer.close();
                } else {
                    //System.out.println(s2);
                    writer.write(line2 + System.getProperty("line.separator"));
                    if (!m_Dictionary.containsKey(s2))
                        System.out.println(s2);
                    m_Dictionary.get(s2).setPointer(counterLine);
                    if(m_Cache.containsKey(s2))
                    {
                        m_Cache.get(s2).setPointer(counterLine);
                        m_Cache.get(s2).setFavDocs("()");// need to change in all times
                    }
                    counterLine++;
                    write1 = false;
                    write2 = true;
                    //writer.close();
                }
            }
            if (write1)
                line = br.readLine();
            if (write2)
                line2 = bs.readLine();
        }
        if (line != null) {
            while (line != null) {
                //System.out.println(1);
                if (!read1&&write1)
                {
                    line = br.readLine();
                    write1=false;
                }
                if (!write1)
                {
                    writer.write(line + System.getProperty("line.separator"));
                    int appercances=getAapperances(line);
                    String sx=line.substring(0, line.indexOf("#") - 1);
                    if(m_Dictionary.containsKey(sx));
                        m_Dictionary.get(sx).setPointer(counterLine);
                    if(m_Cache.containsKey(sx))
                    {
                        m_Cache.get(sx).setPointer(counterLine);
                        m_Cache.get(sx).setFavDocs("()");// need to change in all times
                    }

                    counterLine++;
                    write1=true;
                }

                read1 =false;
            }
        } else
        {
            while (line2 != null) {
                //System.out.println(2);
                if (!read2&&write2)
                {
                    line2 = bs.readLine();
                    write2=false;
                }
                if (!write2)
                {
                    writer.write(line2 + System.getProperty("line.separator"));
                    int appercances=getAapperances(line2);
                    String s=line2.substring(0, line2.indexOf("#") - 1);
                    if(m_Dictionary.containsKey(s));
                        m_Dictionary.get(s).setPointer(counterLine);
                    if(m_Cache.containsKey(s))
                    {
                        m_Cache.get(s).setPointer(counterLine);
                        m_Cache.get(s).setFavDocs("()");// need to change in all times
                    }
                    counterLine++;
                    write2=true;
                }
                read2=false;
            }
        }
        writer.close();
        System.out.println("closed");

        return "";
    }
    public String mergTwoFile(String path1,String path2,String path3) throws IOException {
        File logFile=new File(path3+".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
        BufferedReader br = new BufferedReader(new FileReader(path1));
        BufferedReader bs = new BufferedReader(new FileReader(path2));
        System.out.println(logFile.getAbsolutePath());
        System.out.println(path1);
        System.out.println(path2);
        boolean write1 = false;
        boolean write2 = false;
        boolean read1=true;
        boolean read2=true;
        String line = br.readLine();
        String line2 = bs.readLine();
        String s1 = line.substring(0, line.indexOf("#") - 1);
        String s2 = "";
        if (line2!=null) {
            s2 = line2.substring(0, line2.indexOf("#") - 1);
        }

        while (line != null && line2 != null &&!line.equals("null") &&!line2.equals("null")) {
            /**try {
             if (write1) {
             line = br.readLine();
             //System.out.println(line);*/
            if (line != null&&!line.equals("null")){
                //System.out.println(line);
                s1 = line.substring(0, line.indexOf("#") - 1);
                //System.out.println(s1);
            }
            /**}
             } catch (IOException e) {
             e.printStackTrace();
             }
             try {
             //try(BufferedReader br = new BufferedReader(new FileReader(path2))) {
             if (write2) {
             line2 = bs.readLine();
             //System.out.println(line2);*/
            if (line2 != null&& !line2.equals("null")){
                // System.out.println(line2);
                //System.out.println(line2.indexOf("#") - 1);
                s2 = line2.substring(0, line2.indexOf("#") - 1);
            }
            /**}
             } catch (IOException e) {
             e.printStackTrace();
             }*/
            if (s1.equals(s2)) {
                String docs = line.substring(line.indexOf("&"), line.length()) + line2.substring(line2.indexOf("&") + 1, line2.length());
                String NUM1=line.substring(line.indexOf("#") + 1, line.indexOf("&") - 1);
                NUM1=NUM1.replaceAll(" ","");

                String NUM2=line2.substring(line2.indexOf("#") + 1, line2.indexOf("&") - 1);
                NUM2=NUM2.replaceAll(" ","");
                int number = Integer.parseInt(NUM1) + Integer.parseInt(NUM2);
                line = s1 + " " + "#" + " " + number + " " + docs;
                writer.write(line + System.getProperty("line.separator"));
                write1 = true;
                write2 = true;
                //writer.close();
            } else {
                if (s1.compareTo(s2) < 0) {
                    //System.out.println(s1);
                    writer.write(line + System.getProperty("line.separator"));
                    write1 = true;
                    write2 = false;
                    //writer.close();
                } else {
                    //System.out.println(s2);
                    writer.write(line2 + System.getProperty("line.separator"));
                    write1 = false;
                    write2 = true;
                    //writer.close();
                }
            }
            if (write1)
             line = br.readLine();
            if (write2)
                line2 = bs.readLine();
        }
        if (line != null) {
            while (line != null) {
                //System.out.println(1);
                if (!read1&&write1)
                {
                    line = br.readLine();
                    write1=false;
                }
                if (!write1)
                {
                    writer.write(line + System.getProperty("line.separator"));
                    write1=true;
                }
                read1 =false;
            }
        } else
        {
            while (line2 != null) {
                //System.out.println(2);
                if (!read2&&write2)
                {
                    line2 = bs.readLine();
                    write2=false;
                }
                if (!write2)
                {
                    writer.write(line2 + System.getProperty("line.separator"));
                    write2=true;
                }
                read2=false;
            }
        }
        writer.close();
        System.out.println("closed");

        return "";
    }
    private int getAapperances(String line)
    {
        int first=line.indexOf("[");
        int last=line.indexOf("]");
        int appercances=0;
        try {
            appercances = Integer.parseInt(line.substring(first, last));
        }
        catch(NumberFormatException nfe) {
            System.out.println("dont have int appernces print in indexr last merge");
        }
        return appercances;
    }
}