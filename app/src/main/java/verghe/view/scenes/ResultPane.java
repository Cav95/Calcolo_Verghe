package verghe.view.scenes;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import verghe.model.print.PdfDocumentGenerator;
import verghe.model.print.PrintSettings;
import verghe.view.View;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

/**
 * ResultPane class that extends JDialog to display the results of the tubular
 * calculations.
 */
public class ResultPane extends JDialog {
        /**
         * Constructor for ResultPane.
         * 
         * @param view       the main view of the application
         * @param title      the title of the dialog
         * @param removeMode whether to remove the dialog after displaying results
         * @param result     the result string to be displayed
         */
        public ResultPane(final View view, final String title, boolean removeMode, String result) {
                super(view.getMainFrame(), title, ModalityType.APPLICATION_MODAL);
                this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.setSize(800, 500);
                this.setMaximumSize(this.getSize());
                this.setLocationRelativeTo(view.getMainFrame());
                this.setResizable(true);

                this.setLayout(new BorderLayout());

                final JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout());
                mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));
                this.add(mainPanel, BorderLayout.CENTER);

                // Panel for buttons
                JPanel bottomPanel = new JPanel();
                bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
                mainPanel.add(bottomPanel, BorderLayout.EAST);

                final JTextArea lbResultFinal = new JTextArea(result);
                lbResultFinal.setLineWrap(true);
                lbResultFinal.setWrapStyleWord(true);
                lbResultFinal.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(lbResultFinal);
                mainPanel.add(scrollPane, BorderLayout.CENTER);

                // Modern PDF Print Button
                JButton stampaPdfButton = new JButton("Stampa PDF");
                stampaPdfButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                // Show print settings dialog
                                PrintSettings defaultSettings = new PrintSettings();
                                PrintSettingsDialog settingsDialog = new PrintSettingsDialog(
                                    (java.awt.Frame) ResultPane.this.getOwner(),
                                    defaultSettings
                                );
                                
                                PrintSettings selectedSettings = settingsDialog.showDialog();
                                
                                if (selectedSettings != null) {
                                    // Generate PDF with selected settings
                                    PdfDocumentGenerator generator = new PdfDocumentGenerator(selectedSettings);
                                    
                                    // Create temporary file path
                                    String timestamp = java.time.LocalDateTime.now()
                                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                                    String outputPath = System.getProperty("user.home") 
                                        + File.separator + "Downloads" 
                                        + File.separator + "calcolo_verghe_" + timestamp + ".pdf";
                                    
                                    if (generator.generatePdf(lbResultFinal.getText(), outputPath, title)) {
                                        // Apri il PDF automaticamente
                                        try {
                                            File pdfFile = new File(outputPath);
                                            if (Desktop.isDesktopSupported()) {
                                                Desktop.getDesktop().open(pdfFile);
                                            }
                                        } catch (IOException ex) {
                                            // Se non riesce ad aprire, continua comunque
                                            System.err.println("Impossibile aprire il PDF: " + ex.getMessage());
                                        }
                                        
                                        JOptionPane.showMessageDialog(ResultPane.this,
                                            "PDF generato con successo:\n" + outputPath,
                                            "Stampa Completata",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(ResultPane.this,
                                            "Errore durante la generazione del PDF",
                                            "Errore",
                                            JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                        }
                });
                bottomPanel.add(stampaPdfButton);

                // Classic Print Button (for backward compatibility)
                JButton stampaButton = new JButton("Stampa (Classica)");
                stampaButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(final ActionEvent e) {
                                try {
                                        boolean printed = lbResultFinal.print();
                                        if (printed) {
                                                JOptionPane.showMessageDialog(ResultPane.this,
                                                                "Stampa completata con successo!");
                                        } else {
                                                JOptionPane.showMessageDialog(ResultPane.this, "Stampa annullata.");
                                        }
                                } catch (PrinterException ex) {
                                        JOptionPane.showMessageDialog(ResultPane.this,
                                                        "Errore durante la stampa: " + ex.getMessage());
                                }
                        }
                });
                bottomPanel.add(stampaButton);
                this.setVisible(removeMode);
        };

}
