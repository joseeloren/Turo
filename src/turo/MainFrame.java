package turo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.Arrays;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class MainFrame extends JFrame {

    private JPanel general;

    private JButton calculate;

    private JComboBox roomType;
    private JTextField beginDate;
    private JTextField endDate;
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

    public MainFrame() {

        configuration = new Configuration();
        configuration.updateConfiguration();

        general = new JPanel();
        general.setLayout(new GridLayout(9, 2));
        calculate = new JButton("Calcular");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Configuración");
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        menu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                new ConfigurationDialog(MainFrame.this);
                configuration.updateConfiguration();
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
        roomType = new JComboBox(configuration.getRoomsNames());
        beginDate = new JTextField(10);

        endDate = new JTextField(10);
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
        beginDateL = new JLabel("Fecha entrada (dd/mm/yyyy):");
        endDateL = new JLabel("Fecha salida (dd/mm/yyyy):");
        discountL = new JLabel("Tipo descuento:");
        discountPercentL = new JLabel("Descuento:");
        baseImponibleL = new JLabel("Base imponible:");
        igicL = new JLabel("IGIC:");
        totalL = new JLabel("Total:");

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
                    if (discount.getText().equals("Larga estancia") ||discount.getText().equals("Incentivos permantes")) {
                        discount.setText("");
                        discountPercent.setText("");
                    }
                    PairDaysPricesDiscount daysPricesDiscount = new Calculator((String) MainFrame.this.roomType.getSelectedItem(), MainFrame.this.beginDate.getText(), MainFrame.this.endDate.getText(), MainFrame.this.configuration).calculate();
                    PairDaysPrice[] calculate1 = daysPricesDiscount.getPairDaysPrices();

                    double finalPrice = 0;
                    String aloja = "";
                    for (int i = 0; i < calculate1.length; i++) {
                        if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                            aloja += Integer.toString(calculate1[i].getDays()) + "D x " + Double.toString(calculate1[i].getPrice()) + "€ + ";
                        }
                    }

                    if ((discount.getText().equals("Incentivos permanentes") || discount.getText().equals("Larga estancia") || discount.getText().equals("")) && daysPricesDiscount.getDiscountType().equals("Incentivos permanentes")) {
                        String[] da = daysPricesDiscount.getDiscount()[1].split("=");
                        int dai = Math.abs(Integer.parseInt(da[0]) - Integer.parseInt(da[1]));
                        if (daysPricesDiscount.getDiscount()[4].equals("Ultimos")) {
                            while (dai != 0) {
                                dai--;
                                for (int i = calculate1.length - 1; i >= 0; i--) {
                                    if (calculate1[i].getDays() > 0) {
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
                        discountPercent.setText(daysPricesDiscount.getDiscount()[4] + " " + daysPricesDiscount.getDiscount()[1]);
                        for (int i = 0; i < calculate1.length; i++) {
                            if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                                finalPrice += calculate1[i].getDays() * calculate1[i].getPrice();
                            }
                        }
                        discount.setText(daysPricesDiscount.getDiscountType());
                    } else if ((discount.getText().equals("Incentivos permanentes") || discount.getText().equals("Larga estancia") || discount.getText().equals("")) && daysPricesDiscount.getDiscountType().equals("Larga estancia")) {
                        for (int i = 0; i < calculate1.length; i++) {
                            if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                                finalPrice += calculate1[i].getDays() * calculate1[i].getPrice();
                            }
                        }
                        double dis = Double.parseDouble(daysPricesDiscount.getDiscount()[1]);
                        discountPercent.setText(daysPricesDiscount.getDiscount()[1]);
                        finalPrice = finalPrice - (dis / 100) * finalPrice;
                        discount.setText(daysPricesDiscount.getDiscountType());
                    } else {
                        for (int i = 0; i < calculate1.length; i++) {
                            if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                                finalPrice += calculate1[i].getDays() * calculate1[i].getPrice();
                            }
                        }

                        if (!discount.getText().equals("Incentivos permanentes") && !discount.getText().equals("Larga estancia") && !discount.getText().equals("")) {

                            finalPrice = finalPrice - finalPrice * ((double) Integer.parseInt(discountPercent.getText()) / 100);

                        }

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
        setIconImage((new ImageIcon(getClass().getClassLoader().getResource("resources/icon.png")).getImage()));
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
