/*     */ package turo;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.LineNumberReader;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
/*     */ import java.time.ZoneId;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Configuration
/*     */ {
/*     */   private int seasons;
/*     */   private int rooms;
/*     */   private String[][] prices;
/*     */   private String[] dates;
/*     */   
/*     */   public Configuration()
/*     */   {
/*  21 */     this.seasons = 1;
/*  22 */     this.rooms = 1;
/*  23 */     this.prices = new String[this.rooms][this.seasons];
/*  24 */     this.dates = new String[this.seasons];
/*     */   }
/*     */   
/*     */   public void updateConfiguration() {
/*  28 */     File file = new File("seasons.csv");
/*     */     
/*  30 */     if (file.exists()) {
/*     */       try {
/*  32 */         LineNumberReader lnr = new LineNumberReader(new java.io.FileReader(file));
/*  33 */         lnr.skip(Long.MAX_VALUE);
/*  34 */         this.rooms = lnr.getLineNumber();
/*  35 */         lnr.close();
/*     */       }
/*     */       catch (Exception localException) {}
/*     */       try {
/*  39 */         BufferedReader br = new BufferedReader(new java.io.FileReader(file));Throwable localThrowable3 = null;
/*     */         try {
/*  41 */           boolean isFirst = true;
/*  42 */           int count = 0;
/*  43 */           String line; while ((line = br.readLine()) != null) {
/*  44 */             if (isFirst) {
/*  45 */               String[] items = line.split(";");
/*  46 */               this.seasons = items.length;
/*  47 */               this.prices = new String[this.rooms][this.seasons];
/*  48 */               this.dates = items;
/*  49 */               isFirst = false;
/*     */             } else {
/*  51 */               this.prices[count] = line.split(";");
/*  52 */               count++;
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Throwable localThrowable1)
/*     */         {
/*  39 */           localThrowable3 = localThrowable1;throw localThrowable1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  55 */           if (br != null) if (localThrowable3 != null) try { br.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else br.close();
/*     */         }
/*     */       } catch (Exception localException1) {}
/*     */     }
/*     */   }
/*     */   
/*  61 */   public Object[][] getData() { Object[][] data = new Object[this.rooms + 1][this.seasons];
/*  62 */     data[0] = ((Object[])this.dates.clone());
/*  63 */     for (int i = 0; i < this.prices.length; i++) {
/*  64 */       data[(i + 1)] = ((Object[])this.prices[i].clone());
/*     */     }
/*  66 */     return data;
/*     */   }
/*     */   
/*     */   public int getSeasons() {
/*  70 */     return this.seasons;
/*     */   }
/*     */   
/*     */   public int getRooms() {
/*  74 */     return this.rooms;
/*     */   }
/*     */   
/*     */   public String[][] getPrices() {
/*  78 */     return this.prices;
/*     */   }
/*     */   
/*     */   public double getPrice(int season, String roomType) {
/*  82 */     for (int i = 0; i < this.prices.length; i++) {
                System.out.println("roomType(getPrice)="+this.prices[i][0]);
/*  83 */       if (this.prices[i][0].equals(roomType))
/*  84 */         return Double.parseDouble(this.prices[i][(season + 1)]);
/*     */     }
/*  86 */     return 0;
/*     */   }
/*     */   
/*     */   public String[] getDates() {
/*  90 */     return this.dates;
/*     */   }
/*     */   
/*     */   public int getSeason(LocalDate date)
/*     */     throws java.text.ParseException
/*     */   {
/*  96 */     for (int i = 1; i < this.dates.length; i++) {
/*  97 */       String[] dates2 = this.dates[i].split("-");
/*  98 */       SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
/*  99 */       Date startDate = formatter.parse(dates2[0]);
/* 100 */       Date endDate = formatter.parse(dates2[1]);
/* 101 */       LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
/* 102 */       LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
/* 103 */       if ((date.isEqual(start)) || (date.isEqual(end)) || ((date.isAfter(start)) && (date.isBefore(end)))) {
/* 104 */         return i;
/*     */       }
/*     */     }
/* 107 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Jose\Documents\NetBeansProjects\Turo\build\classes\!\turo\Configuration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */