package verghe.view.scenes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import verghe.controller.ControllerModel;
import verghe.view.scenes.api.Scene;

/**
 * MainMenuScene class that implements the Scene interface to create the main
 * menu
 * of the application.
 * It allows users to add, remove, and calculate tubulars, as well as import
 * data from Excel.
 */
public class MainMenuScene implements Scene {

    private static final int NUM_COLUMN = 30;
    private static final String STD_NUM_SILO = "1";
    private static final Integer TIME_TO_LAMP = 6;
    private static final String NAME_FILE = "TABELLA.xlsx";
    private String pathFile = System.getProperty("user.home") + SEP + NAME_FILE;
    private static final String SEP = System.getProperty("file.separator");
    private static final String FILE_SCELTO_EXCEL = "File scelto excel:";

    private Integer tempNumSilo = Integer.parseInt(STD_NUM_SILO);

    final JTextField tfNumSilo = new JTextField(STD_NUM_SILO, 6);
    final JTextField tfCodeSilo = new JTextField("", 10);
    final JTextArea lbResult = new JTextArea(10, NUM_COLUMN);

    final JTextArea lbChosenExcelFile = new JTextArea(1, NUM_COLUMN);

    // Checkbox for manual input
    final JCheckBox cbManualInput = new JCheckBox("Inserimento Manuale", false);

    final JPanel mainMenuPanel;
    // final JLabel imageLabel = new JLabel();

    // label text
    JLabel numSilo = new JLabel("Numero Silo: ");
    JLabel siloCode = new JLabel("Codice Silo: ");
    JLabel tubolarList = new JLabel("Lista Tubolari: ");

    private final ControllerModel controller;

    /**
     * Constructor for MainMenuScene.
     * 
     * @param controller the controller model to manage the application logic
     */
    public MainMenuScene(final ControllerModel controller) {
        this.controller = controller;
        this.mainMenuPanel = new JPanel(new BorderLayout(10, 10));
        initialize();
    }

    /**
     * Initializes the main menu panel with components and layout.
     */
    private void initialize() {
        mainMenuPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Nord: Input e selezione tubolare
        JPanel jpNORTH = new JPanel();
        jpNORTH.setLayout(new BoxLayout(jpNORTH, BoxLayout.X_AXIS));
        jpNORTH.setBorder(new EmptyBorder(0, 0, 10, 0));
        jpNORTH.add(cbManualInput);
        jpNORTH.add(Box.createHorizontalStrut(15));
        jpNORTH.add(siloCode);
        jpNORTH.add(tfCodeSilo);
        jpNORTH.add(Box.createHorizontalStrut(5));
        jpNORTH.add(numSilo);
        jpNORTH.add(tfNumSilo);
        jpNORTH.add(Box.createHorizontalStrut(5));

        mainMenuPanel.add(jpNORTH, BorderLayout.NORTH);

        // Ovest: Immagine
        JPanel jpWest = new ManualPannel(controller);
        mainMenuPanel.add(jpWest, BorderLayout.WEST);

        // Sud: Posizione file
        JPanel jpSud = new JPanel();
        jpSud.setLayout(new BoxLayout(jpSud, BoxLayout.Y_AXIS));
        jpSud.setBorder(new EmptyBorder(0, 0, 0, 0));
        jpSud.add(new JLabel(FILE_SCELTO_EXCEL));
        jpSud.add(new JScrollPane(lbChosenExcelFile));
        jpSud.add(Box.createVerticalStrut(5));
        mainMenuPanel.add(jpSud, BorderLayout.SOUTH);

        // Centro: Risultati
        JPanel jpCenter = new JPanel();
        jpCenter.setLayout(new BoxLayout(jpCenter, BoxLayout.Y_AXIS));
        jpCenter.setBorder(new EmptyBorder(0, 0, 0, 0));
        lbResult.setEditable(false);
        lbChosenExcelFile.setEditable(false);
        lbChosenExcelFile.setText(pathFile);
        lbChosenExcelFile.setSize(1, NUM_COLUMN);
        jpCenter.add(tubolarList);
        jpCenter.add(new JScrollPane(lbResult));
        jpCenter.add(Box.createVerticalStrut(10));
        mainMenuPanel.add(jpCenter, BorderLayout.CENTER);

        // Est: Pulsanti azione
        JPanel jpEast = new ButtonActionPannel(controller, this);
        mainMenuPanel.add(jpEast, BorderLayout.EAST);

        // Timer per aggiornare l'immagine
        final Timer timer = new Timer(TIME_TO_LAMP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lbResult.setText(controller.tubolarAdded());
                jpWest.setVisible(cbManualInput.isSelected());
            }
        });
        timer.start();
    }

    /**
     * Gets the path of the Excel file.
     * 
     * @return the path of the Excel file
     */
    public String getPathFile() {
        return pathFile;
    }

    /**
     * Gets the number of the silo from the text field.
     * 
     * @return the number of the silo as an Integer
     */
    public Integer getTfNumSilo() {
        return Integer.parseInt(tfNumSilo.getText());
    }

    /**
     * Gets the code of the silo from the text field.
     * 
     * @return the code of the silo as a String
     */
    public String getTfCodeSilo() {
        return tfCodeSilo.getText();
    }

    /**
     * Gets the temporary number of the silo.
     * 
     * @return the temporary number of the silo as an Integer
     */
    public Integer gettempNumSilo() {
        return tempNumSilo;
    }

    /**
     * Sets the label for the chosen Excel file.
     * 
     * @param text the text to set in the label
     */
    public void setLbChosenExcelFile(String text) {
        lbChosenExcelFile.setText(text);
    }

    /**
     * Sets the path of the Excel file.
     * 
     * @param pathFile the path of the Excel file to set
     */
    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    /**
     * Sets the temporary number of the silo.
     * 
     * @param tempNumSilo the temporary number of the silo to set.
     */
    public void setTempNumSilo(Integer tempNumSilo) {
        this.tempNumSilo = tempNumSilo;
    }

    /**
     * Gets the text area for displaying results.
     * 
     * @return the JTextArea for results.
     */
    public JTextArea getLbResult() {
        return lbResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getPanel() {
        return mainMenuPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSceneName() {
        return "Main";
    }

}
