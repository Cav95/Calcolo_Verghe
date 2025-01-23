package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import model.*;

public class DisplayImplementation {


    public void display() {

        MultiQueue<Integer, String> tubolarList = new MultiQueueImpl<>();

        final JTextField tfLenght = new JTextField("Lunghezza", 10);
        final JTextField tfQuantity = new JTextField("Quantità", 6);
        final JTextArea lbResult = new JTextArea();
        lbResult.setColumns(30);
        final JButton btAdd = new JButton("Add Tubolar");
        final JButton btRem = new JButton("Remove Tubolar");
        final JButton btCalc = new JButton("See short cut tubolar");
        final JButton btCalcTotale = new JButton("See total cut tubolar");
        final JButton btRestart = new JButton("Delete All");
        final JTextArea lbResultFinal = new JTextArea();
        lbResultFinal.setColumns(30);

        JComboBox<String> jComboBox = new JComboBox<>(NameTubolar.stringEnum());

        btAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String s = String.valueOf(jComboBox.getSelectedItem()); // "Code"
                String lengthTubolar = tfLenght.getText(); // "Code"
                int quantity = Integer.valueOf(tfQuantity.getText());

                try {
                    tubolarList.openNewQueue(s);
                } catch (IllegalArgumentException e) {
                    System.out.println("");
                }

                tubolarList.addTubolar(Integer.valueOf(lengthTubolar), s, quantity);
                String outPut = tubolarList.printAllQueue();
                lbResult.setText(outPut);

            }
        });

        btRem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String s = String.valueOf(jComboBox.getSelectedItem()); // "Code"
                String lengthTubolar = tfLenght.getText(); // "Code"
                tubolarList.removeTubolar(s, Integer.valueOf(lengthTubolar));
                lbResult.setText(tubolarList.printAllQueue());
                tubolarList.printAllQueue();
            }

        });

        btCalc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lbResultFinal.setText(CalcolatorTubolar

                        .printCuttedTubolarSmoll(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue())));
                tubolarList.printAllQueue();
            }

        });

        btCalcTotale.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lbResultFinal.setText(CalcolatorTubolar
                        .printCuttedTubolar(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue())));
                tubolarList.printAllQueue();
            }

        });

        btRestart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (var elem : tubolarList.getMultiQueue().entrySet()) {
                    tubolarList.getMultiQueue().remove(elem.getKey());
                }
                lbResultFinal.setText("");
                lbResult.setText("");
            }

        });

        final MyFrame frame = new MyFrame("Calcolo Verghe");

        final JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));// new LoadImage();
        final JPanel jpNORTH = new JPanel(new FlowLayout());
        final JPanel jpWest = new JPanel();
        JLabel imagelabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource("/view/barboncino.jpeg")));

        jpWest.setLayout(new BoxLayout(jpWest, 1));
        frame.getMainPanel().add(jp, BorderLayout.CENTER);
        frame.getMainPanel().add(jpNORTH, BorderLayout.NORTH);
        frame.getMainPanel().add(jpWest, BorderLayout.WEST);
       
        jpNORTH.setBackground(Color.PINK);
        jpWest.setBackground(Color.PINK);
        jp.setBackground(Color.PINK);

        jpNORTH.add(jComboBox);

        jpNORTH.add(tfLenght);
        jpNORTH.add(tfQuantity);
        jpNORTH.add(btAdd);
        jpNORTH.add(btRem);

        jpWest.add(btCalc);
        jpWest.add(btCalcTotale);
        jpWest.add(btRestart);

        jp.add(lbResult);
        jp.add(lbResultFinal);

        frame.setVisible(true);

    }

}
