package CalcoloTubolare.view.scenes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import CalcoloTubolare.controller.MainMenuController;
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

    final JButton btRestart = new JButton("Delete All");
    final JTextArea lbResultFinal = new JTextArea();

    final JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    final JPanel jpNORTH = new JPanel(new FlowLayout());
    final JPanel jpEast = new JPanel();
    final JPanel jpWest = new JPanel();

    JLabel imageLabel = new JLabel();

    final JButton btStressed = new JButton("Stressed?");
    JLabel picLabel = new JLabel();

    private final MainMenuController controller;
    private final JPanel mainMenuPanel;

    /**
     * Creates a new {@link MainMenuScene}.
     * 
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

        JComboBox<NameTubolar> jComboBox = new JComboBox<>(NameTubolar.values());

        picLabel.setIcon(new ImageIcon(ClassLoader.getSystemResource(nameStresedPic())));
        picLabel.setVisible(false);
        picLabel.setMaximumSize(new Dimension(20, 20));

        jpEast.setLayout(new BoxLayout(jpEast, 1));

        mainMenuPanel.add(jp, BorderLayout.CENTER);
        mainMenuPanel.add(jpNORTH, BorderLayout.NORTH);
        mainMenuPanel.add(jpEast, BorderLayout.EAST);
        mainMenuPanel.add(jpWest, BorderLayout.WEST);

        mainMenuPanel.add(btStressed, BorderLayout.SOUTH);

        jpNORTH.add(jComboBox);

        jpNORTH.add(tfLenght);
        jpNORTH.add(tfQuantity);
        jpNORTH.add(btAdd);
        jpNORTH.add(btRem);

        jpWest.add(imageLabel);

        jpEast.add(btCalc);
        jpEast.add(btCalcTotale);
        jpEast.add(btCalcFromExcel);
        jpEast.add(btRestart);

        jp.add(lbResult);
        jp.add(lbResultFinal);
        jp.add(picLabel);

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
                lbResultFinal.setText(CalcolatorTubolar

                        .printCuttedTubolarSmoll(
                                CalcolatorTubolar.calcoloTotal(controller.getTubolarList().getMultiQueue())));
            }

        });

        btCalcTotale.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lbResultFinal.setText(CalcolatorTubolar
                        .printCuttedTubolar(
                                CalcolatorTubolar.calcoloTotal(controller.getTubolarList().getMultiQueue())));
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

        btStressed.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                picLabel.setVisible(!picLabel.isVisible());
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
    }

    @Override
    public JPanel getPanel() {
        return mainMenuPanel;
    }

    @Override
    public String getSceneName() {
        return "Main";
    }
    
    private String nameStresedPic(){
        var user = System.getProperty("user.home");
        if(user.contains("scibilia") || user.contains("cavina")){
            return "view/OIP.jpg";
        }else{
            return "view/barboncino.jpeg";
        }
    }

}
