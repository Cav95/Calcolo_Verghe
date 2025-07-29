package CalcoloTubolare.view.scenes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import CalcoloTubolare.controller.ControllerModel;
import CalcoloTubolare.model.TextOutputFactory;
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

    private static final String RULES_PATH = "\\\\srvut\\ut\\FogliElettronici-Modelli\\Verghe\\rules.html";
    private static final String NESSUN_TUBOLARE_PRESENTE = "Nessun tubolare presente.\nAggiungi tubolari prima di calcolare.";
    private static final String LISTA_DI_TAGLIO = "Lista di taglio";
    private static final String TUBOLARI_UTILIZZATI = "Tubolari Utilizzati";
    private static final String TUBOLAR_IMG_DYR = "tubolar/";
    private static final int NUM_COLUMN = 30;
    private static final String STD_NUM_SILO = "1";
    private static final Integer TIME_TO_LAMP = 5;
    private static final String NAME_FILE = "TABELLA.xlsx";
    private String pathFile = System.getProperty("user.home") + SEP + NAME_FILE;
    private static final String SEP = System.getProperty("file.separator");
    private static final String SOLO_VERGHE_6MT = "Solo Verghe 6mt";
    private static final String FILE_SCELTO_EXCEL = "File scelto excel:";

    // Fields for manual input
    final JTextField tfLenght = new JTextField("", 10);
    final JTextField tfQuantity = new JTextField("", 6);
    final JTextField tfNumSilo = new JTextField(STD_NUM_SILO, 6);
    final JTextField tfCodeSilo = new JTextField("", 10);
    final JTextArea lbResult = new JTextArea(10, NUM_COLUMN);
    final JTextArea lbChosenExcelFile = new JTextArea(1, NUM_COLUMN);
    final JButton btAddTubolar = new JButton("Aggiungi Tubolare");
    final JButton btRemoveTubolar = new JButton("Rimuovi Tubolare");

    // Button for output of reduced calculation
    final JButton btCalcoloReduced = new JButton(TUBOLARI_UTILIZZATI);
    final JButton btCalcoloTotale = new JButton(LISTA_DI_TAGLIO);
    final JButton btConfer = new JButton("Confer");

    // Button to calculate from Excel
    // It will read the file and add the tubulars to the list
    final JButton btCalcFromExcel = new JButton("Importa da Excel");
    final JButton btSelectExcel = new JButton("Scegli File Excel");
    final JButton istruction = new JButton("Istruzioni Excel");
    final JButton btOpenExcel = new JButton("Apri File Excel");

    // Button to show rules of use
    final JButton btRulesofUse = new JButton("Regole di Utilizzo");

    // Button to delete all tubulars and reset the application
    final JButton btRestart = new JButton("Svuota Tutto");

    // Checkbox for optimal calculation
    final JCheckBox cbOttimale = new JCheckBox(SOLO_VERGHE_6MT, false);

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
        jpNORTH.add(Box.createHorizontalStrut(5));
        jpNORTH.add(new JLabel("Lunghezza: "));
        jpNORTH.add(tfLenght);
        jpNORTH.add(Box.createHorizontalStrut(5));
        jpNORTH.add(new JLabel("Quantità: "));
        jpNORTH.add(tfQuantity);
        jpNORTH.add(Box.createHorizontalStrut(5));
        jpNORTH.add(btAddTubolar);
        jpNORTH.add(Box.createHorizontalStrut(5));
        jpNORTH.add(btRemoveTubolar);
        jpNORTH.add(Box.createHorizontalStrut(15));
        jpNORTH.add(new JLabel("Numero Silo: "));
        jpNORTH.add(tfNumSilo);
        jpNORTH.add(Box.createHorizontalStrut(5));
        jpNORTH.add(new JLabel("Codice Silo: "));
        jpNORTH.add(tfCodeSilo);
        jpNORTH.add(Box.createHorizontalStrut(5));

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
        jpEast.add(btConfer);
        jpEast.add(Box.createVerticalStrut(5));
        jpEast.add(cbOttimale);
        jpEast.add(Box.createVerticalStrut(20));

        // Add buttons for Excel operations
        jpEast.add(btCalcFromExcel);
        jpEast.add(Box.createVerticalStrut(5));
        jpEast.add(btSelectExcel);
        jpEast.add(Box.createVerticalStrut(5));
        jpEast.add(istruction);
        jpEast.add(Box.createVerticalStrut(5));
        jpEast.add(btOpenExcel);
        jpEast.add(Box.createVerticalStrut(20));
        jpEast.add(btRulesofUse).setVisible(true); // Hide rules button for now

        // Add button to restart the application
        jpEast.add(btRestart);

        mainMenuPanel.add(jpEast, BorderLayout.EAST);

        // Azioni pulsanti
        btAddTubolar.addActionListener(e -> {
            String s = String.valueOf(jComboBox.getSelectedItem());
            controller.newTubolarList(s, Integer.parseInt(tfLenght.getText()), Integer.parseInt(tfQuantity.getText()));
            lbResult.setText(controller.tubalarAdded());
        });

        btRemoveTubolar.addActionListener(e -> {
            controller.getTubolarList().removeTubolar(String.valueOf(jComboBox.getSelectedItem()),
                    Integer.parseInt(tfLenght.getText()));
            lbResult.setText(controller.tubalarAdded());
        });

        btCalcFromExcel.addActionListener(e -> {
            controller.addTubolarFromExcel(pathFile, Integer.valueOf(tfNumSilo.getText()));
            lbResult.setText(controller.tubalarAdded());
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
                        NESSUN_TUBOLARE_PRESENTE);
                return;
            }
            var result = TextOutputFactory.reducedResultString(tfCodeSilo.getText(), !cbOttimale.isSelected(),
                    controller);
            ;

            new ResultPane(controller.getView(), TUBOLARI_UTILIZZATI, true, result);
        });

        btCalcoloTotale.addActionListener(e -> {
            if (lbResult.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mainMenuPanel,
                        NESSUN_TUBOLARE_PRESENTE);
                return;
            }
            var result = TextOutputFactory.extendedResultString(tfCodeSilo.getText(), !cbOttimale.isSelected(),
                    controller);
            new ResultPane(controller.getView(), LISTA_DI_TAGLIO, true, result);
        });

        btConfer.addActionListener(e -> {
            if (lbResult.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mainMenuPanel,
                        NESSUN_TUBOLARE_PRESENTE);
                return;
            }
            var result = TextOutputFactory.confertOutPut(controller , tfCodeSilo.getText(), Integer.parseInt(tfNumSilo.getText()));
            new ResultPane(controller.getView(), LISTA_DI_TAGLIO, true, result);
        });

        btRestart.addActionListener(e -> {
            controller.restart();
            lbResult.setText("");
        });

        istruction.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainMenuPanel, TextOutputFactory.rulesOfUseExcel(),
                    "Regole Excel", JOptionPane.INFORMATION_MESSAGE);
        });

        btRulesofUse.addActionListener(e -> {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(new File(RULES_PATH));
                } else {
                    System.out.println("Desktop non supportato!");
                }
            } catch (IOException | NullPointerException j) {
                System.err.println("Errore durante l'apertura del file HTML: " + j.getMessage());
            }
        });

        // Timer per aggiornare l'immagine
        final Timer timer = new Timer(TIME_TO_LAMP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var image = new ImageIcon(
                            ClassLoader.getSystemResource(TUBOLAR_IMG_DYR + jComboBox.getSelectedItem() + ".png"));
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
                // Desktop.getDesktop().browse(new URI(RULE_SITE));
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

}
