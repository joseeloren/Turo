package turo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public PairDaysPricesDiscount calculate() {
        PairDaysPrice[] daysPrices = new PairDaysPrice[this.configuration.getSeasons()];

        for (int i = 0; i < daysPrices.length; i++) {
            daysPrices[i] = new PairDaysPrice(0, 0);

        }
        LocalDate start = null;
        LocalDate end = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            Date startDate = formatter.parse(this.beginDate);
            Date endDate = formatter.parse(this.endDate);

            start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1L)) {
                int season = this.configuration.getSeason(date);
                daysPrices[season].setPrice(this.configuration.getPrice(season, getRoomType()));
                daysPrices[season].setDays(daysPrices[season].getDays() + 1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Imposible realizar la operacion. Revise las fechas.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        int diasTotales = 0;
        for (PairDaysPrice daysPrice : daysPrices) {
            diasTotales += daysPrice.getDays();
        }

        String[][] longEstanceData = (String[][]) this.configuration.getLongEstanceData();

        for (String[] longEstance : longEstanceData) {
            if ((longEstance[0].equals(getRoomType()) || longEstance[0].equals("Todos")) && (diasTotales >= Integer.parseInt(longEstance[2]))) {
                String[] dates = longEstance[4].split("-");
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                LocalDate start2 = null;
                LocalDate end2 = null;
                try {
                    Date startDate2 = formatter.parse(dates[0]);
                    Date endDate2 = formatter.parse(dates[1]);
                    start2 = startDate2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    end2 = endDate2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (longEstance[3].equals("A la llegada")) {
                        for (LocalDate date = start2; date.isBefore(end2); date = date.plusDays(1L)) {
                            if (date.isEqual(start)) {
                                return new PairDaysPricesDiscount(daysPrices, "Larga estancia", longEstance);
                            }
                        }
                    } else if (longEstance[3].equals("A la salida")) {
                        for (LocalDate date = start2; date.isBefore(end2); date = date.plusDays(1L)) {
                            if (date.isEqual(end)) {
                                return new PairDaysPricesDiscount(daysPrices, "Larga estancia", longEstance);
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            }
        }

        String[][] permanentData = (String[][]) this.configuration.getPermanentData();

        int n = permanentData.length;

        for (int c = 0; c < (n - 1); c++) {
            for (int d = 0; d < n - c - 1; d++) {
                if (Integer.parseInt(permanentData[d][5]) > Integer.parseInt(permanentData[d + 1][5])) {
                    String[] swap = permanentData[d];
                    permanentData[d] = permanentData[d + 1];
                    permanentData[d + 1] = swap;
                } else if (Integer.parseInt(permanentData[d][5]) == Integer.parseInt(permanentData[d + 1][5])) {
                    int a1 = Integer.parseInt(permanentData[d][1].split("=")[0]);
                    int a2 = Integer.parseInt(permanentData[d + 1][1].split("=")[0]);
                    if (a1 > a2) {
                        String[] swap = permanentData[d];
                        permanentData[d] = permanentData[d + 1];
                        permanentData[d + 1] = swap;
                    }
                }
            }
        }

        for (String[] permanent : permanentData) {
            String[] asd = permanent[1].split("=");
            int compa = Integer.parseInt(asd[0]);
            if ((permanent[0].equals(getRoomType()) || permanent[0].equals("Todos")) && (diasTotales <= Integer.parseInt(permanent[5])) && (compa <= diasTotales)) {
                System.out.println("entro");
                String[] dates = permanent[3].split("-");
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                LocalDate start2 = null;
                LocalDate end2 = null;
                try {
                    Date startDate2 = formatter.parse(dates[0]);
                    Date endDate2 = formatter.parse(dates[1]);
                    start2 = startDate2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    end2 = endDate2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (permanent[2].equals("A la llegada")) {
                        System.out.println("otro");
                        for (LocalDate date = start2; date.isBefore(end2); date = date.plusDays(1L)) {
                            if (date.isEqual(start2)) {
                                return new PairDaysPricesDiscount(daysPrices, "Incentivos permanentes", permanent);
                            }
                        }
                    } else if (permanent[2].equals("A la salida")) {
                        for (LocalDate date = start2; date.isBefore(end2); date = date.plusDays(1L)) {
                            if (date.isEqual(end2)) {
                                return new PairDaysPricesDiscount(daysPrices, "Incentivos permanentes", permanent);
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            }
        }

        return new PairDaysPricesDiscount(daysPrices, "", null);
    }
}
