package view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.MainMenuController;
import model.NameTubolar;
import view.scenes.api.Scene;

/**
 * The {@link Scene} that represents the main menu of the game.
 */
public class MainMenuScene implements Scene {

    private static final int COLOR_BUTTONS_PANEL = 0xECCD99;
    private static final int R_BORDER = 0;
    private static final int G_BORDER = 0;
    private static final int B_BORDER = 0;
    private static final int A_BORDER = 50;
    private static final int FONT_SIZE = 30;
    private static final int FONT_SIZE_TITLE = 50;
    private static final int THICKNESS = 4;
    private static final int COLOR_BACKGROUND = 0xDCBA85;
    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 60;
    private static final int TOP = 2;
    private static final int LEFT = 2;
    private static final int BOTTOM = 2;
    private static final int RIGHT = 2;
    private static final String FONT = "Roboto";
    private static final String SCENE_NAME = "menu";

        final JTextField tfLenght = new JTextField("Lunghezza", 10);
    final JTextField tfQuantity = new JTextField("Quantità", 6);
    final JTextArea lbResult = new JTextArea();
    
    final JButton btAdd = new JButton("Add Tubolar");
    final JButton btRem = new JButton("Remove Tubolar");
    final JButton btCalc = new JButton("See short cut tubolar");
    final JButton btCalcTotale = new JButton("See total cut tubolar");
    final JButton btRestart = new JButton("Delete All");
    final JTextArea lbResultFinal = new JTextArea();
    final JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    final JPanel jpNORTH = new JPanel(new FlowLayout());
    final JPanel jpWest = new JPanel();

    private final MainMenuController controller;
    private final JPanel mainMenuPanel;

    /**
     * Creates a new {@link MainMenuScene}.
     * @param controller the controller for the main menu.
     */
    public MainMenuScene(final MainMenuController controller) {
        this.controller = controller;
        this.mainMenuPanel = new JPanel(new BorderLayout());
        initialize();
    }

    /**
     * Initializes the components of the MainMenuScene.
     * Sets up the layout, styles, and event listeners for the components.
     */
    private void initialize() {
        lbResult.setColumns(30);

        lbResultFinal.setColumns(30);

        JComboBox<String> jComboBox = new JComboBox<>(NameTubolar.stringEnum());



        //JLabel imagelabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource("view/barboncino.jpeg")));

        jpWest.setLayout(new BoxLayout(jpWest, 1));
        mainMenuPanel.add(jp, BorderLayout.CENTER);
        mainMenuPanel.add(jpNORTH, BorderLayout.NORTH);
        mainMenuPanel.add(jpWest, BorderLayout.WEST);
       
        jpNORTH.setBackground(Color.PINK);
        jpWest.setBackground(Color.PINK);
        jp.setBackground(Color.PINK);

        //jpNORTH.add(imagelabel);
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
    }

    @Override
    public JPanel getPanel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPanel'");
    }

    @Override
    public String getSceneName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSceneName'");
    }

}
