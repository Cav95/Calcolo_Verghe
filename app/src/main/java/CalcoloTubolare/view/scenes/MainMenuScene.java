package CalcoloTubolare.view.scenes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.text.View;

import java.awt.print.*;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import CalcoloTubolare.controller.ControllerModel;
import CalcoloTubolare.model.CalcolatorTubolar;
import CalcoloTubolare.model.api.NameTubolar;
import CalcoloTubolare.view.scenes.api.Scene;

/**
 * The {@link Scene} that represents the main menu of the game.
 */
public class MainMenuScene implements Scene {
    private static final Integer TIME_TO_LAMP = 5;
    private static final String NAME_FILE = "TABELLA.xlsx";
    private static final String SEP = System.getProperty("file.separator");

    final JTextField tfLenght = new JTextField("Lunghezza", 10);
    final JTextField tfQuantity = new JTextField("Quantità", 6);
    final JTextArea lbResult = new JTextArea();

    final JButton btAdd = new JButton("Add Tubolar");
    final JButton btRem = new JButton("Remove Tubolar");
    final JButton btCalc = new JButton("See short cut tubolar");
    final JButton btCalcTotale = new JButton("See total cut tubolar");

    final JButton btCalcFromExcel = new JButton("Add Tubolar from Excel");
    final JButton istruction = new JButton("Istruzioni Excel");

    final JButton btRestart = new JButton("Delete All");
    final JTextArea lbResultFinal = new JTextArea();

    final JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    final JPanel jpNORTH = new JPanel(new FlowLayout());
    final JPanel jpEast = new JPanel();
    final JPanel jpWest = new JPanel();

    JLabel imageLabel = new JLabel();

    private final ControllerModel controller;
    private final JPanel mainMenuPanel;

    /**
     * Creates a new {@link MainMenuScene}.
     * 
     * @param controller the controller for the main menu.
     */
    public MainMenuScene(final ControllerModel controller) {
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

        //lbResultFinal.setColumns(30);

        JComboBox<NameTubolar> jComboBox = new JComboBox<>(NameTubolar.values());

        jpEast.setLayout(new BoxLayout(jpEast, 1));

        mainMenuPanel.add(jp, BorderLayout.CENTER);
        mainMenuPanel.add(jpNORTH, BorderLayout.NORTH);
        mainMenuPanel.add(jpEast, BorderLayout.EAST);
        mainMenuPanel.add(jpWest, BorderLayout.WEST);

        jpNORTH.add(jComboBox);

        jpNORTH.add(tfLenght);
        jpNORTH.add(tfQuantity);
        jpNORTH.add(btAdd);
        jpNORTH.add(btRem);

        jpWest.add(imageLabel);

        jpEast.add(btCalc);
        jpEast.add(btCalcTotale);
        jpEast.add(btCalcFromExcel);
        jpEast.add(istruction);
        jpEast.add(btRestart);

        jp.add(lbResult);
        jp.add(lbResultFinal);

        btAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String s = String.valueOf(jComboBox.getSelectedItem()); // "Code"
                controller.newTubolarList(s, Integer.valueOf(tfLenght.getText()).intValue(),
                        Integer.valueOf(tfQuantity.getText()).intValue());
                lbResult.setText(controller.getTubolarList().printAllQueue());
            }

        });

        btCalcFromExcel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addTubolarFromExcel(System.getProperty("user.home") + SEP + NAME_FILE);
                lbResult.setText(controller.getTubolarList().printAllQueue());
            }

        });

        btCalc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var result = CalcolatorTubolar.printCuttedTubolarSmoll(
                                CalcolatorTubolar.calcoloTotal(controller.getTubolarList().getMultiQueue()));
                //lbResultFinal.setText(result);
                new ResultPane(controller.getView(), "result", true, result);
            }

        });

        btCalcTotale.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var result = CalcolatorTubolar
                        .printCuttedTubolar(
                                CalcolatorTubolar.calcoloTotal(controller.getTubolarList().getMultiQueue()));

                //lbResultFinal.setText(result);
                new ResultPane(controller.getView(), "result", true, result);
            }

        });

        btRestart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.restart();
                lbResultFinal.setText("");
                lbResult.setText("");
            }

        });

        btRem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getTubolarList().removeTubolar(String.valueOf(jComboBox.getSelectedItem()),
                        Integer.valueOf(tfLenght.getText()));
                lbResult.setText(controller.getTubolarList().printAllQueue());
            }

        });


        final Timer timer = new Timer(TIME_TO_LAMP, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var image = new ImageIcon(
                            ClassLoader.getSystemResource("tubolar/" + jComboBox.getSelectedItem() + ".png"));
                    Image newimg = image.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(newimg));

                } catch (Exception l) {
                    // TODO: handle exception
                }

            }

        });
        timer.start();

        istruction.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jComboBox, "Il file Excel da SolidWork \n"
                        + "deve essere salvato in C:\\user\\{yourUser} \n"
                        + "con nome TABELLA.xlsx \n"
                        + "dove {yourUser} è il tuo nome utente di Windows.\n"
                        + "Il file Excel deve avere le colonne: \n"
                        + "Codice, Lunghezza, Quantità, Diametro, Spessore", "Regole Excel", 0);
            }

        });

        JFrame frame = new JFrame("Stampa JTextArea");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Creazione del pulsante "Stampa"
        JButton stampaButton = new JButton("Stampa");
        stampaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Metodo per stampare il contenuto del JTextArea
                    boolean printed = lbResultFinal.print();
                    if (printed) {
                        JOptionPane.showMessageDialog(frame, "Stampa completata con successo!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Stampa annullata.");
                    }
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(frame, "Errore durante la stampa: " + ex.getMessage());
                }
            }
        });
        jpEast.add(stampaButton);

    }

    @Override
    public JPanel getPanel() {
        return mainMenuPanel;
    }

    @Override
    public String getSceneName() {
        return "Main";
    }

}
