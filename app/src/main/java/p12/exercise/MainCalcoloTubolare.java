package p12.exercise;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import calcoloVerghe.Frame.*;


public class MainCalcoloTubolare {


    public static void main(String[] args){
 
      MultiQueue<Integer,String> prova = new MultiQueueImpl<>();

		final JTextField tfCode = new JTextField(10);
        final JTextField tfLenght = new JTextField(20);
		final JLabel lbResult = new JLabel("Result: 0");
		final JButton btAdd = new JButton("Add Tubolar");
		
		btAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				String s = tfCode.getText(); // "Code"
                String lengthTubolar = tfLenght.getText(); // "Code"

                try{
                prova.openNewQueue(s);
            }
                catch (IllegalArgumentException e){
                    System.out.println("");
                }

                prova.addTubolar(Integer.valueOf(lengthTubolar),s);

                lbResult.setText(prova.allEnqueuedElements().toString());
                prova.printAllQueue();
                
			}
		});
		
		final FlowLayout lay = new FlowLayout(FlowLayout.CENTER,10,10); 
		final MyFrame frame = new MyFrame("I/O Example",lay);
		frame.getMainPanel().add(tfCode);
        frame.getMainPanel().add(tfLenght);
		frame.getMainPanel().add(lbResult);
		frame.getMainPanel().add(btAdd);
		frame.setVisible(true);


}
}