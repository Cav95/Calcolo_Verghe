package Frame;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import p12.exercise.*;

public class DisplayImplementation {

    public static void display() {

        

        MultiQueue<Integer,String> tubolarList = new MultiQueueImpl<>();

        final JTextField tfLenght = new JTextField("Lunghezza", 10);
        final JTextField tfQuantity = new JTextField("Quantità", 6);
        final JTextArea lbResult = new JTextArea();
        final JButton btAdd = new JButton("Add Tubolar");
        final JButton btRem = new JButton("Remove Tubolar");
        final JButton btCalc = new JButton("See short cut tubolar");
        final JButton btCalcTotale = new JButton("See total cut tubolar");
        final JButton btRestart = new JButton("Delete All");
        final JTextArea lbResultFinal = new JTextArea();

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
                lbResultFinal.setText(CalcolatorTubolar.printCuttedTubolarSmoll(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue())));
                tubolarList.printAllQueue();
            }

        });

        btCalcTotale.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lbResultFinal.setText(CalcolatorTubolar.printCuttedTubolar(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue())));
                tubolarList.printAllQueue();
            }

        });
        
        btRestart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for ( var elem : tubolarList.getMultiQueue().entrySet() ){
                    tubolarList.getMultiQueue().remove(elem.getKey());
                }
                lbResultFinal.setText("");
                lbResult.setText("");
            }

        });



        
        final FlowLayout lay = new FlowLayout(FlowLayout.CENTER, 10, 10);
       // final BorderLayout layBorder = new BorderLayout();
        final MyFrame frame = new MyFrame("I/O Example", lay);
        
        frame.getMainPanel().add(jComboBox);

        frame.getMainPanel().add(tfLenght);
        frame.getMainPanel().add(tfQuantity);   

        frame.getMainPanel().add(btAdd);
        frame.getMainPanel().add(btRem);
        frame.getMainPanel().add(btCalc);
        frame.getMainPanel().add(btCalcTotale);
        frame.getMainPanel().add(btRestart);

        frame.getMainPanel().add(lbResult);
        frame.getMainPanel().add(lbResultFinal);
        frame.setVisible(true);

    }

}
