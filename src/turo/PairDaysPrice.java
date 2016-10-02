package turo;

public class PairDaysPrice {

    private int days = 0;
    private double price = 0;

    public PairDaysPrice(int days, double price) {
        this.days = days;
        this.price = price;
    }

    public int getDays() {
        return this.days;
    }

    public double getPrice() {
        return this.price;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
