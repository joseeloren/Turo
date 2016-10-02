/*    */ package turo;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ public class Turo
/*    */ {
/*    */   static final String LOOKANDFEEL = "System";
/*    */   
/*    */   public static void main(String[] args) throws Exception
/*    */   {
/* 12 */     initLookAndFeel();
/*    */     
/*    */ 
/* 15 */     javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
/*    */     
/* 17 */     MainFrame frame = new MainFrame();
/*    */   }
/*    */   
/*    */ 
/*    */   private static void initLookAndFeel()
/*    */   {
/* 23 */     String lookAndFeel = null;
/*    */     
/* 25 */     if ("System" != null) {
/* 26 */       if ("System".equals("Metal")) {
/* 27 */         lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
/*    */ 
/*    */ 
/*    */ 
/*    */       }
/* 32 */       else if ("System".equals("System")) {
/* 33 */         lookAndFeel = UIManager.getSystemLookAndFeelClassName();
/*    */ 
/*    */       }
/* 36 */       else if ("System".equals("Motif")) {
/* 37 */         lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
/*    */ 
/*    */       }
/* 40 */       else if ("System".equals("GTK")) {
/* 41 */         lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
/*    */       }
/*    */       else
/*    */       {
/* 45 */         System.err.println("Unexpected value of LOOKANDFEEL specified: System");
/*    */         
/* 47 */         lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
/*    */       }
/*    */       try
/*    */       {
/* 51 */         UIManager.setLookAndFeel(lookAndFeel);
/*    */       }
/*    */       catch (ClassNotFoundException e)
/*    */       {
/* 55 */         System.err.println("Couldn't find class for specified look and feel:" + lookAndFeel);
/*    */         
/* 57 */         System.err.println("Did you include the L&F library in the class path?");
/* 58 */         System.err.println("Using the default look and feel.");
/*    */       }
/*    */       catch (javax.swing.UnsupportedLookAndFeelException e)
/*    */       {
/* 62 */         System.err.println("Can't use the specified look and feel (" + lookAndFeel + ") on this platform.");
/*    */         
/*    */ 
/* 65 */         System.err.println("Using the default look and feel.");
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 69 */         System.err.println("Couldn't get specified look and feel (" + lookAndFeel + "), for some reason.");
/*    */         
/*    */ 
/* 72 */         System.err.println("Using the default look and feel.");
/* 73 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Jose\Documents\NetBeansProjects\Turo\build\classes\!\turo\Turo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */