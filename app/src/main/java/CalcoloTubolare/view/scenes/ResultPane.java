package CalcoloTubolare.view.scenes;

import javax.swing.JDialog;
import javax.swing.JFrame;

import CalcoloTubolare.view.View;

public class ResultPane extends JDialog{
        private static final String FONT = "Roboto";

        private final View view;

        public ResultPane(View view, String title, boolean removeMode) {
        super(view.getMainFrame(), title, ModalityType.APPLICATION_MODAL);
        this.view = view;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 500);
        this.setMaximumSize(this.getSize());
        this.setLocationRelativeTo(view.getMainFrame());
        this.setResizable(true);
        };
    
}
