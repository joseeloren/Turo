/*     */ package turo;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.AbstractTableModel;
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
/*     */ public class ConfigurationDialog
/*     */   extends JDialog
/*     */ {
/*     */   private JTable table;
/*     */   private JButton save;
/*     */   private JButton cancel;
/*     */   private JButton addSeason;
/*     */   private JButton removeSeason;
/*     */   private JButton addRoom;
/*     */   private JButton removeRoom;
/*     */   private JPanel buttons;
/*     */   private String[] columnNames;
/*     */   private Object[][] data;
/*     */   
/*     */   public ConfigurationDialog(final MainFrame frame)
/*     */   {
/*  49 */     super(frame, true);
/*     */     
/*  51 */     this.data = frame.getConfiguration().getData();
/*  52 */     this.columnNames = new String[this.data[0].length];
/*  53 */     for (int i = 1; i < this.columnNames.length; i++) {
/*  54 */       this.columnNames[i] = ("Temporada " + Integer.toString(i));
/*     */     }
/*     */     
/*  57 */     this.buttons = new JPanel();
/*  58 */     this.save = new JButton("Guardar");
/*  59 */     this.cancel = new JButton("Cancelar");
/*  60 */     this.addSeason = new JButton("Añadir temporada");
/*  61 */     this.removeSeason = new JButton("Quitar temporada");
/*  62 */     this.addRoom = new JButton("Añadir habitación");
/*  63 */     this.removeRoom = new JButton("Quitar habitación");
/*     */     
/*  65 */     this.buttons.setLayout(new BoxLayout(this.buttons, 0));
/*  66 */     this.buttons.add(this.addRoom);
/*  67 */     this.buttons.add(this.removeRoom);
/*  68 */     this.buttons.add(this.addSeason);
/*  69 */     this.buttons.add(this.removeSeason);
/*  70 */     this.buttons.add(this.save);
/*  71 */     this.buttons.add(this.cancel);
/*     */     
/*  73 */     this.table = new JTable();
/*  74 */     final AbstractTableModel abstractTableModel = new AbstractTableModel()
/*     */     {
/*     */       public String getColumnName(int column) {
/*  77 */         return ConfigurationDialog.this.columnNames[column];
/*     */       }
/*     */       
/*     */       public int findColumn(String columnName)
/*     */       {
/*  82 */         for (int i = 0; i < ConfigurationDialog.this.columnNames.length; i++) {
/*  83 */           if (ConfigurationDialog.this.columnNames[i].equals(columnName)) {
/*  84 */             return i;
/*     */           }
/*     */         }
/*  87 */         return -1;
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int row, int col)
/*     */       {
/*  92 */         return (row != col) && (col != 0);
/*     */       }
/*     */       
/*     */       public int getRowCount()
/*     */       {
/*  97 */         return ConfigurationDialog.this.data.length;
/*     */       }
/*     */       
/*     */ 
/*     */       public int getColumnCount()
/*     */       {
/* 103 */         return ConfigurationDialog.this.data[0].length;
/*     */       }
/*     */       
/*     */       public Object getValueAt(int rowIndex, int columnIndex)
/*     */       {
/* 108 */         return ConfigurationDialog.this.data[rowIndex][columnIndex];
/*     */       }
/*     */       
/* 111 */     };
/* 112 */     this.table.setModel(abstractTableModel);
/*     */     
/* 114 */     this.addSeason.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 117 */         String[] newColumnNames = new String[ConfigurationDialog.this.columnNames.length + 1];
/* 118 */         System.arraycopy(ConfigurationDialog.this.columnNames, 0, newColumnNames, 0, ConfigurationDialog.this.columnNames.length);
/* 119 */         newColumnNames[(newColumnNames.length - 1)] = ("Temporada " + Integer.toString(newColumnNames.length - 1));
/*     */         
/* 121 */         Object[][] newData = new Object[ConfigurationDialog.this.data.length][ConfigurationDialog.this.columnNames.length + 1];
/* 122 */         for (int i = 0; i < ConfigurationDialog.this.data.length; i++) {
/* 123 */           System.arraycopy(ConfigurationDialog.this.data[i], 0, newData[i], 0, ConfigurationDialog.this.data[i].length);
/*     */         }
/* 125 */         ConfigurationDialog.this.columnNames = newColumnNames;
/* 126 */         ConfigurationDialog.this.data = newData;
/*     */         
/* 128 */         abstractTableModel.fireTableStructureChanged();
/*     */       }
/*     */       
/* 131 */     });
/* 132 */     this.removeSeason.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 135 */         if (ConfigurationDialog.this.data[0].length > 2) {
/* 136 */           String[] newColumnNames = new String[ConfigurationDialog.this.columnNames.length - 1];
/* 137 */           System.arraycopy(ConfigurationDialog.this.columnNames, 0, newColumnNames, 0, ConfigurationDialog.this.columnNames.length - 1);
/*     */           
/* 139 */           Object[][] newData = new Object[ConfigurationDialog.this.data.length][ConfigurationDialog.this.columnNames.length - 1];
/* 140 */           for (int i = 0; i < ConfigurationDialog.this.data.length; i++) {
/* 141 */             System.arraycopy(ConfigurationDialog.this.data[i], 0, newData[i], 0, ConfigurationDialog.this.data[i].length - 1);
/*     */           }
/* 143 */           ConfigurationDialog.this.columnNames = newColumnNames;
/* 144 */           ConfigurationDialog.this.data = newData;
/*     */           
/* 146 */           abstractTableModel.fireTableStructureChanged();
/*     */         }
/*     */         
/*     */       }
/* 150 */     });
/* 151 */     this.addRoom.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 154 */         Object[][] newData = new Object[ConfigurationDialog.this.data.length + 1][ConfigurationDialog.this.columnNames.length];
/* 155 */         for (int i = 0; i < ConfigurationDialog.this.data.length; i++) {
/* 156 */           System.arraycopy(ConfigurationDialog.this.data[i], 0, newData[i], 0, ConfigurationDialog.this.data[i].length);
/*     */         }
/* 158 */         newData[(newData.length - 1)][0] = "Nueva";
/* 159 */         for (int i = 1; i < newData[0].length; i++) {
/* 160 */           newData[(newData.length - 1)][i] = "0,00";
/*     */         }
/* 162 */         ConfigurationDialog.this.data = newData;
/*     */         
/* 164 */         abstractTableModel.fireTableStructureChanged();
/*     */       }
/*     */       
/* 167 */     });
/* 168 */     this.removeRoom.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 171 */         if (ConfigurationDialog.this.data.length > 2) {
/* 172 */           Object[][] newData = new Object[ConfigurationDialog.this.data.length - 1][ConfigurationDialog.this.columnNames.length];
/* 173 */           for (int i = 0; i < ConfigurationDialog.this.data.length - 1; i++) {
/* 174 */             System.arraycopy(ConfigurationDialog.this.data[i], 0, newData[i], 0, ConfigurationDialog.this.data[i].length);
/*     */           }
/* 176 */           ConfigurationDialog.this.data = newData;
/*     */           
/* 178 */           abstractTableModel.fireTableStructureChanged();
/*     */         }
/*     */         
/*     */       }
/* 182 */     });
/* 183 */     this.save.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 187 */         File file = new File("seasons.csv");
/*     */         
/*     */         try
/*     */         {
/* 191 */           file.createNewFile();
/*     */           
/* 193 */           FileWriter writer = new FileWriter(file);
/*     */           
/* 195 */           for (int i = 0; i < ConfigurationDialog.this.data.length; i++) {
/* 196 */             String line = "";
/* 197 */             for (int j = 0; j < ConfigurationDialog.this.data[i].length; j++) {
/* 198 */               line = line + ConfigurationDialog.this.data[i][j] + ";";
/*     */             }
/*     */             
/* 201 */             if (i == ConfigurationDialog.this.data.length - 1) {
/* 202 */               writer.write(line);
/*     */             } else
/* 204 */               writer.write(line + "\n");
/* 205 */             writer.flush();
/*     */           }
/* 207 */           writer.close();
/* 208 */           frame.getConfiguration().updateConfiguration();
/*     */         } catch (Exception localException) {}
/* 210 */         ConfigurationDialog.this.setVisible(false);
/* 211 */         ConfigurationDialog.this.dispose();
/*     */       }
/*     */       
/* 214 */     });
/* 215 */     this.cancel.addActionListener(new ActionListener()
/*     */     {
/*     */ 
/*     */       public void actionPerformed(ActionEvent e) {}
/*     */ 
/*     */ 
/* 221 */     });
/* 222 */     setTitle("Configuración");
/*     */     
/* 224 */     JScrollPane scrollPane = new JScrollPane(this.table);
/* 225 */     getContentPane().setLayout(new BorderLayout());
/*     */     
/* 227 */     getContentPane().add(scrollPane, "Center");
/* 228 */     getContentPane().add(this.buttons, "South");
/* 229 */     pack();
/* 230 */     setLocationRelativeTo(null);
/* 231 */     setVisible(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\Jose\Documents\NetBeansProjects\Turo\build\classes\!\turo\ConfigurationDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */