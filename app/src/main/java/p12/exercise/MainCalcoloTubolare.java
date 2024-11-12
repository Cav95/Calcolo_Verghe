package p12.exercise;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import calcoloVerghe.Frame.*;

public class MainCalcoloTubolare {

    public static void main(String[] args) {

        MultiQueue<Integer, String> prova = new MultiQueueImpl<>();

        final JTextField tfLenght = new JTextField(20);
        final JTextField tfQuantity = new JTextField(5);
        final JLabel lbResult = new JLabel("Result: 0");
        final JButton btAdd = new JButton("Add Tubolar");
        final JButton btRem = new JButton("Remove Tubolar");
        final JButton btCalc = new JButton("See the cut tubolar");

        String[] optionsToChoose = new String[NameTubolar.values().length];
        
        for(NameTubolar elem : NameTubolar.values()){
            optionsToChoose[elem.ordinal()] = elem.getactualName();
        }

        JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);

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

                prova.printAllQueue();

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
                prova.calcoloTotal();
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
        frame.setVisible(true);

    }
}
