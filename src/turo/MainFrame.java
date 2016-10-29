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

public class MainFrame extends JFrame {
    private JPanel general;

    private JButton calculate;
    private JButton config;

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

    public MainFrame() {

        configuration = new Configuration();
        configuration.updateConfiguration();

        general = new JPanel();
        general.setLayout(new GridLayout(10, 2));
        calculate = new JButton("Calcular");
        config = new JButton("Configuración");
        roomType = new JComboBox(configuration.getRoomsNames());
        beginDate = new JTextField(10);

        endDate = new JTextField(10);
        discount = new JTextField();
        discount.setEditable(false);
        discountPercent = new JTextField();
        discountPercent.setEditable(false);
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
        discountPercentL = new JLabel("Porcentaje descuento:");
        baseImponibleL = new JLabel("Base imponible:");
        igicL = new JLabel("IGIC:");
        totalL = new JLabel("Total:");

        config.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfigurationDialog(MainFrame.this);
            }
        }
        );

        getRootPane()
                .setDefaultButton(calculate);

        general.add(roomTypeL);
        general.add(roomType);
        general.add(beginDateL);
        general.add(beginDate);
        general.add(endDateL);
        general.add(endDate);
        general.add(alojamientoL);
        general.add(alojamiento);
        general.add(discountL);
        general.add(discount);
        general.add(discountPercentL);
        general.add(discountPercent);
        general.add(baseImponibleL);
        general.add(baseImponible);
        general.add(igicL);
        general.add(igic);
        general.add(totalL);
        general.add(total);
        general.add(config);
        general.add(calculate);

        getContentPane().add(general);
        this.calculate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    PairDaysPrice[] calculate1 = new Calculator((String) MainFrame.this.roomType.getSelectedItem(), MainFrame.this.beginDate.getText(), MainFrame.this.endDate.getText(), MainFrame.this.configuration).calculate();

                    double finalPrice = 0;
                    String aloja = "";
                    for (int i = 0; i < calculate1.length; i++) {
                        if (calculate1[i].getDays() > 0 || calculate1[i].getPrice() > 0) {
                            aloja += Integer.toString(calculate1[i].getDays()) + "D x " + Double.toString(calculate1[i].getPrice()) + "€ + ";
                            finalPrice += calculate1[i].getDays() * calculate1[i].getPrice();
                        }
                    }
                    if (aloja.length() > 1)
                        aloja = aloja.substring(0, aloja.length() - 2);

                    System.out.println("finalprice=" + finalPrice);
                    alojamiento.setText(aloja);
                    Double tax = Double.parseDouble(configuration.getTax())/100;
                    baseImponible.setText(String.format("%.2f euros", finalPrice / (1+(tax))));
                    igic.setText(String.format("%.2f euros", finalPrice / (1+tax) * (tax)));
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
