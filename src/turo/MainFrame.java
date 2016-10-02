/*     */ package turo;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.text.ParseException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.MenuEvent;
/*     */ import javax.swing.event.MenuListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MainFrame
/*     */   extends JFrame
/*     */ {
/*     */   private JMenuBar menuBar;
/*     */   private JMenu fileMenu;
/*     */   private JMenu optionsMenu;
/*     */   private BoxLayout boxLayout;
/*     */   private JPanel top;
/*     */   private JPanel bottom;
/*     */   private JPanel general;
/*     */   private JButton calculate;
/*     */   private JTable table;
/*     */   private JTextField client;
/*     */   private JTextField booking;
/*     */   private JTextField beginDate;
/*     */   private JTextField endDate;
/*     */   private JTextField roomType;
/*     */   private JLabel roomTypeL;
/*     */   private JLabel clientL;
/*     */   private JLabel bookingL;
/*     */   private JLabel beginDateL;
/*     */   private JLabel endDateL;
/*     */   private JPanel roomTypeP;
/*     */   private JPanel clientP;
/*     */   private JPanel bookingP;
/*     */   private JPanel beginDateP;
/*     */   private JPanel endDateP;
/*     */   private JMenuItem option;
/*     */   private Configuration configuration;
/*     */   
/*     */   public MainFrame()
/*     */   {
/*  78 */     this.configuration = new Configuration();
/*  79 */     this.configuration.updateConfiguration();
/*     */     
/*  81 */     this.top = new JPanel();
/*  82 */     this.bottom = new JPanel();
/*  83 */     this.general = new JPanel();
/*     */     
/*  85 */     this.menuBar = new JMenuBar();
/*  86 */     this.fileMenu = new JMenu("Archivo");
/*  87 */     this.optionsMenu = new JMenu("Configuración");
/*  88 */     this.optionsMenu.addMenuListener(new MenuListener()
/*     */     {
/*     */       public void menuSelected(MenuEvent e) {
/*  91 */         new ConfigurationDialog(MainFrame.this);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       public void menuDeselected(MenuEvent e) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       public void menuCanceled(MenuEvent e) {}
/* 103 */     });
/* 104 */     this.menuBar.add(this.fileMenu);
/* 105 */     this.menuBar.add(this.optionsMenu);
/*     */     
/* 107 */     setJMenuBar(this.menuBar);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 113 */     this.calculate = new JButton("Calcular");
/*     */     
/*     */ 
/* 116 */     String[] columnNames = { "Num. Reserva", "Cliente", "Tipo habitacion", "Fecha entrada", "Fecha salida", "Tipo descuento", "Porcentaje descuento", "Base imponible", "IGIC", "Total" };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 128 */     final Object[][] data = { { "85611444", "WINIGER MARKUS", "Premium", "13/07/2016", "24/07/2016", "Ninguno", "0", "771,03", "53,97", "825" } };
/*     */     
/*     */ 
/*     */ 
/* 132 */     this.table = new JTable(data, columnNames);
/* 133 */     this.table.setFillsViewportHeight(true);
/* 134 */     this.table.setAutoResizeMode(4);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 140 */     this.clientP = new JPanel();
/*     */     
/* 142 */     this.bookingP = new JPanel();
/* 143 */     this.beginDateP = new JPanel();
/* 144 */     this.endDateP = new JPanel();
/* 145 */     this.roomTypeP = new JPanel();
/*     */     
/*     */ 
/* 148 */     this.beginDate = new JTextField();
/* 149 */     this.endDate = new JTextField();
/* 150 */     this.client = new JTextField();
/* 151 */     this.booking = new JTextField();
/* 152 */     this.roomType = new JTextField();
/*     */     
/* 154 */     this.clientL = new JLabel("Nombre cliente:");
/*     */     
/*     */ 
/* 157 */     this.bookingL = new JLabel("Num. Reserva:");
/*     */     
/* 159 */     this.beginDateL = new JLabel("Fecha entrada (dd/mm/yyyy):");
/*     */     
/* 161 */     this.endDateL = new JLabel("Fecha salida (dd/mm/yyyy):");
/*     */     
/* 163 */     this.roomTypeL = new JLabel("Tipo habitación:");
/*     */     
/*     */ 
/* 166 */     this.calculate.setAlignmentX(0.0F);
/*     */     
/*     */ 
/* 169 */     this.clientP.setLayout(new BoxLayout(this.clientP, 1));
/* 170 */     this.bookingP.setLayout(new BoxLayout(this.bookingP, 1));
/* 171 */     this.beginDateP.setLayout(new BoxLayout(this.beginDateP, 1));
/* 172 */     this.endDateP.setLayout(new BoxLayout(this.endDateP, 1));
/* 173 */     this.roomTypeP.setLayout(new BoxLayout(this.roomTypeP, 1));
/*     */     
/* 175 */     this.clientP.add(this.clientL);
/* 176 */     this.clientP.add(this.client);
/* 177 */     this.roomTypeP.add(this.roomTypeL);
/* 178 */     this.roomTypeP.add(this.roomType);
/* 179 */     this.bookingP.add(this.bookingL);
/* 180 */     this.bookingP.add(this.booking);
/* 181 */     this.beginDateP.add(this.beginDateL);
/* 182 */     this.beginDateP.add(this.beginDate);
/* 183 */     this.endDateP.add(this.endDateL);
/* 184 */     this.endDateP.add(this.endDate);
/* 185 */     this.bottom.add(this.clientP);
/* 186 */     this.bottom.add(this.roomTypeP);
/* 187 */     this.bottom.add(this.bookingP);
/* 188 */     this.bottom.add(this.beginDateP);
/* 189 */     this.bottom.add(this.endDateP);
/* 190 */     this.bottom.add(this.calculate);
/*     */     
/*     */ 
/* 193 */     this.calculate.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 197 */           PairDaysPrice[] calculate1 = new Calculator(MainFrame.this.client.getText(), MainFrame.this.roomType.getText(), MainFrame.this.beginDate.getText(), MainFrame.this.endDate.getText(), MainFrame.this.configuration).calculate();
/* 198 */           Object[][] newData = new Object[data.length + 1][data[0].length];
/* 199 */           for (int i = 0; i < data.length; i++) {
/* 200 */             System.arraycopy(data, 0, newData, 0, data[i].length);
/*     */           }
/*     */           
/* 203 */           double finalPrice = 0.0D;
/* 204 */           for (int i = 0; i < calculate1.length; i++) {
/* 205 */             finalPrice += calculate1[i].getDays() * calculate1[i].getPrice();
/*     */           }
/*     */           
/* 208 */           newData[data.length][0] = MainFrame.this.client;
/* 209 */           newData[data.length][1] = MainFrame.this.roomType;
/* 210 */           newData[data.length][2] = MainFrame.this.booking;
/* 211 */           newData[data.length][3] = MainFrame.this.beginDate;
/* 212 */           newData[data.length][4] = MainFrame.this.endDate;
/* 213 */           newData[data.length][9] = MainFrame.this.endDate;
/*     */         } catch (ParseException ex) {
/* 215 */           Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
/*     */         }
/*     */         
/*     */       }
/*     */       
/* 220 */     });
/* 221 */     this.general.setLayout(new BorderLayout());
/*     */     
/* 223 */     this.general.add(new JScrollPane(this.table), "Center");
/* 224 */     this.general.add(this.bottom, "South");
/*     */     
/* 226 */     getContentPane().add(this.general);
/*     */     
/* 228 */     setTitle("Turo");
/* 229 */     setDefaultCloseOperation(3);
/* 230 */     pack();
/* 231 */     setSize(new Dimension(800, 500));
/* 232 */     setLocationRelativeTo(null);
/* 233 */     setVisible(true);
/*     */   }
/*     */   
/*     */   public Configuration getConfiguration() {
/* 237 */     return this.configuration;
/*     */   }
/*     */ }


/* Location:              C:\Users\Jose\Documents\NetBeansProjects\Turo\build\classes\!\turo\MainFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */