package turo;

public class PairDaysPrice {

    private double price;
    private int days;

    public PairDaysPrice(double price, int days) {
        this.price = price;
        this.days = days;
    }

    public double getPrice() {
        return price;
    }

    public int getDays() {
        return days;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDays(int days) {
        this.days = days;
    }

}
