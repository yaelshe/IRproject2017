package SearchEngine;

public class DictionaryTermGui {
    private String term;
    private String amount;

    public DictionaryTermGui(String name, String quantity){
        term=name;
        amount=quantity;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTerm()
    {
        return term;
    }
}
