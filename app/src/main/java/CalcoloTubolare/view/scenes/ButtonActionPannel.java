package CalcoloTubolare.view.scenes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.File;
import java.io.IOException;

import CalcoloTubolare.controller.ControllerModel;
import CalcoloTubolare.model.TextOutputFactory;
import java.awt.*;

public class ButtonActionPannel extends JPanel {
    private static final String SOLO_VERGHE_6MT = "Solo Verghe 6mt";
    private static final String PER_ROMBO = "per Rombo";
    private static final String LISTA_DI_TAGLIO = "Lista di taglio";
    private static final String TUBOLARI_UTILIZZATI = "per Acquisti";
    // private static final String NESSUN_TUBOLARE_PRESENTE = "Nessun tubolare
    // presente.\nAggiungi tubolari prima di calcolare.";
    private static final String RULES_PATH = "\\\\srvut\\ut\\FogliElettronici-Modelli\\Verghe\\rules.html";
    // Button for output of reduced calculation
    final JButton btUsedTubolarList = new JButton(TUBOLARI_UTILIZZATI);
    final JButton btCuttedList = new JButton(LISTA_DI_TAGLIO);
    final JButton btConfert = new JButton(PER_ROMBO);

    // Button to calculate from Excel
    // It will read the file and add the tubulars to the list
    final JButton btCalcFromExcel = new JButton("Importa da Excel");
    final JButton btSelectExcel = new JButton("Scegli File Excel");
    final JButton istruction = new JButton("Istruzioni Excel");
    final JButton btOpenExcel = new JButton("Apri File Excel");

    // Checkbox for optimal calculation
    final JCheckBox cbOttimale = new JCheckBox(SOLO_VERGHE_6MT, false);

    // Button to show rules of use
    final JButton btRulesofUse = new JButton("Regole di Utilizzo");

    // Button to delete all tubulars and reset the application
    final JButton btRestart = new JButton("Svuota Tutto");

    MainMenuScene mainMenuPanel;

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
            // lbResult.setText(controller.tubolarAdded());
        });

        btSelectExcel.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Seleziona file Excel");
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                mainMenuPanel.setPathFile(fileChooser.getSelectedFile().toPath().toString());
                JOptionPane.showMessageDialog(this, "File selezionato: " + mainMenuPanel.getPathFile());
            }
            mainMenuPanel.setLbChosenExcelFile(mainMenuPanel.getPathFile());
        });

        btUsedTubolarList.addActionListener(e -> {
            noSiloCode(mainMenuPanel.getTfCodeSilo());
            siloNumberDifferent();
            /*
             * if (lbResult.getText().isEmpty()) {
             * JOptionPane.showMessageDialog(this,
             * NESSUN_TUBOLARE_PRESENTE);
             * return;
             * }
             */
            var result = TextOutputFactory.reducedResultString(mainMenuPanel.getTfCodeSilo(), !cbOttimale.isSelected(),
                    controller, mainMenuPanel.gettempNumSilo());

            new ResultPane(controller.getView(), TUBOLARI_UTILIZZATI, true, result);
        });

        btCuttedList.addActionListener(e -> {
            siloNumberDifferent();
            noSiloCode(mainMenuPanel.getTfCodeSilo());
            /*
             * if (lbResult.getText().isEmpty()) {
             * JOptionPane.showMessageDialog(mainMenuPanel,
             * NESSUN_TUBOLARE_PRESENTE);
             * return;
             * }
             */
            var result = TextOutputFactory.extendedResultString(mainMenuPanel.getTfCodeSilo(), !cbOttimale.isSelected(),
                    controller, mainMenuPanel.gettempNumSilo());
            new ResultPane(controller.getView(), LISTA_DI_TAGLIO, true, result);
        });

        btConfert.addActionListener(e -> {
            siloNumberDifferent();
            /*
             * if (lbResult.getText().isEmpty()) {
             * JOptionPane.showMessageDialog(mainMenuPanel,
             * NESSUN_TUBOLARE_PRESENTE);
             * return;
             * }
             */
            noSiloCode(mainMenuPanel.getTfCodeSilo());
            var result = TextOutputFactory.confertOutPut(controller, mainMenuPanel.getTfCodeSilo(),
                    mainMenuPanel.gettempNumSilo());
            new ResultPane(controller.getView(), LISTA_DI_TAGLIO, true, result);
        });

        btRestart.addActionListener(e -> {
            controller.restart();
        });

        istruction.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, TextOutputFactory.rulesOfUseExcel(),
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
            JOptionPane.showMessageDialog(this, "Inserire un codice Silo valido ES: STX4848CM0001.");
        }
    }

    private void siloNumberDifferent() {
        if (mainMenuPanel.getTfNumSilo() != mainMenuPanel.gettempNumSilo()) {
            JOptionPane.showMessageDialog(this,
                    "Il numero dei silo deve essere lo stesso di quello \nusato per l'importazione da Excel.");
        }
    }



}
