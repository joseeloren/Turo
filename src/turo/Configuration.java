package turo;

import java.io.BufferedReader;
import java.io.File;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    private int seasons;
    private int rooms;
    private String[][] prices;
    private String[] seasonsDates;
    private String[][] permanents;
    private String[][] longEstance;
    private String tax;

    public Configuration() {
        this.seasons = 1;
        this.rooms = 1;
        this.prices = new String[this.rooms][this.seasons];
        this.seasonsDates = new String[this.seasons];
        this.longEstance = new String[1][5];
        this.permanents = new String[1][6];
    }

    String getTax() {
        return tax;
    }

    public void updateConfiguration() {
        File file4 = new File("longEstance.csv");
        int rows2 = 0;
        if (file4.exists()) {
            try {
                LineNumberReader lnr = new LineNumberReader(new java.io.FileReader(file4));
                lnr.skip(Long.MAX_VALUE);
                rows2 = lnr.getLineNumber();
                lnr.close();
            } catch (Exception localException) {
            }
            try {
                BufferedReader br = new BufferedReader(new java.io.FileReader(file4));
                Throwable localThrowable3 = null;
                try {
                    int count = 0;
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (count == 0) {
                            String[] items = line.split(";");
                            this.longEstance = new String[rows2][items.length];
                            this.longEstance[0] = items;
                        } else {
                            this.longEstance[count] = line.split(";");
                        }
                        count++;

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

        File file0 = new File("permanent.csv");
        int rows = 0;
        if (file0.exists()) {
            try {
                LineNumberReader lnr = new LineNumberReader(new java.io.FileReader(file0));
                lnr.skip(Long.MAX_VALUE);
                rows = lnr.getLineNumber();
                lnr.close();
            } catch (Exception localException) {
            }
            try {
                BufferedReader br = new BufferedReader(new java.io.FileReader(file0));
                Throwable localThrowable3 = null;
                try {
                    int count = 0;
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (count == 0) {
                            String[] items = line.split(";");
                            this.permanents = new String[rows][items.length];
                            this.permanents[0] = items;
                        } else {
                            this.permanents[count] = line.split(";");
                        }
                        count++;

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
                            this.seasonsDates = items;
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

        File file2 = new File("tax.csv");

        if (file2.exists()) {
            try {
                BufferedReader br = new BufferedReader(new java.io.FileReader(file2));
                Throwable localThrowable3 = null;
                try {
                    String line;
                    if ((line = br.readLine()) != null) {
                        tax = line;
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

        orderSeasons();
    }

    public Object[][] getSeasonsData() {
        Object[][] data = new Object[this.rooms + 1][this.seasons];
        data[0] = ((Object[]) this.seasonsDates.clone());
        for (int i = 0; i < this.prices.length; i++) {
            data[(i + 1)] = ((Object[]) this.prices[i].clone());
        }
        return data;
    }

    public Object[][] getPermanentData() {
        return permanents;
    }

    public Object[][] getLongEstanceData() {
        return longEstance;
    }

    public int getSeasons() {
        return this.seasons;
    }

    public int getRooms() {
        return this.rooms;
    }

    public String[][] getPrices() {
        for (int i = 0; i < prices.length; i++) {
            for (int j = 0; j < prices[i].length; j++) {
                System.out.println(prices[i][j]);
            }

        }
        return this.prices;
    }

    public String[] getRoomsNames() {
        String[] result = new String[this.rooms];
        for (int i = 0; i < this.rooms; i++) {
            result[i] = this.prices[i][0];
        }
        return result;
    }

    public double getPrice(int season, String roomType) {
        for (int i = 0; i < this.prices.length; i++) {
            System.out.println("roomType(getPrice)=" + this.prices[i][0]);

            if (this.prices[i][0].equals(roomType)) {
                if (this.prices[i].length <= season) {
                    System.out.println("this.prices[i].length <= season");
                    System.out.println("this.prices[i].length=" + this.prices[i].length);
                    System.out.println("season=" + season);
                    return 0;
                }
                return Double.parseDouble(this.prices[i][season]);
            }
        }
        return 0;
    }

    public String[] getDates() {
        return this.seasonsDates;
    }

    public int getSeason(LocalDate date)
            throws java.text.ParseException {
        for (int i = 1; i < this.seasonsDates.length; i++) {
            String[] dates2 = this.seasonsDates[i].split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
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

    private void orderSeasons() {
        int n = seasonsDates.length;
        for (int c = 1; c < (n - 1); c++) {
            for (int d = 1; d < n - c - 1; d++) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                try {
                    Date d1 = formatter.parse(seasonsDates[d].split("-")[0]);
                    Date d2 = formatter.parse(seasonsDates[d + 1].split("-")[0]);
                    LocalDate s1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate s2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (s1.isAfter(s2)) {
                        String swap = seasonsDates[d];
                        seasonsDates[d] = seasonsDates[d + 1];
                        seasonsDates[d + 1] = swap;
                        for (int j = 0; j < prices.length; j++) {
                            swap = prices[j][d];
                            prices[j][d] = prices[j][d+1];
                            prices[j][d+1] = swap;
                        }
                }
                } catch (ParseException ex) {
                    Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
}
