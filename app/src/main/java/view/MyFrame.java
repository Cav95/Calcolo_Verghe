package view;

import java.awt.*;
import javax.swing.*;

/* Specializzazione di JFrame:
 * - JFrame è Serializable!
 * - Il costruttore accetta titolo e layout-manager
 * - Si aggiunge il JPanel
 * - Un metodo getMainPanel() ci dà il pannello
 */
public class MyFrame extends JFrame{
	
	private final JPanel jpMain = new JPanel(new BorderLayout());
	
	
	
	public MyFrame(String title){
		super(title); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000,500);
		
		Color mycolor = new Color(255,124,200);
		Container c = this.getContentPane();
		c.setBackground(mycolor);
		// Il layout-manager può essere passato al costruttore di JPanel
		//this.jpMain = new JPanel(new BorderLayout());
		
		this.getContentPane().add(this.jpMain);


	}
	
	public JPanel getMainPanel(){
		return this.jpMain;
	}

}