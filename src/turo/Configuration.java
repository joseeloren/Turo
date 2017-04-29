package turo;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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

    public void updateConfiguration() throws UnsupportedEncodingException, IOException {

        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        int i = decodedPath.lastIndexOf("/");
        decodedPath = decodedPath.substring(0, i+1);
        System.out.println(decodedPath);
        

        File dir = new File(System.getProperty("user.home") + "/Turo/");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file4 = new File(dir.getAbsolutePath() + "/longEstance.csv");
        File file41 = new File(decodedPath + "longEstance.csv");
        
        if (!file4.exists()) {    
            Files.copy(file41.toPath(), file4.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

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

        File file0 = new File(dir.getAbsolutePath()  + "/permanent.csv");
        
        File file01 = new File(decodedPath + "permanent.csv");

        if (!file0.exists()) {    
            Files.copy(file01.toPath(), file0.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        
    
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

        File file = new File(dir.getAbsolutePath() + "/seasons.csv");

         File file11 = new File(decodedPath + "seasons.csv");

        if (!file.exists()) {    
            Files.copy(file11.toPath(), file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

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

        File file2 = new File(dir.getAbsolutePath() + "/tax.csv");

        File file22 = new File(decodedPath + "tax.csv");

        if (!file2.exists()) {    
            Files.copy(file22.toPath(), file2.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
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

    public String[] getRoomsNames() {
        String[] result = new String[this.rooms];
        for (int i = 0; i < this.rooms; i++) {
            result[i] = this.prices[i][0];
        }
        return result;
    }

    public double getPrice(int season, String roomType) {
        for (int i = 0; i < this.prices.length; i++) {

            if (this.prices[i][0].equals(roomType)) {
                if (this.prices[i].length <= season) {

                    return 0;
                }
                return Double.parseDouble(this.prices[i][season].replace(',', '.'));
            }
        }
        return 0;
    }

    public String[] getDates() {
        return this.seasonsDates;
    }

    public int getSeason(LocalDate date) {
        for (int i = 1; i < this.seasonsDates.length; i++) {
            String[] dates2 = this.seasonsDates[i].split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            Date startDate;
            try {
                startDate = formatter.parse(dates2[0]);
                Date endDate = formatter.parse(dates2[1]);
                LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if ((date.isEqual(start)) || (date.isEqual(end)) || ((date.isAfter(start)) && (date.isBefore(end)))) {
                    return i;
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Imposible realizar la operacion. Revise las fechas.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
        return -1;
    }

    private void orderSeasons() {
        int n = seasonsDates.length;
        for (int c = 1; c < n - 1; c++) {
            for (int d = 1; d < n - c; d++) {
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
                            prices[j][d] = prices[j][d + 1];
                            prices[j][d + 1] = swap;
                        }
                    }
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Imposible realizar la operacion. Revise las fechas.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }
}
