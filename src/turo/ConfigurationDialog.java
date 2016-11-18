package turo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.File;
import java.io.FileWriter;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class ConfigurationDialog
        extends JDialog {

    private JTable seasons;
    private JTable permanent;
    private JTable longEstance;
    private JButton save;
    private JButton cancel;
    private JButton addSeason;
    private JButton removeSeason;
    private JButton addRoom;
    private JButton removeRoom;
    private JButton addPermanent;
    private JButton removePermanent;
    private JButton addLongEstance;
    private JButton removeLongEstance;
    private JPanel seasonsButtons;
    private JPanel permanentButtons;
    private JPanel longEstanceButtons;
    private JPanel endButtons;
    private String[] seasonsColumnNames;
    private final String[] permanentColumnNames = {"Orden", "Dura", "Calculo", "Fecha", "Días", "Duración"};
    private final String[] longEstanceColumnNames = {"Orden", "Descuento", "Estancia", "Calculo", "Fecha"};
    private Object[][] seasonsData;
    private Object[][] permanentData;
    private Object[][] longEstanceData;
    private JLabel seasonsText;
    private JLabel permanentText;
    private JLabel longEstanceText;
    private JLabel taxText;
    private JTextField tax;
    private JPanel taxButtons;
    private JComboBox roomsCombo;

    public ConfigurationDialog(final MainFrame frame) {
        super(frame, true);
        
        this.taxButtons = new JPanel();
        this.seasonsText = new JLabel("PRECIOS POR TEMPORADA");
        this.permanentText = new JLabel("INCENTIVOS PERMANENTES");
        this.longEstanceText = new JLabel("DESCUENTOS DE LARGA INSTANCIA");
        this.taxText = new JLabel("IMPUESTO");
        this.tax = new JTextField();
        this.permanentData = frame.getConfiguration().getPermanentData();

        this.longEstanceData = frame.getConfiguration().getLongEstanceData();

        this.seasonsData = frame.getConfiguration().getSeasonsData();
        this.tax.setText(frame.getConfiguration().getTax());
        this.seasonsColumnNames = new String[this.seasonsData[0].length];
        this.seasonsColumnNames[0] = "";
        for (int i = 1; i < this.seasonsColumnNames.length; i++) {
            this.seasonsColumnNames[i] = ("Temporada " + Integer.toString(i));
        }

        this.seasonsButtons = new JPanel();
        this.permanentButtons = new JPanel();
        this.longEstanceButtons = new JPanel();
        this.endButtons = new JPanel();

        this.save = new JButton("Guardar");
        this.cancel = new JButton("Cancelar");
        this.addSeason = new JButton("Añadir temporada");
        this.removeSeason = new JButton("Quitar temporada");
        this.addRoom = new JButton("Añadir habitación");
        this.removeRoom = new JButton("Quitar habitación");
        this.addPermanent = new JButton("Añadir incentivo");
        this.removePermanent = new JButton("Quitar incentivo");
        this.addLongEstance = new JButton("Añadir instancia larga");
        this.removeLongEstance = new JButton("Quitar instancia larga");

        this.seasonsButtons.setLayout(new BoxLayout(this.seasonsButtons, BoxLayout.X_AXIS));
        this.permanentButtons.setLayout(new BoxLayout(this.permanentButtons, BoxLayout.X_AXIS));
        this.longEstanceButtons.setLayout(new BoxLayout(this.longEstanceButtons, BoxLayout.X_AXIS));
        this.endButtons.setLayout(new BoxLayout(this.endButtons, BoxLayout.X_AXIS));
        this.taxButtons.setLayout(new BoxLayout(this.taxButtons, BoxLayout.X_AXIS));
        this.taxButtons.add(taxText);
        this.taxButtons.add(tax);
        this.permanentButtons.add(this.addPermanent);
        this.permanentButtons.add(this.removePermanent);
        this.longEstanceButtons.add(addLongEstance);
        this.longEstanceButtons.add(removeLongEstance);
        this.seasonsButtons.add(this.addRoom);
        this.seasonsButtons.add(this.removeRoom);
        this.seasonsButtons.add(this.addSeason);
        this.seasonsButtons.add(this.removeSeason);
        this.endButtons.add(this.save);
        this.endButtons.add(this.cancel);

        permanent = new JTable();
        seasons = new JTable();
        longEstance = new JTable();
        
        

        final AbstractTableModel abstractTableModel = new AbstractTableModel() {
            public String getColumnName(int column) {
                return ConfigurationDialog.this.seasonsColumnNames[column];
            }

            public int findColumn(String columnName) {
                for (int i = 0; i < ConfigurationDialog.this.seasonsColumnNames.length; i++) {
                    if (ConfigurationDialog.this.seasonsColumnNames[i].equals(columnName)) {
                        return i;
                    }
                }
                return -1;
            }

            public boolean isCellEditable(int row, int col) {
                if (row == 0 && col == 0) {
                    return false;
                }
                return true;

            }

            public int getRowCount() {
                return ConfigurationDialog.this.seasonsData.length;
            }

            public int getColumnCount() {
                return ConfigurationDialog.this.seasonsData[0].length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return ConfigurationDialog.this.seasonsData[rowIndex][columnIndex];
            }

            public void setValueAt(Object value, int row, int col) {
                seasonsData[row][col] = value;
                fireTableCellUpdated(row, col);
            }

        };
        abstractTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                System.out.println("tableChanged");
                roomsCombo = new JComboBox();
                for (int i = 1; i < seasonsData.length; i++) {
                    roomsCombo.addItem(seasonsData[i][0]);
                }
                roomsCombo.addItem("Todos");
                TableColumn c0 = permanent.getColumnModel().getColumn(0);
                c0.setCellEditor(new DefaultCellEditor(roomsCombo));
                TableColumn c1 = longEstance.getColumnModel().getColumn(0);
                c1.setCellEditor(new DefaultCellEditor(roomsCombo));
            }
        });
        this.seasons.setModel(abstractTableModel);

        final AbstractTableModel abstractTableModel2 = new AbstractTableModel() {
            public String getColumnName(int column) {
                return ConfigurationDialog.this.permanentColumnNames[column];
            }

            public int findColumn(String columnName) {
                for (int i = 0; i < ConfigurationDialog.this.permanentColumnNames.length; i++) {
                    if (ConfigurationDialog.this.permanentColumnNames[i].equals(columnName)) {
                        return i;
                    }
                }
                return -1;
            }

            public boolean isCellEditable(int row, int col) {
                return true;
            }

            public int getRowCount() {
                return ConfigurationDialog.this.permanentData.length;
            }

            public int getColumnCount() {
                return ConfigurationDialog.this.permanentData[0].length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {

                return ConfigurationDialog.this.permanentData[rowIndex][columnIndex];

            }

            public void setValueAt(Object value, int row, int col) {

                permanentData[row][col] = value;
                fireTableCellUpdated(row, col);

            }

        };
        this.permanent.setModel(abstractTableModel2);

        String[] roomsNames = frame.getConfiguration().getRoomsNames();
        
        roomsCombo = new JComboBox();

        for (String roomsName : roomsNames) {
            roomsCombo.addItem(roomsName);
        }
        roomsCombo.addItem("Todos");

        final AbstractTableModel abstractTableModel3 = new AbstractTableModel() {
            public String getColumnName(int column) {
                return ConfigurationDialog.this.longEstanceColumnNames[column];
            }

            public int findColumn(String columnName) {
                for (int i = 0; i < ConfigurationDialog.this.longEstanceColumnNames.length; i++) {
                    if (ConfigurationDialog.this.longEstanceColumnNames[i].equals(columnName)) {
                        return i;
                    }
                }
                return -1;
            }

            public boolean isCellEditable(int row, int col) {
                return true;
            }

            public int getRowCount() {
                return ConfigurationDialog.this.longEstanceData.length;
            }

            public int getColumnCount() {
                return ConfigurationDialog.this.longEstanceData[0].length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return ConfigurationDialog.this.longEstanceData[rowIndex][columnIndex];
            }

            public void setValueAt(Object value, int row, int col) {
                longEstanceData[row][col] = value;
                fireTableCellUpdated(row, col);
            }

        };
        this.longEstance.setModel(abstractTableModel3);

        TableColumn c0 = permanent.getColumnModel().getColumn(0);
        c0.setCellEditor(new DefaultCellEditor(roomsCombo));
        TableColumn c1 = longEstance.getColumnModel().getColumn(0);
        c1.setCellEditor(new DefaultCellEditor(roomsCombo));

        TableColumn c01 = permanent.getColumnModel().getColumn(2);

        JComboBox comboBox2 = new JComboBox();

        comboBox2.addItem("A la llegada");
        comboBox2.addItem("A la salida");

        c01.setCellEditor(new DefaultCellEditor(comboBox2));

        TableColumn c02 = permanent.getColumnModel().getColumn(4);

        JComboBox comboBox3 = new JComboBox();

        comboBox3.addItem("Ultimos");
        comboBox3.addItem("Primeros");

        c02.setCellEditor(new DefaultCellEditor(comboBox3));

        

        TableColumn c2 = longEstance.getColumnModel().getColumn(3);

        JComboBox comboBox5 = new JComboBox();

        comboBox5.addItem("A la llegada");
        comboBox5.addItem("A la salida");

        c2.setCellEditor(new DefaultCellEditor(comboBox5));

        
        
        this.addSeason.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] newColumnNames = new String[ConfigurationDialog.this.seasonsColumnNames.length + 1];
                System.arraycopy(ConfigurationDialog.this.seasonsColumnNames, 0, newColumnNames, 0, ConfigurationDialog.this.seasonsColumnNames.length);
                newColumnNames[(newColumnNames.length - 1)] = ("Temporada " + Integer.toString(newColumnNames.length - 1));

                Object[][] newData = new Object[ConfigurationDialog.this.seasonsData.length][ConfigurationDialog.this.seasonsColumnNames.length + 1];
                for (int i = 0; i < ConfigurationDialog.this.seasonsData.length; i++) {
                    System.arraycopy(ConfigurationDialog.this.seasonsData[i], 0, newData[i], 0, ConfigurationDialog.this.seasonsData[i].length);
                }
                ConfigurationDialog.this.seasonsColumnNames = newColumnNames;
                ConfigurationDialog.this.seasonsData = newData;

                ((AbstractTableModel) seasons.getModel()).fireTableStructureChanged();
            }

        });

        this.removeSeason.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ConfigurationDialog.this.seasonsData[0].length > 2) {
                    String[] newColumnNames = new String[ConfigurationDialog.this.seasonsData[0].length - 1];
                    System.arraycopy(ConfigurationDialog.this.seasonsColumnNames, 0, newColumnNames, 0, ConfigurationDialog.this.seasonsData[0].length - 1);

                    Object[][] newData = new Object[ConfigurationDialog.this.seasonsData.length][ConfigurationDialog.this.seasonsData[0].length - 1];
                    for (int i = 0; i < ConfigurationDialog.this.seasonsData.length; i++) {
                        System.arraycopy(ConfigurationDialog.this.seasonsData[i], 0, newData[i], 0, ConfigurationDialog.this.seasonsData[i].length - 1);
                    }
                    ConfigurationDialog.this.seasonsColumnNames = newColumnNames;
                    ConfigurationDialog.this.seasonsData = newData;

                    ((AbstractTableModel) seasons.getModel()).fireTableStructureChanged();
                }

            }
        });
        this.addPermanent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[][] newData = new Object[ConfigurationDialog.this.permanentData.length + 1][ConfigurationDialog.this.permanentColumnNames.length];
                for (int i = 0; i < ConfigurationDialog.this.permanentData.length; i++) {
                    System.arraycopy(ConfigurationDialog.this.permanentData[i], 0, newData[i], 0, ConfigurationDialog.this.permanentData[i].length);
                }
                ConfigurationDialog.this.permanentData = newData;

                ((AbstractTableModel) permanent.getModel()).fireTableDataChanged();
            }

        });
        this.addLongEstance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[][] newData = new Object[ConfigurationDialog.this.longEstanceData.length + 1][ConfigurationDialog.this.longEstanceColumnNames.length];
                for (int i = 0; i < ConfigurationDialog.this.longEstanceData.length; i++) {
                    System.arraycopy(ConfigurationDialog.this.longEstanceData[i], 0, newData[i], 0, ConfigurationDialog.this.longEstanceData[i].length);
                }
                ConfigurationDialog.this.longEstanceData = newData;

                ((AbstractTableModel) longEstance.getModel()).fireTableDataChanged();
            }

        });
        this.addRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[][] newData = new Object[ConfigurationDialog.this.seasonsData.length + 1][ConfigurationDialog.this.seasonsColumnNames.length];
                System.out.println(ConfigurationDialog.this.seasonsData.length);
                for (int i = 0; i < ConfigurationDialog.this.seasonsData.length; i++) {
                    System.arraycopy(ConfigurationDialog.this.seasonsData[i], 0, newData[i], 0, ConfigurationDialog.this.seasonsData[i].length);
                }
                newData[(newData.length - 1)][0] = "Nueva";
                for (int i = 1; i < newData[0].length; i++) {
                    newData[(newData.length - 1)][i] = "0,00";
                }
                ConfigurationDialog.this.seasonsData = newData;

                ((AbstractTableModel) seasons.getModel()).fireTableDataChanged();

            }

        });
        this.removeRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ConfigurationDialog.this.seasonsData.length > 2) {
                    Object[][] newData = new Object[ConfigurationDialog.this.seasonsData.length - 1][ConfigurationDialog.this.seasonsColumnNames.length];
                    for (int i = 0; i < ConfigurationDialog.this.seasonsData.length - 1; i++) {
                        System.arraycopy(ConfigurationDialog.this.seasonsData[i], 0, newData[i], 0, ConfigurationDialog.this.seasonsData[i].length);
                    }
                    ConfigurationDialog.this.seasonsData = newData;

                    ((AbstractTableModel) seasons.getModel()).fireTableDataChanged();
                }
            }
        });
        this.removeLongEstance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ConfigurationDialog.this.longEstanceData.length > 0) {
                    Object[][] newData = new Object[ConfigurationDialog.this.longEstanceData.length - 1][ConfigurationDialog.this.longEstanceColumnNames.length];
                    for (int i = 0; i < ConfigurationDialog.this.longEstanceData.length - 1; i++) {
                        System.arraycopy(ConfigurationDialog.this.longEstanceData[i], 0, newData[i], 0, ConfigurationDialog.this.longEstanceData[i].length);
                    }
                    ConfigurationDialog.this.longEstanceData = newData;

                    ((AbstractTableModel) longEstance.getModel()).fireTableDataChanged();

                }

            }
        });
        this.removePermanent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ConfigurationDialog.this.permanentData.length > 0) {
                    Object[][] newData = new Object[ConfigurationDialog.this.permanentData.length - 1][ConfigurationDialog.this.permanentColumnNames.length];
                    for (int i = 0; i < ConfigurationDialog.this.permanentData.length - 1; i++) {
                        System.arraycopy(ConfigurationDialog.this.permanentData[i], 0, newData[i], 0, ConfigurationDialog.this.permanentData[i].length);
                    }
                    ConfigurationDialog.this.permanentData = newData;

                    ((AbstractTableModel) permanent.getModel()).fireTableDataChanged();
                }

            }
        });
        this.save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File f = new File(System.getProperty("java.class.path"));
                File dir = f.getAbsoluteFile().getParentFile();
                String path = dir.toString();

                File fileR = new File(path + "/longEstance.csv");

                try {
                    fileR.createNewFile();

                    FileWriter writer = new FileWriter(fileR);

                    for (int i = 0; i < ConfigurationDialog.this.longEstanceData.length; i++) {
                        String line = "";
                        for (int j = 0; j < ConfigurationDialog.this.longEstanceData[i].length; j++) {

                            line = line + ConfigurationDialog.this.longEstanceData[i][j] + ";";
                        }

                        if (line.indexOf("null") < 0) {
                            writer.write(line + "\n");
                        }

                        writer.flush();
                    }
                    writer.close();
                } catch (Exception localException) {
                }

                File file0 = new File(path + "/permanent.csv");

                try {
                    file0.createNewFile();

                    FileWriter writer = new FileWriter(file0);

                    for (int i = 0; i < ConfigurationDialog.this.permanentData.length; i++) {
                        String line = "";
                        for (int j = 0; j < ConfigurationDialog.this.permanentData[i].length; j++) {
                            line = line + ConfigurationDialog.this.permanentData[i][j] + ";";
                        }

                        if (line.indexOf("null") < 0) {
                            writer.write(line + "\n");
                        }

                        writer.flush();
                    }
                    writer.close();
                } catch (Exception localException) {
                }

                File file = new File(path + "/seasons.csv");

                try {
                    file.createNewFile();

                    FileWriter writer = new FileWriter(file);

                    for (int i = 0; i < ConfigurationDialog.this.seasonsData.length; i++) {
                        String line = "";
                        for (int j = 0; j < ConfigurationDialog.this.seasonsData[i].length; j++) {
                            line = line + ConfigurationDialog.this.seasonsData[i][j] + ";";
                        }

                        if (i == ConfigurationDialog.this.seasonsData.length - 1) {
                            writer.write(line);
                        } else {
                            writer.write(line + "\n");
                        }
                        writer.flush();
                    }
                    writer.close();
                } catch (Exception localException) {
                }

                File file2 = new File(path + "/tax.csv");
                

                try {
                    file2.createNewFile();

                    FileWriter writer = new FileWriter(file2);

                    writer.write(ConfigurationDialog.this.tax.getText() + "\n");
                    writer.flush();
                    writer.close();
                } catch (Exception localException) {
                }
                frame.getConfiguration().updateConfiguration();
                ConfigurationDialog.this.setVisible(false);
                ConfigurationDialog.this.dispose();
            }

        });
        this.cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }

        });
        setTitle("Configuración");

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        seasonsButtons.setBorder(new EmptyBorder(0, 0, 15, 0));
        permanentButtons.setBorder(new EmptyBorder(0, 0, 15, 0));
        longEstanceButtons.setBorder(new EmptyBorder(0, 0, 15, 0));
        taxButtons.setBorder(new EmptyBorder(0, 0, 15, 0));

        permanent.setPreferredScrollableViewportSize(new Dimension((int) permanent.getSize().getWidth(), 80));
        longEstance.setPreferredScrollableViewportSize(new Dimension((int) permanent.getSize().getWidth(), 80));
        seasons.setPreferredScrollableViewportSize(new Dimension((int) permanent.getSize().getWidth(), 80));

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        p1.add(seasonsText);
        p2.add(permanentText);
        p3.add(longEstanceText);

        getContentPane().add(p1);
        getContentPane().add(new JScrollPane(seasons));
        getContentPane().add(this.seasonsButtons);
        getContentPane().add(p2);
        getContentPane().add(new JScrollPane(permanent));
        getContentPane().add(permanentButtons);
        getContentPane().add(p3);
        getContentPane().add(new JScrollPane(longEstance));
        getContentPane().add(longEstanceButtons);
        getContentPane().add(taxButtons);
        getContentPane().add(endButtons);
        getRootPane()
                .setDefaultButton(save);

        pack();
        

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
