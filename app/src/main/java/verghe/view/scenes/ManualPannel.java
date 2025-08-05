package verghe.view.scenes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import verghe.controller.ControllerModel;
import verghe.model.api.NameTubolar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class ManualPannel extends JPanel {

    private static final Integer TIME_TO_LAMP = 5;
    private static final String TUBOLAR_IMG_DYR = "tubolar/";
    private static final String PNG = ".png";

    private JLabel imageLabel = new JLabel();
    private JLabel tipe = new JLabel("Tipo:");
    JComboBox<NameTubolar> jComboBox = new JComboBox<>(NameTubolar.values());
    private JLabel lenght = new JLabel("Lunghezza:");
    private JTextField tfLenght = new JTextField();
    private JLabel quantity = new JLabel("Quantità:");
    private JTextField tfQuantity = new JTextField();
    private JButton btAddTubolar = new JButton("Aggiungi Tubolare");
    private JButton btRemoveTubolar = new JButton("Remuvi Tubolare");

    /**
     * Constructor for ManualPannel.
     * 
     * @param controller the controller model to manage the application logic
     */
    public ManualPannel(final ControllerModel controller) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(0, 0, 0, 10));
        imageLabel.setPreferredSize(new Dimension(200, 200));
        this.add(tipe);
        this.add(jComboBox);
        this.add(Box.createHorizontalStrut(5));
        this.add(imageLabel);
        this.add(Box.createHorizontalStrut(5));
        tfLenght.setPreferredSize(new Dimension(80, 10));
        this.add(lenght);
        this.add(tfLenght);
        this.add(Box.createHorizontalStrut(5));
        tfQuantity.setPreferredSize(new Dimension(80, 10));
        this.add(quantity);
        this.add(tfQuantity);
        this.add(Box.createHorizontalStrut(5));
        this.add(btAddTubolar);
        this.add(Box.createHorizontalStrut(5));
        this.add(btRemoveTubolar);

        // Azioni pulsanti
        btAddTubolar.addActionListener(e -> {
            noLenghtandQuantity();
            String s = String.valueOf(jComboBox.getSelectedItem());
            controller.newTubolarList(s, Integer.parseInt(tfLenght.getText()), Integer.parseInt(tfQuantity.getText()));
        });

        btRemoveTubolar.addActionListener(e -> {
            controller.getTubolarList().removeTubolar(String.valueOf(jComboBox.getSelectedItem()),
                    Integer.parseInt(tfLenght.getText()));
        });

        // Timer per aggiornare l'immagine
        final Timer timer = new Timer(TIME_TO_LAMP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var image = new ImageIcon(
                            ClassLoader.getSystemResource(TUBOLAR_IMG_DYR + jComboBox.getSelectedItem() + PNG));
                    Image newimg = image.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(newimg));
                    // jpWest.setVisible(cbManualInput.isSelected());

                } catch (Exception l) {
                    imageLabel.setIcon(null);
                }
            }
        });
        timer.start();

    }

        private void noLenghtandQuantity() {
        if (tfLenght.getText().isBlank()
                || tfQuantity.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Inserire lunghezza e quantità.");
        }
    }
}
