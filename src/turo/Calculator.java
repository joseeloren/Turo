/*    */ package turo;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.time.Instant;
/*    */ import java.time.LocalDate;
/*    */ import java.time.ZoneId;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Calculator
/*    */ {
/*    */   private String client;
/*    */   private String roomType;
/*    */   private String beginDate;
/*    */   private String endDate;
/*    */   private Configuration configuration;
/*    */   
/*    */   public Calculator(String client, String roomType, String beginDate, String endDate, Configuration configuration) throws java.text.ParseException
/*    */   {
/* 19 */     this.client = client;
/* 20 */     this.roomType = roomType;
/* 21 */     this.beginDate = beginDate;
/* 22 */     this.endDate = endDate;
/* 23 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public String getClient() {
/* 27 */     return this.client;
/*    */   }
/*    */   
/*    */   public String getRoomType() {
/* 31 */     return this.roomType;
/*    */   }
/*    */   
/*    */   public String getBeginDate() {
/* 35 */     return this.beginDate;
/*    */   }
/*    */   
/*    */   public String getEndDate() {
/* 39 */     return this.endDate;
/*    */   }
/*    */   
/*    */   public PairDaysPrice[] calculate() throws java.text.ParseException {
/* 43 */     SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
/* 44 */     Date startDate = formatter.parse(this.beginDate);
/* 45 */     Date endDate = formatter.parse(this.endDate);
/* 46 */     PairDaysPrice[] daysPrices = new PairDaysPrice[this.configuration.getSeasons()];
/*    */     
/* 48 */     LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
/* 49 */     LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
/*    */     
/* 51 */     for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1L)) {
/* 52 */       int season = this.configuration.getSeason(date);
/* 53 */       daysPrices[season].setPrice(this.configuration.getPrice(season, getRoomType()));
/* 54 */       daysPrices[season].setDays(daysPrices[season].getDays() + 1);
/*    */     }
/*    */     
/* 57 */     return daysPrices;
/*    */   }
/*    */ }


/* Location:              C:\Users\Jose\Documents\NetBeansProjects\Turo\build\classes\!\turo\Calculator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */