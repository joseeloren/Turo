package turo;

public class PairDaysPricesDiscount {

    private PairDaysPrice[] pairDaysPrices;
    private String discountType;
    private String[] discount;

    public PairDaysPricesDiscount(PairDaysPrice[] pairDaysPrices, String discountType, String[] discount) {
        this.pairDaysPrices = pairDaysPrices;
        this.discountType = discountType;
        this.discount = discount;
    }

    public PairDaysPrice[] getPairDaysPrices() {
        return pairDaysPrices;
    }

    public String getDiscountType() {
        return discountType;
    }

    public String[] getDiscount() {
        return discount;
    }

    public void setPairDaysPrices(PairDaysPrice[] pairDaysPrices) {
        this.pairDaysPrices = pairDaysPrices;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public void setDiscount(String[] discount) {
        this.discount = discount;
    }
}
