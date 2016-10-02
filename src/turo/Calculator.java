package turo;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

// this is a comment
public class Calculator {

    private String client;
    private String roomType;
    private String beginDate;
    private String endDate;
    private SeasonConfiguration configuration;

    public Calculator(String client, String roomType, String beginDate, String endDate, SeasonConfiguration configuration) throws java.text.ParseException {
        this.client = client;
        this.roomType = roomType;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.configuration = configuration;
    }

    public String getClient() {
        return this.client;
    }

    public String getRoomType() {
        return this.roomType;
    }

    public String getBeginDate() {
        return this.beginDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public PairDaysPrice[] calculate() throws java.text.ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = formatter.parse(this.beginDate);
        Date endDate = formatter.parse(this.endDate);
        PairDaysPrice[] daysPrices = new PairDaysPrice[this.configuration.getSeasons()];
        for (int i = 0; i < daysPrices.length; i++) {
            daysPrices[i] = new PairDaysPrice(0, 0);
            
        }

        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1L)) {
            int season = this.configuration.getSeason(date);
            System.out.println("season=" + season);
            System.out.println("roomType=" + roomType);
            System.out.println("price=" + this.configuration.getPrice(season, getRoomType()));
            daysPrices[season].setPrice(this.configuration.getPrice(season, getRoomType()));
            daysPrices[season].setDays(daysPrices[season].getDays() + 1);
        }

        return daysPrices;
    }
}
