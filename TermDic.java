package SearchEngine;

public class TermDic implements Comparable {
    String name;
    int apperances;
    int pointer;//row number in posting file
    int numOfDocs;

    public TermDic(String name, int apperances,int linePointer,int num) {
        this.name = name;
        this.apperances = apperances;
        this.pointer=linePointer;
        this.numOfDocs=num;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public int getNumOfDocs() {
        return numOfDocs;
    }

    public void setNumOfDocs(int numOfDocs) {
        this.numOfDocs = this.numOfDocs+numOfDocs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getApperances() {
        return apperances;
    }

    public void setApperances(int apperances) {
        this.apperances = this.apperances+apperances;
    }
    @Override
    public int compareTo(Object o) {
        if(this.numOfDocs==((TermDic)o).getNumOfDocs())
            return 0;
        else if(this.numOfDocs>((TermDic)o).getNumOfDocs())
            return 1;
        else
            return -1;
    }
}
