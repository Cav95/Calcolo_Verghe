package Frame;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import p12.exercise.*;

public class DisplayImplementation {
    public static final String SEP = File.separator;
    public static final String FILE_NAME ="app" + SEP + "main" + SEP + "resources" + SEP + "prova.txt"; // app\src\main\resources\prova.txt

    public static void display() {

        MultiQueue<Integer, String> prova = new MultiQueueImpl<>();

        final JTextField tfLenght = new JTextField("Lunghezza", 10);
        final JTextField tfQuantity = new JTextField("Quantità", 6);
        final JTextArea lbResult = new JTextArea();
        final JButton btAdd = new JButton("Add Tubolar");
        final JButton btRem = new JButton("Remove Tubolar");
        final JButton btCalc = new JButton("See the cut tubolar");
        final JTextArea lbResultFinal = new JTextArea();

        JComboBox<String> jComboBox = new JComboBox<>(NameTubolar.stringEnum());

        btAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String s = String.valueOf(jComboBox.getSelectedItem()); // "Code"
                String lengthTubolar = tfLenght.getText(); // "Code"
                int quantity = Integer.valueOf(tfQuantity.getText());

                try {
                    prova.openNewQueue(s);
                } catch (IllegalArgumentException e) {
                    System.out.println("");
                }

                prova.addTubolar(Integer.valueOf(lengthTubolar), s, quantity);
                String outPut = prova.printAllQueue();
                lbResult.setText(outPut);

                try (
                        final DataOutputStream dstream = new DataOutputStream(
                                new BufferedOutputStream(
                                        new FileOutputStream(FILE_NAME)))) {
                    dstream.writeUTF(outPut);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });

        btRem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String s = String.valueOf(jComboBox.getSelectedItem()); // "Code"
                String lengthTubolar = tfLenght.getText(); // "Code"
                prova.removeTubolar(s, Integer.valueOf(lengthTubolar));
                prova.printAllQueue();
            }

        });

        btCalc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lbResultFinal.setText(prova.printCuttedTubolar(prova.calcoloTotal()));
                prova.printAllQueue();
            }

        });

        final FlowLayout lay = new FlowLayout(FlowLayout.CENTER, 10, 10);
        final MyFrame frame = new MyFrame("I/O Example", lay);
        frame.getMainPanel().add(jComboBox);
        frame.getMainPanel().add(tfLenght);
        frame.getMainPanel().add(tfQuantity);
        frame.getMainPanel().add(lbResult);
        frame.getMainPanel().add(btAdd);
        frame.getMainPanel().add(btRem);
        frame.getMainPanel().add(btCalc);
        frame.getMainPanel().add(lbResult);
        frame.getMainPanel().add(lbResultFinal);
        frame.setVisible(true);

    }

}
