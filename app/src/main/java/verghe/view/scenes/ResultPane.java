package verghe.view.scenes;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import verghe.view.View;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

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

                // Correggi il nome del pannello
                JPanel bottomPanel = new JPanel();
                bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
                mainPanel.add(bottomPanel, BorderLayout.EAST);

                final JTextArea lbResultFinal = new JTextArea(result);
                lbResultFinal.setLineWrap(true);
                lbResultFinal.setWrapStyleWord(true);
                lbResultFinal.setEditable(false); // opzionale: rende la textarea di sola lettura

                JScrollPane scrollPane = new JScrollPane(lbResultFinal);
                mainPanel.add(scrollPane, BorderLayout.CENTER);

                // Pulsante "Stampa"
                JButton stampaButton = new JButton("Stampa");
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
