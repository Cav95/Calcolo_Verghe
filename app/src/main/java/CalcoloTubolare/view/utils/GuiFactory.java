package CalcoloTubolare.view.utils;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

public class GuiFactory {

    private GuiFactory() {
        // Prevent instantiation
    }

    /**
     * Creates a JButton with specified properties.
     * 
     * @param name        the text of the button
     * @param backgroud   the background color of the button
     * @param foreGround  the foreground color of the button
     * @param font        the font of the button text
     * @param action      the action listener for the button
     * @param size        the size of the button
     * @param isEnabled   whether the button is enabled
     * @return a JButton with specified properties
     */
    static final public JButton getButtom(final String name, final Color backgroud, final Color foreGround,
            final Font font, final ActionListener action , final Dimension size , boolean isEnabled) {

        final JButton button = new JButton(name);
        button.setBackground(backgroud);
        button.setForeground(foreGround);
        button.setFont(font);
        button.addActionListener(action);
        button.setSize(size);
        button.setEnabled(isEnabled);
        button.setFocusPainted(false);
        return button;

    }
    
}
