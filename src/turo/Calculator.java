package turo;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;

// this is a comment
public class Calculator {

    private String roomType;
    private String beginDate;
    private String endDate;
    private Configuration configuration;

    public Calculator(String roomType, String beginDate, String endDate, Configuration configuration) throws java.text.ParseException {
        this.roomType = roomType;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.configuration = configuration;
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

    public PairDaysPrice[] calculate() {
        PairDaysPrice[] daysPrices = new PairDaysPrice[this.configuration.getSeasons()];
        for (int i = 0; i < daysPrices.length; i++) {
            daysPrices[i] = new PairDaysPrice(0, 0);

        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = formatter.parse(this.beginDate);
            Date endDate = formatter.parse(this.endDate);

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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Imposible realizar la operacion. Revise las fechas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return daysPrices;
    }
}
