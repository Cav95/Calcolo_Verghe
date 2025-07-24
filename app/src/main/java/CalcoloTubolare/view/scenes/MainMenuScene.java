package CalcoloTubolare.view.scenes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import CalcoloTubolare.controller.ControllerModel;
import CalcoloTubolare.model.api.NameTubolar;
import CalcoloTubolare.view.scenes.api.Scene;

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
    private static final Integer TIME_TO_LAMP = 5;
    private static final String NAME_FILE = "TABELLA.xlsx";
    private String pathFile = System.getProperty("user.home") + SEP + NAME_FILE;
    private static final String SEP = System.getProperty("file.separator");

    final JTextField tfLenght = new JTextField("", 10);
    final JTextField tfQuantity = new JTextField("", 6);
    final JTextField tfNumSilo = new JTextField(STD_NUM_SILO, 6); // aggiungi questo campo
    final JTextField tfCodeSilo = new JTextField("", 10); // campo per il codice silo
    final JTextArea lbResult = new JTextArea(10, NUM_COLUMN);
    final JTextArea lbChosenExcelFile = new JTextArea(1, NUM_COLUMN);
    final JButton btAddTubolar = new JButton("Aggiungi Tubolare");
    final JButton btRemoveTubolar = new JButton("Rimuovi Tubolare");
    final JButton btCalcoloReduced = new JButton("Tagli Ottimizzati");
    final JButton btCalcoloTotale = new JButton("Tagli Totali");
    final JButton btCalcFromExcel = new JButton("Importa da Excel");
    final JButton btSelectExcel = new JButton("Scegli File Excel");
    final JButton istruction = new JButton("Istruzioni Excel");
    final JButton btRestart = new JButton("Svuota Tutto");
    final JButton btOpenExcel = new JButton("Apri File Excel");
    final JCheckBox cbOttimale = new JCheckBox("Solo Verghe 6mt", false);

    final JPanel mainMenuPanel;
    final JLabel imageLabel = new JLabel();

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
        JComboBox<NameTubolar> jComboBox = new JComboBox<>(NameTubolar.values());
        jpNORTH.add(new JLabel("Tipo: "));
        jpNORTH.add(jComboBox);
        jpNORTH.add(Box.createHorizontalStrut(10));
        jpNORTH.add(new JLabel("Lunghezza: "));
        jpNORTH.add(tfLenght);
        jpNORTH.add(Box.createHorizontalStrut(10));
        jpNORTH.add(new JLabel("Quantità: "));
        jpNORTH.add(tfQuantity);
        jpNORTH.add(Box.createHorizontalStrut(10));
        jpNORTH.add(new JLabel("Numero Silo: "));
        jpNORTH.add(tfNumSilo);
        jpNORTH.add(Box.createHorizontalStrut(10));
        jpNORTH.add(new JLabel("Codice Silo: "));
        jpNORTH.add(tfCodeSilo);
        jpNORTH.add(Box.createHorizontalStrut(10));
        jpNORTH.add(btAddTubolar);
        jpNORTH.add(Box.createHorizontalStrut(5));
        jpNORTH.add(btRemoveTubolar);

        mainMenuPanel.add(jpNORTH, BorderLayout.NORTH);

        // Ovest: Immagine
        JPanel jpWest = new JPanel();
        jpWest.setLayout(new BoxLayout(jpWest, BoxLayout.Y_AXIS));
        jpWest.setBorder(new EmptyBorder(0, 0, 0, 10));
        imageLabel.setPreferredSize(new Dimension(200, 200));
        jpWest.add(imageLabel);
        mainMenuPanel.add(jpWest, BorderLayout.WEST);

        // Sud: Posizione file
        JPanel jpSud = new JPanel();
        jpSud.setLayout(new BoxLayout(jpSud, BoxLayout.Y_AXIS));
        jpSud.setBorder(new EmptyBorder(0, 0, 0, 0));
        jpSud.add(new JLabel("File scelto excel:"));
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
        jpCenter.add(new JLabel("Lista Tubolari:"));
        jpCenter.add(new JScrollPane(lbResult));
        jpCenter.add(Box.createVerticalStrut(10));
        mainMenuPanel.add(jpCenter, BorderLayout.CENTER);

        // Est: Pulsanti azione
        JPanel jpEast = new JPanel();
        jpEast.setLayout(new BoxLayout(jpEast, BoxLayout.Y_AXIS));
        jpEast.setBorder(new EmptyBorder(0, 10, 0, 0));
        jpEast.add(btCalcoloReduced);
        jpEast.add(Box.createVerticalStrut(5));
        jpEast.add(btCalcoloTotale);
        jpEast.add(Box.createVerticalStrut(5));
        jpEast.add(btCalcFromExcel);
        jpEast.add(Box.createVerticalStrut(5));
        jpEast.add(btSelectExcel);
        jpEast.add(Box.createVerticalStrut(5));
        jpEast.add(istruction);
        jpEast.add(Box.createVerticalStrut(5));
        jpEast.add(btRestart);
        jpEast.add(Box.createVerticalStrut(10));
        jpEast.add(cbOttimale);
        jpEast.add(Box.createVerticalStrut(20));
        jpEast.add(btOpenExcel);
        mainMenuPanel.add(jpEast, BorderLayout.EAST);

        // Azioni pulsanti
        btAddTubolar.addActionListener(e -> {
            String s = String.valueOf(jComboBox.getSelectedItem());
            controller.newTubolarList(s, Integer.parseInt(tfLenght.getText()), Integer.parseInt(tfQuantity.getText()));
            lbResult.setText(controller.getTubolarList().printAllQueue());
        });

        btRemoveTubolar.addActionListener(e -> {
            controller.getTubolarList().removeTubolar(String.valueOf(jComboBox.getSelectedItem()),
                    Integer.parseInt(tfLenght.getText()));
            lbResult.setText(controller.getTubolarList().printAllQueue());
        });

        btCalcFromExcel.addActionListener(e -> {
            controller.addTubolarFromExcel(pathFile, Integer.valueOf(tfNumSilo.getText()));
            lbResult.setText(controller.getTubolarList().printAllQueue());
        });

        btSelectExcel.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Seleziona file Excel");
            int result = fileChooser.showOpenDialog(mainMenuPanel);
            if (result == JFileChooser.APPROVE_OPTION) {
                pathFile = fileChooser.getSelectedFile().toPath().toString();
                JOptionPane.showMessageDialog(mainMenuPanel, "File selezionato: " + pathFile);
            }
            lbChosenExcelFile.setText(pathFile);
        });

        btCalcoloReduced.addActionListener(e -> {
            if (lbResult.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mainMenuPanel,
                        "Nessun tubolare presente.\nAggiungi tubolari prima di calcolare.");
                return;
            }
            var result = structureCode(tfCodeSilo.getText()) + ottimalOutputString(cbOttimale.isSelected())
                    + controller.partialCalcolateTubolar(cbOttimale.isSelected()) + userName();
            ;

            new ResultPane(controller.getView(), "Calcolo Ridotto", true, result);
        });

        btCalcoloTotale.addActionListener(e -> {
            if (lbResult.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mainMenuPanel,
                        "Nessun tubolare presente.\nAggiungi tubolari prima di calcolare.");
                return;
            }
            var result = structureCode(tfCodeSilo.getText()) + ottimalOutputString(cbOttimale.isSelected())
                    + controller.totalCalcolateTubolar(cbOttimale.isSelected()) + userName();
            new ResultPane(controller.getView(), "Calcolo Totale", true, result);
        });

        btRestart.addActionListener(e -> {
            controller.restart();
            lbResult.setText("");
        });

        istruction.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainMenuPanel,
                    "Il file Excel da SolidWork \n"
                            + "deve essere salvato in C:\\user\\{yourUser} \n"
                            + "con nome TABELLA.xlsx \n"
                            + "dove {yourUser} è il tuo nome utente di Windows.\n"
                            + "Il file Excel deve avere le colonne: \n"
                            + "Codice, Lunghezza, Quantità, Diametro, Spessore",
                    "Regole Excel", JOptionPane.INFORMATION_MESSAGE);
        });

        // Timer per aggiornare l'immagine
        final Timer timer = new Timer(TIME_TO_LAMP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var image = new ImageIcon(
                            ClassLoader.getSystemResource("tubolar/" + jComboBox.getSelectedItem() + ".png"));
                    Image newimg = image.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(newimg));
                } catch (Exception l) {
                    imageLabel.setIcon(null);
                }
            }
        });
        timer.start();

        btOpenExcel.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new java.io.File(pathFile));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainMenuPanel, "Impossibile aprire il file:\n" + ex.getMessage());
            }
        });
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

    /**
     * {@inheritDoc}
     */
    private String ottimalOutputString(Boolean optimal) {
        return optimal ? "Caso Ottimo Tubolari 12m\\6mt\n\n" : "Caso Pessimo Tubolari solo 6mt \n\n";

    }

    /**
     * Formats the structure code for display.
     */
    private String structureCode(String codeSilo) {
        return "Codice della strutura: " + codeSilo + "\n\n";

    }

    private String userName() {
        return "Autore: " + System.getProperty("user.name") + "\n" + LocalDate.now();
    }

}
