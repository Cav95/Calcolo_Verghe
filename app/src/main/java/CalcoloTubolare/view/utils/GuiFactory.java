package CalcoloTubolare.view.utils;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

public class GuiFactory {

    static public JButton getButtom(final String name, final Color backgroud, final Color foreGround,
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
