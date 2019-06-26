package budget;

public class Budget {

    private String date; // yyyy/MM

    public Budget(String date, Integer amount) {
        this.date = date;
        this.amount = amount;
    }

    private Integer amount;



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }





}
