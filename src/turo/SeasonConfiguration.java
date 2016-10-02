package turo;

import java.io.BufferedReader;
import java.io.File;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SeasonConfiguration {

    private int seasons;
    private int rooms;
    private String[][] prices;
    private String[] dates;

    public SeasonConfiguration() {
        this.seasons = 1;
        this.rooms = 1;
        this.prices = new String[this.rooms][this.seasons];
        this.dates = new String[this.seasons];
    }

    public void updateConfiguration() {
        File file = new File("seasons.csv");

        if (file.exists()) {
            try {
                LineNumberReader lnr = new LineNumberReader(new java.io.FileReader(file));
                lnr.skip(Long.MAX_VALUE);
                this.rooms = lnr.getLineNumber();
                lnr.close();
            } catch (Exception localException) {
            }
            try {
                BufferedReader br = new BufferedReader(new java.io.FileReader(file));
                Throwable localThrowable3 = null;
                try {
                    boolean isFirst = true;
                    int count = 0;
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (isFirst) {
                            String[] items = line.split(";");
                            this.seasons = items.length;
                            this.prices = new String[this.rooms][this.seasons];
                            this.dates = items;
                            isFirst = false;
                        } else {
                            this.prices[count] = line.split(";");
                            count++;
                        }
                    }
                } catch (Throwable localThrowable1) {
                    localThrowable3 = localThrowable1;
                    throw localThrowable1;

                } finally {
                    if (br != null) {
                        if (localThrowable3 != null) {
                            try {
                                br.close();
                            } catch (Throwable localThrowable2) {
                                localThrowable3.addSuppressed(localThrowable2);
                            }
                        } else {
                            br.close();
                        }
                    }
                }
            } catch (Exception localException1) {
            }
        }
    }

    public Object[][] getData() {
        Object[][] data = new Object[this.rooms + 1][this.seasons];
        data[0] = ((Object[]) this.dates.clone());
        for (int i = 0; i < this.prices.length; i++) {
            data[(i + 1)] = ((Object[]) this.prices[i].clone());
        }
        return data;
    }

    public int getSeasons() {
        return this.seasons;
    }

    public int getRooms() {
        return this.rooms;
    }

    public String[][] getPrices() {
        return this.prices;
    }

    public double getPrice(int season, String roomType) {
        for (int i = 0; i < this.prices.length; i++) {
            System.out.println("roomType(getPrice)=" + this.prices[i][0]);
            
            if (this.prices[i][0].equals(roomType)) {
                if (this.prices[i].length <= season) {
                    System.out.println("this.prices[i].length <= season");
                    System.out.println("this.prices[i].length=" +this.prices[i].length);
                    System.out.println("season=" +season);
                    return 0;
                }
                return Double.parseDouble(this.prices[i][season]);
            }
        }
        return 0;
    }

    public String[] getDates() {
        return this.dates;
    }

    public int getSeason(LocalDate date)
            throws java.text.ParseException {
        for (int i = 1; i < this.dates.length; i++) {
            String[] dates2 = this.dates[i].split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date startDate = formatter.parse(dates2[0]);
            Date endDate = formatter.parse(dates2[1]);
            LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if ((date.isEqual(start)) || (date.isEqual(end)) || ((date.isAfter(start)) && (date.isBefore(end)))) {
                return i;
            }
        }
        return -1;
    }
}
