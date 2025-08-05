package verghe.view.scenes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import verghe.controller.ControllerModel;
import verghe.model.TextOutputFactory;

import java.io.File;
import java.io.IOException;
import java.awt.*;

/**
 * ButtonActionPannel class that provides a panel with buttons for various
 * actions in the application.
 * It includes buttons for importing from Excel, calculating tubulars,
 * showing rules of use, and restarting the application.
 * It also includes checkboxes for optimal calculations and buttons for
 * displaying results.
 * This class is part of the CalcoloTubolare application.
 */
public class ButtonActionPannel extends JPanel {
    private static final String CORRECT_SILO_CODE = "Inserire un codice Silo valido ES: STX4848CM0001.";
    private static final String SELECT_EXCEL_FILE = "Seleziona file Excel";
    private static final String SAME_NUM_SILO = "Il numero dei silo deve essere lo stesso di quello \nusato per l'importazione da Excel.";
    private static final String SOLO_VERGHE_6MT = "Solo Verghe 6mt";
    private static final String PER_ROMBO = "per Rombo";
    private static final String CUT_LIST = "Lista di taglio";
    private static final String USED_TUBOLAR = "per Acquisti";
    private static final String NO_TUBOLAR_SELECTED = "Nessun tubolare presente.\nAggiungi tubolari prima di calcolare.";
    private static final String RULES_PATH = "\\\\srvut\\ut\\FogliElettronici-Modelli\\Verghe\\rules.html";

    // Button for output of reduced calculation
    final private JButton btUsedTubolarList = new JButton(USED_TUBOLAR);
    final private JButton btCuttedList = new JButton(CUT_LIST);
    final private JButton btConfert = new JButton(PER_ROMBO);

    // Button to calculate from Excel
    // It will read the file and add the tubulars to the list
    final private JButton btCalcFromExcel = new JButton("Importa da Excel");
    final private JButton btSelectExcel = new JButton("Scegli File Excel");
    final private JButton istruction = new JButton("Istruzioni Excel");
    final private JButton btOpenExcel = new JButton("Apri File Excel");

    // Checkbox for optimal calculation
    final private JCheckBox cbOttimale = new JCheckBox(SOLO_VERGHE_6MT, false);

    // Button to show rules of use
    final private JButton btRulesofUse = new JButton("Regole di Utilizzo");

    // Button to delete all tubulars and reset the application
    final private JButton btRestart = new JButton("Svuota Tutto");

    private MainMenuScene mainMenuPanel;

    public ButtonActionPannel(final ControllerModel controller, MainMenuScene mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(0, 10, 0, 0));

        this.add(btSelectExcel);
        this.add(Box.createVerticalStrut(5));
        this.add(btCalcFromExcel);
        this.add(Box.createVerticalStrut(5));
        this.add(cbOttimale);
        this.add(Box.createVerticalStrut(20));
        this.add(btUsedTubolarList);
        this.add(Box.createVerticalStrut(5));
        this.add(btConfert);
        this.add(Box.createVerticalStrut(5));
        this.add(btCuttedList);
        this.add(Box.createVerticalStrut(5));
        this.add(btOpenExcel);
        this.add(Box.createVerticalStrut(20));
        this.add(istruction);
        this.add(Box.createVerticalStrut(5));
        this.add(btRulesofUse); // Hide rules button for now

        // Add button to restart the application
        this.add(btRestart);

        btCalcFromExcel.addActionListener(e -> {
            mainMenuPanel.setTempNumSilo(mainMenuPanel.getTfNumSilo());
            controller.addTubolarFromExcel(mainMenuPanel.getPathFile(), mainMenuPanel.getTfNumSilo());
        });

        btSelectExcel.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle(SELECT_EXCEL_FILE);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                mainMenuPanel.setPathFile(fileChooser.getSelectedFile().toPath().toString());
                JOptionPane.showMessageDialog(mainMenuPanel.getPanel(),
                        "File selezionato: " + mainMenuPanel.getPathFile());
            }
            mainMenuPanel.setLbChosenExcelFile(mainMenuPanel.getPathFile());
        });

        btUsedTubolarList.addActionListener(e -> {
            noSiloCode(mainMenuPanel.getTfCodeSilo());
            siloNumberDifferent();
            controllEmptyResulPannel();

            var result = TextOutputFactory.reducedResultString(mainMenuPanel.getTfCodeSilo(),
                    !cbOttimale.isSelected(),
                    controller, mainMenuPanel.gettempNumSilo());

            new ResultPane(controller.getView(), USED_TUBOLAR, true, result);
        });

        btCuttedList.addActionListener(e -> {
            siloNumberDifferent();
            noSiloCode(mainMenuPanel.getTfCodeSilo());
            controllEmptyResulPannel();

            var result = TextOutputFactory.extendedResultString(mainMenuPanel.getTfCodeSilo(),
                    !cbOttimale.isSelected(),
                    controller, mainMenuPanel.gettempNumSilo());
            new ResultPane(controller.getView(), CUT_LIST, true, result);
        });

        btConfert.addActionListener(e -> {
            siloNumberDifferent();
            controllEmptyResulPannel();

            noSiloCode(mainMenuPanel.getTfCodeSilo());
            var result = TextOutputFactory.confertOutPut(controller, mainMenuPanel.getTfCodeSilo(),
                    mainMenuPanel.gettempNumSilo());
            new ResultPane(controller.getView(), CUT_LIST, true, result);
        });

        btRestart.addActionListener(e -> {
            controller.restart();
        });

        istruction.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainMenuPanel.getPanel(),
                    TextOutputFactory.rulesOfUseExcel(),
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

        btOpenExcel.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new java.io.File(mainMenuPanel.getPathFile()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Impossibile aprire il file:\n" + ex.getMessage());
            }
        });
    }

    private void noSiloCode(String codeSilo) {
        if (codeSilo.isBlank()
                || !codeSilo.startsWith("ST")) {
            JOptionPane.showMessageDialog(mainMenuPanel.getPanel(), CORRECT_SILO_CODE);
        }
    }

    private void siloNumberDifferent() {
        if (mainMenuPanel.getTfNumSilo() != mainMenuPanel.gettempNumSilo()) {
            JOptionPane.showMessageDialog(mainMenuPanel.getPanel(),
                    SAME_NUM_SILO);
        }
    }

    private void controllEmptyResulPannel() {
        if (mainMenuPanel.getLbResult().getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainMenuPanel.getPanel(),
                    NO_TUBOLAR_SELECTED);
            return;
        }
    }

}
