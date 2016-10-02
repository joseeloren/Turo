package turo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class MainFrame
        extends JFrame {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu optionsMenu;
    private BoxLayout boxLayout;
    private JPanel top;
    private JPanel bottom;
    private JPanel general;
    private JButton calculate;
    private JTable table;
    private JTextField client;
    private JTextField booking;
    private JTextField beginDate;
    private JTextField endDate;
    private JTextField roomType;
    private JLabel roomTypeL;
    private JLabel clientL;
    private JLabel bookingL;
    private JLabel beginDateL;
    private JLabel endDateL;
    private JPanel roomTypeP;
    private JPanel clientP;
    private JPanel bookingP;
    private JPanel beginDateP;
    private JPanel endDateP;
    private JMenuItem option;
    private Object[][] data;
    private SeasonConfiguration configuration;

    public MainFrame() {

        this.configuration = new SeasonConfiguration();
        this.configuration.updateConfiguration();

        this.top = new JPanel();
        this.bottom = new JPanel();
        this.general = new JPanel();

        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("Archivo");
        this.optionsMenu = new JMenu("Configuración");

        this.optionsMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                new SeasonDialog(MainFrame.this);
            }

            public void menuDeselected(MenuEvent e) {
            }

            public void menuCanceled(MenuEvent e) {
            }
        });
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.optionsMenu);

        setJMenuBar(this.menuBar);

        this.calculate = new JButton("Calcular");

        String[] columnNames = {"Num. Reserva", "Cliente", "Tipo habitacion", "Fecha entrada", "Fecha salida", "Tipo descuento", "Porcentaje descuento", "Base imponible", "IGIC", "Total"};
        this.data = new Object[0][columnNames.length];

        this.table = new JTable(data, columnNames);
        this.table.setFillsViewportHeight(true);
        this.table.setAutoResizeMode(4);

        getRootPane().setDefaultButton(calculate);

        final AbstractTableModel abstractTableModel = new AbstractTableModel() {
            public String getColumnName(int column) {
                return columnNames[column];
            }

            public int findColumn(String columnName) {
                for (int i = 0; i < columnNames.length; i++) {
                    if (columnNames[i].equals(columnName)) {
                        return i;
                    }
                }
                return -1;
            }

            public boolean isCellEditable(int row, int col) {
                return false;
            }

            public int getRowCount() {
                if (data != null) {
                    return data.length;
                } else {
                    return 0;
                }
            }

            public int getColumnCount() {
                if (data != null && data.length >= 0) {
                    return columnNames.length;
                } else {
                    return 0;
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return data[rowIndex][columnIndex];
            }

        };
        this.table.setModel(abstractTableModel);

        this.clientP = new JPanel();

        this.bookingP = new JPanel();
        this.beginDateP = new JPanel();
        this.endDateP = new JPanel();
        this.roomTypeP = new JPanel();

        this.beginDate = new JTextField();
        this.endDate = new JTextField();
        this.client = new JTextField();
        this.booking = new JTextField();
        this.roomType = new JTextField();

        this.clientL = new JLabel("Nombre cliente:");

        this.bookingL = new JLabel("Num. Reserva:");

        this.beginDateL = new JLabel("Fecha entrada (dd/mm/yyyy):");

        this.endDateL = new JLabel("Fecha salida (dd/mm/yyyy):");

        this.roomTypeL = new JLabel("Tipo habitación:");

        this.clientP.setLayout(new BoxLayout(this.clientP, BoxLayout.Y_AXIS));
        this.bookingP.setLayout(new BoxLayout(this.bookingP, BoxLayout.Y_AXIS));
        this.beginDateP.setLayout(new BoxLayout(this.beginDateP, BoxLayout.Y_AXIS));
        this.endDateP.setLayout(new BoxLayout(this.endDateP, BoxLayout.Y_AXIS));
        this.roomTypeP.setLayout(new BoxLayout(this.roomTypeP, BoxLayout.Y_AXIS));

        this.clientP.add(this.clientL);
        this.clientP.add(this.client);
        this.roomTypeP.add(this.roomTypeL);
        this.roomTypeP.add(this.roomType);
        this.bookingP.add(this.bookingL);
        this.bookingP.add(this.booking);
        this.beginDateP.add(this.beginDateL);
        this.beginDateP.add(this.beginDate);
        this.endDateP.add(this.endDateL);
        this.endDateP.add(this.endDate);
        this.bottom.add(this.clientP);
        this.bottom.add(this.roomTypeP);
        this.bottom.add(this.bookingP);
        this.bottom.add(this.beginDateP);
        this.bottom.add(this.endDateP);
        this.bottom.add(this.calculate);

        this.calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PairDaysPrice[] calculate1 = new Calculator(MainFrame.this.client.getText(), MainFrame.this.roomType.getText(), MainFrame.this.beginDate.getText(), MainFrame.this.endDate.getText(), MainFrame.this.configuration).calculate();
                    Object[][] newData = new Object[(data != null) ? data.length + 1 : 1][columnNames.length];
                    for (int i = 0; i < ((data != null) ? data.length : 0); i++) {
                        System.arraycopy(data[i], 0, newData[i], 0, columnNames.length);
                    }

                    double finalPrice = 0;
                    for (int i = 0; i < calculate1.length; i++) {
                        finalPrice += calculate1[i].getDays() * calculate1[i].getPrice();
                    }

                    System.out.println("finalprice=" + finalPrice);

                    newData[newData.length - 1][0] = MainFrame.this.client.getText();
                    newData[newData.length - 1][1] = MainFrame.this.roomType.getText();
                    newData[newData.length - 1][2] = MainFrame.this.booking.getText();
                    newData[newData.length - 1][3] = MainFrame.this.beginDate.getText();
                    newData[newData.length - 1][4] = MainFrame.this.endDate.getText();
                    newData[newData.length - 1][7] = String.format("%.2f", finalPrice/1.07);
                    newData[newData.length - 1][8] = String.format("%.2f", finalPrice/1.07*0.07);
                    newData[newData.length - 1][9] = String.format("%.2f", finalPrice);
                    data = newData;

                    abstractTableModel.fireTableStructureChanged();

                } catch (ParseException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        this.general.setLayout(new BorderLayout());

        this.general.add(new JScrollPane(this.table), "Center");
        this.general.add(this.bottom, "South");

        getContentPane().add(this.general);

        setTitle("Turo");
        setDefaultCloseOperation(3);
        pack();
        setSize(new Dimension(1500,400));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public SeasonConfiguration getConfiguration() {
        return this.configuration;
    }
}
