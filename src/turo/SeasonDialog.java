package turo;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class SeasonDialog
        extends JDialog {

    private JTable table;
    private JButton save;
    private JButton cancel;
    private JButton addSeason;
    private JButton removeSeason;
    private JButton addRoom;
    private JButton removeRoom;
    private JPanel buttons;
    private String[] columnNames;
    private Object[][] data;

    public SeasonDialog(final MainFrame frame) {
        super(frame, true);

        this.data = frame.getConfiguration().getData();
        this.columnNames = new String[this.data[0].length];
        for (int i = 1; i < this.columnNames.length; i++) {
            this.columnNames[i] = ("Temporada " + Integer.toString(i));
        }

        this.buttons = new JPanel();
        this.save = new JButton("Guardar");
        this.cancel = new JButton("Cancelar");
        this.addSeason = new JButton("Añadir temporada");
        this.removeSeason = new JButton("Quitar temporada");
        this.addRoom = new JButton("Añadir habitación");
        this.removeRoom = new JButton("Quitar habitación");

        this.buttons.setLayout(new BoxLayout(this.buttons, 0));
        this.buttons.add(this.addRoom);
        this.buttons.add(this.removeRoom);
        this.buttons.add(this.addSeason);
        this.buttons.add(this.removeSeason);
        this.buttons.add(this.save);
        this.buttons.add(this.cancel);

        this.table = new JTable();
        final AbstractTableModel abstractTableModel = new AbstractTableModel() {
            public String getColumnName(int column) {
                return SeasonDialog.this.columnNames[column];
            }

            public int findColumn(String columnName) {
                for (int i = 0; i < SeasonDialog.this.columnNames.length; i++) {
                    if (SeasonDialog.this.columnNames[i].equals(columnName)) {
                        return i;
                    }
                }
                return -1;
            }

            public boolean isCellEditable(int row, int col) {
                return (row != col) && (col != 0);
            }

            public int getRowCount() {
                return SeasonDialog.this.data.length;
            }

            public int getColumnCount() {
                return SeasonDialog.this.data[0].length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return SeasonDialog.this.data[rowIndex][columnIndex];
            }

        };
        this.table.setModel(abstractTableModel);

        this.addSeason.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] newColumnNames = new String[SeasonDialog.this.columnNames.length + 1];
                System.arraycopy(SeasonDialog.this.columnNames, 0, newColumnNames, 0, SeasonDialog.this.columnNames.length);
                newColumnNames[(newColumnNames.length - 1)] = ("Temporada " + Integer.toString(newColumnNames.length - 1));

                Object[][] newData = new Object[SeasonDialog.this.data.length][SeasonDialog.this.columnNames.length + 1];
                for (int i = 0; i < SeasonDialog.this.data.length; i++) {
                    System.arraycopy(SeasonDialog.this.data[i], 0, newData[i], 0, SeasonDialog.this.data[i].length);
                }
                SeasonDialog.this.columnNames = newColumnNames;
                SeasonDialog.this.data = newData;
                abstractTableModel.fireTableStructureChanged();
            }

        });
        this.removeSeason.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (SeasonDialog.this.data[0].length > 2) {
                    String[] newColumnNames = new String[SeasonDialog.this.columnNames.length - 1];
                    System.arraycopy(SeasonDialog.this.columnNames, 0, newColumnNames, 0, SeasonDialog.this.columnNames.length - 1);

                    Object[][] newData = new Object[SeasonDialog.this.data.length][SeasonDialog.this.columnNames.length - 1];
                    for (int i = 0; i < SeasonDialog.this.data.length; i++) {
                        System.arraycopy(SeasonDialog.this.data[i], 0, newData[i], 0, SeasonDialog.this.data[i].length - 1);
                    }
                    SeasonDialog.this.columnNames = newColumnNames;
                    SeasonDialog.this.data = newData;

                    abstractTableModel.fireTableStructureChanged();
                }

            }
        });
        this.addRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[][] newData = new Object[SeasonDialog.this.data.length + 1][SeasonDialog.this.columnNames.length];
                for (int i = 0; i < SeasonDialog.this.data.length; i++) {
                    System.arraycopy(SeasonDialog.this.data[i], 0, newData[i], 0, SeasonDialog.this.data[i].length);
                }
                newData[(newData.length - 1)][0] = "Nueva";
                for (int i = 1; i < newData[0].length; i++) {
                    newData[(newData.length - 1)][i] = "0,00";
                }
                SeasonDialog.this.data = newData;

                abstractTableModel.fireTableStructureChanged();
            }

        });
        this.removeRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (SeasonDialog.this.data.length > 2) {
                    Object[][] newData = new Object[SeasonDialog.this.data.length - 1][SeasonDialog.this.columnNames.length];
                    for (int i = 0; i < SeasonDialog.this.data.length - 1; i++) {
                        System.arraycopy(SeasonDialog.this.data[i], 0, newData[i], 0, SeasonDialog.this.data[i].length);
                    }
                    SeasonDialog.this.data = newData;

                    abstractTableModel.fireTableStructureChanged();
                }

            }
        });
        this.save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = new File("seasons.csv");

                try {
                    file.createNewFile();

                    FileWriter writer = new FileWriter(file);

                    for (int i = 0; i < SeasonDialog.this.data.length; i++) {
                        String line = "";
                        for (int j = 0; j < SeasonDialog.this.data[i].length; j++) {
                            line = line + SeasonDialog.this.data[i][j] + ";";
                        }

                        if (i == SeasonDialog.this.data.length - 1) {
                            writer.write(line);
                        } else {
                            writer.write(line + "\n");
                        }
                        writer.flush();
                    }
                    writer.close();
                    frame.getConfiguration().updateConfiguration();
                } catch (Exception localException) {
                }
                SeasonDialog.this.setVisible(false);
                SeasonDialog.this.dispose();
            }

        });
        this.cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }

        });
        setTitle("Configuración");

        JScrollPane scrollPane = new JScrollPane(this.table);
        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(scrollPane, "Center");
        getContentPane().add(this.buttons, "South");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}