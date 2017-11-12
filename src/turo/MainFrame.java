package turo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.*;
import javafx.scene.control.DatePicker;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class MainFrame extends JFrame {

    private JPanel general;

    private JButton calculate;

    private JComboBox roomType;
    private JDatePickerImpl beginDate;
    private JDatePickerImpl endDate;
    private JTextField discount;
    private JTextField discountPercent;
    private JTextField baseImponible;
    private JTextField igic;
    private JTextField total;
    private JTextField alojamiento;
    private JLabel roomTypeL;
    private JLabel beginDateL;
    private JLabel endDateL;
    private JLabel discountL;
    private JLabel discountPercentL;
    private JLabel alojamientoL;

    private JLabel baseImponibleL;
    private JLabel igicL;
    private JLabel totalL;

    private Configuration configuration;
    int previousLength = 0;

    public MainFrame() throws IOException {

        configuration = new Configuration();
        configuration.updateConfiguration();

        general = new JPanel();
        general.setLayout(new GridLayout(9, 2));
        calculate = new JButton("Calcular");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Configuración");
        JMenu menu2 = new JMenu("Importar/Exportar");
        JMenuItem imp = new JMenuItem("Importar");
        JMenuItem exp = new JMenuItem("Exportar");
        menu2.add(imp);
        menu2.add(exp);
        menuBar.add(menu);
        menuBar.add(menu2);
        this.setJMenuBar(menuBar);
        menu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                try {
                    configuration.updateConfiguration();
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                new ConfigurationDialog(MainFrame.this);
                System.out.println(Arrays.toString(configuration.getRoomsNames()));
                roomType.removeAllItems();
                for (String room : configuration.getRoomsNames()) {
                    roomType.addItem(room);
                }
                roomType.validate();
                roomType.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        exp.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                new ImportExportDialog(MainFrame.this, 1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        imp.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                new ImportExportDialog(MainFrame.this, 2);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        roomType = new JComboBox(configuration.getRoomsNames());
        discount = new JTextField();
        discountPercent = new JTextField();
        alojamiento = new JTextField();
        alojamiento.setEditable(false);
        baseImponible = new JTextField();
        baseImponible.setEditable(false);
        igic = new JTextField();
        igic.setEditable(false);
        total = new JTextField();
        total.setEditable(false);
        roomTypeL = new JLabel("Tipo habitacion:");
        alojamientoL = new JLabel("Alojamiento:");
        beginDateL = new JLabel("Fecha entrada (dd/mm/yy):");
        endDateL = new JLabel("Fecha salida (dd/mm/yy):");
        discountL = new JLabel("Tipo descuento:");
        discountPercentL = new JLabel("Descuento(%):");
        baseImponibleL = new JLabel("Base imponible:");
        igicL = new JLabel("IGIC:");
        totalL = new JLabel("Total:");

        UtilDateModel model = new UtilDateModel();
        UtilDateModel model2 = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

            private String datePattern = "dd/MM/yy";
            private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

            @Override
            public Object stringToValue(String text) throws ParseException {
                return dateFormatter.parseObject(text);
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value != null) {
                    Calendar cal = (Calendar) value;
                    return dateFormatter.format(cal.getTime());
                }

                return "";
            }

        }
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        beginDate = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);

        endDate = new JDatePickerImpl(datePanel2, new DateLabelFormatter());

        beginDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endDate.getModel().setMonth(beginDate.getModel().getMonth());
                endDate.getModel().setYear(beginDate.getModel().getYear());
            }
        });

        getRootPane()
                .setDefaultButton(calculate);

        general.add(roomTypeL);
        general.add(roomType);
        general.add(beginDateL);
        general.add(beginDate);
        general.add(endDateL);
        general.add(endDate);
        general.add(discountL);
        general.add(discount);
        general.add(discountPercentL);
        general.add(discountPercent);
        general.add(alojamientoL);
        general.add(alojamiento);
        general.add(baseImponibleL);
        general.add(baseImponible);
        general.add(igicL);
        general.add(igic);
        general.add(totalL);
        general.add(total);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(general, BorderLayout.CENTER);
        getContentPane().add(calculate, BorderLayout.SOUTH);

        this.calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (discount.getText().equals("Larga estancia") || discount.getText().equals("Incentivos permanentes")) {
                        discount.setText("");
                        discountPercent.setText("");
                    }
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    String bg = df.format(beginDate.getModel().getValue());
                    String ed = df.format(endDate.getModel().getValue());
                    PairDaysPricesDiscount daysPricesDiscount = new Calculator((String) MainFrame.this.roomType.getSelectedItem(), bg, ed, MainFrame.this.configuration).calculate();
                    PairDaysPrice[] calculate1 = daysPricesDiscount.getPairDaysPrices();

                    double finalPrice = Double.MAX_VALUE;
                    String aloja = "";
                    for (int i = 0; i < calculate1.length; i++) {
                        if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                            aloja += Integer.toString(calculate1[i].getDays()) + "D x " + Double.toString(calculate1[i].getPrice()) + "€ + ";
                        }
                    }

                    double calculopermanente = Double.MAX_VALUE;
                    double calculolarga = Double.MAX_VALUE;
                    double calculopersonalizado = Double.MAX_VALUE;

                    finalPrice = 0;
                    for (int i = 0; i < calculate1.length; i++) {
                        if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                            finalPrice += calculate1[i].getDays() * calculate1[i].getPrice();
                        }
                    }

                    if (!discount.getText().equals("")) {
                        calculopersonalizado = 0;
                        for (int i = 0; i < calculate1.length; i++) {
                            if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                                calculopersonalizado += calculate1[i].getDays() * calculate1[i].getPrice();
                            }
                        }

                        try {
                            calculopersonalizado = calculopersonalizado - calculopersonalizado * (Double.parseDouble(discountPercent.getText()) / 100);
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, "Tipo de descuento incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if (daysPricesDiscount.getDiscountType().equals("Incentivos permanentes")) {
                        System.out.println("Hay incentivos permantes");
                        String[] da = daysPricesDiscount.getDiscount()[1].split("=");
                        int dai = Math.abs(Integer.parseInt(da[0]) - Integer.parseInt(da[1]));
                        if (daysPricesDiscount.getDiscount()[4].equals("Ultimos")) {
                            while (dai != 0) {
                                dai--;
                                for (int i = calculate1.length - 1; i >= 0; i--) {
                                    if (calculate1[i].getDays() > 0) {
                                        System.out.println("Hay descupento de 7 dias?");
                                        calculate1[i].setDays(calculate1[i].getDays() - 1);
                                        break;
                                    }
                                }
                            }
                        } else if (daysPricesDiscount.getDiscount()[4].equals("Primeros")) {
                            while (dai != 0) {
                                dai--;
                                for (int i = 0; i < calculate1.length; i++) {
                                    if (calculate1[i].getDays() > 0) {
                                        calculate1[i].setDays(calculate1[i].getDays() - 1);
                                        break;
                                    }
                                }
                            }

                        }
                        calculopermanente = 0;
                        for (int i = 0; i < calculate1.length; i++) {
                            if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                                calculopermanente += calculate1[i].getDays() * calculate1[i].getPrice();
                            }
                        }
                    } else if (daysPricesDiscount.getDiscountType().equals("Larga estancia")) {
                        calculolarga = 0;
                        for (int i = 0; i < calculate1.length; i++) {
                            if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                                calculolarga += calculate1[i].getDays() * calculate1[i].getPrice();
                            }
                        }
                        double dis = Double.parseDouble(daysPricesDiscount.getDiscount()[1]);
                        calculolarga = calculolarga - (dis / 100) * calculolarga;
                    }

                    System.out.println("calculopermanente=" + calculopermanente);
                    System.out.println("calculolarga=" + calculolarga);
                    System.out.println("calculopersonalizado=" + calculopersonalizado);

                    finalPrice = Double.min(Double.min(calculolarga, calculopermanente), Double.min(calculopersonalizado, finalPrice));
                    if (finalPrice == calculopermanente) {
                        System.out.println("DEBUG");
                        System.out.println("daysPricesDiscount.getDiscount()[4]" + daysPricesDiscount.getDiscount()[4]);
                        System.out.println("daysPricesDiscount.getDiscount()[1]" + daysPricesDiscount.getDiscount()[1]);
                        System.out.println("DEBUG");
                        discountPercent.setText(daysPricesDiscount.getDiscount()[4] + " " + daysPricesDiscount.getDiscount()[1]);
                        discount.setText(daysPricesDiscount.getDiscountType());
                    } else if (finalPrice == calculolarga) {
                        discountPercent.setText(daysPricesDiscount.getDiscount()[1]);
                        discount.setText(daysPricesDiscount.getDiscountType());
                    } else if (finalPrice == calculopersonalizado) {
                        System.out.println("Calculo personalizado");
                    } else {
                        discountPercent.setText("");
                        discount.setText("");
                    }

                    if (aloja.length() > 1) {
                        aloja = aloja.substring(0, aloja.length() - 2);
                    }

                    alojamiento.setText(aloja);
                    Double tax = Double.parseDouble(configuration.getTax()) / 100;
                    baseImponible.setText(String.format("%.2f euros", finalPrice / (1 + (tax))));
                    igic.setText(String.format("%.2f euros", finalPrice / (1 + tax) * (tax)));
                    total.setText(String.format("%.2f euros", finalPrice));

                } catch (ParseException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

        setTitle("Turo");
        setIconImage(new ImageIcon("icon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }
}
