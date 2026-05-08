package verghe.view.scenes;

import verghe.model.print.PrintSettings;
import verghe.model.print.PrintSettings.PageFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * PrintSettingsDialog class for configuring PDF print settings
 * Allows users to customize font, size, colors, and margins
 */
public class PrintSettingsDialog extends JDialog {
    
    private PrintSettings settings;
    private boolean confirmed = false;
    
    // UI Components
    private JComboBox<String> fontCombo;
    private JSpinner fontSizeSpinner;
    private JSpinner titleFontSizeSpinner;
    private JSpinner marginTopSpinner;
    private JSpinner marginBottomSpinner;
    private JSpinner marginLeftSpinner;
    private JSpinner marginRightSpinner;
    private JComboBox<PageFormat> pageFormatCombo;
    private JSpinner lineSpacingSpinner;
    private JButton buttonOK;
    private JButton buttonCancel;
    
    /**
     * Constructor
     */
    public PrintSettingsDialog(Frame owner, PrintSettings initialSettings) {
        super(owner, "Impostazioni Stampa", true);
        this.settings = initialSettings != null ? initialSettings.copy() : new PrintSettings();
        
        initializeUI();
        loadSettings();
    }
    
    /**
     * Initialize UI components
     */
    private void initializeUI() {
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(500, 600);
        this.setLocationRelativeTo(this.getOwner());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Create tabbed pane for organized settings
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Font settings tab
        tabbedPane.addTab("Font", createFontPanel());
        
        // Page settings tab
        tabbedPane.addTab("Pagina", createPagePanel());
        
        // Margin settings tab
        tabbedPane.addTab("Margini", createMarginsPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Annulla");
        
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                dispose();
            }
        });
        
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose();
            }
        });
        
        buttonPanel.add(buttonOK);
        buttonPanel.add(buttonCancel);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
    }
    
    /**
     * Create font settings panel
     */
    private JPanel createFontPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Font selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Font:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        fontCombo = new JComboBox<>(PrintSettings.getAvailableFonts());
        panel.add(fontCombo, gbc);
        
        // Font size
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Dimensione Font (pt):"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(10.0, 6.0, 24.0, 1.0));
        panel.add(fontSizeSpinner, gbc);
        
        // Title font size
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Dimensione Titolo (pt):"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        titleFontSizeSpinner = new JSpinner(new SpinnerNumberModel(14.0, 10.0, 32.0, 1.0));
        panel.add(titleFontSizeSpinner, gbc);
        
        // Line spacing
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Interlinea:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        lineSpacingSpinner = new JSpinner(new SpinnerNumberModel(1.2, 0.8, 2.5, 0.1));
        panel.add(lineSpacingSpinner, gbc);
        
        return panel;
    }
    
    /**
     * Create page settings panel
     */
    private JPanel createPagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Page format
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Formato Pagina:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        pageFormatCombo = new JComboBox<>(PageFormat.values());
        panel.add(pageFormatCombo, gbc);
        
        return panel;
    }
    
    /**
     * Create margin settings panel
     */
    private JPanel createMarginsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Top margin
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Margine Superiore (mm):"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        marginTopSpinner = new JSpinner(new SpinnerNumberModel(40, 10, 100, 5));
        panel.add(marginTopSpinner, gbc);
        
        // Bottom margin
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Margine Inferiore (mm):"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        marginBottomSpinner = new JSpinner(new SpinnerNumberModel(40, 10, 100, 5));
        panel.add(marginBottomSpinner, gbc);
        
        // Left margin
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Margine Sinistro (mm):"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        marginLeftSpinner = new JSpinner(new SpinnerNumberModel(40, 10, 100, 5));
        panel.add(marginLeftSpinner, gbc);
        
        // Right margin
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Margine Destro (mm):"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        marginRightSpinner = new JSpinner(new SpinnerNumberModel(40, 10, 100, 5));
        panel.add(marginRightSpinner, gbc);
        
        return panel;
    }
    
    /**
     * Load current settings into UI components
     */
    private void loadSettings() {
        fontCombo.setSelectedItem(settings.getFontName());
        fontSizeSpinner.setValue((double) settings.getFontSize());
        titleFontSizeSpinner.setValue((double) settings.getTitleFontSize());
        marginTopSpinner.setValue((int) settings.getMarginTop());
        marginBottomSpinner.setValue((int) settings.getMarginBottom());
        marginLeftSpinner.setValue((int) settings.getMarginLeft());
        marginRightSpinner.setValue((int) settings.getMarginRight());
        pageFormatCombo.setSelectedItem(settings.getPageFormat());
        lineSpacingSpinner.setValue((double) settings.getLineSpacing());
    }
    
    /**
     * Save UI component values to settings
     */
    private void saveSettings() {
        settings.setFontName((String) fontCombo.getSelectedItem());
        settings.setFontSize(((Double) fontSizeSpinner.getValue()).floatValue());
        settings.setTitleFontSize(((Double) titleFontSizeSpinner.getValue()).floatValue());
        settings.setMarginTop(((Integer) marginTopSpinner.getValue()).floatValue());
        settings.setMarginBottom(((Integer) marginBottomSpinner.getValue()).floatValue());
        settings.setMarginLeft(((Integer) marginLeftSpinner.getValue()).floatValue());
        settings.setMarginRight(((Integer) marginRightSpinner.getValue()).floatValue());
        settings.setPageFormat((PageFormat) pageFormatCombo.getSelectedItem());
        settings.setLineSpacing(((Double) lineSpacingSpinner.getValue()).floatValue());
    }
    
    /**
     * Show dialog and return settings if confirmed
     */
    public PrintSettings showDialog() {
        this.setVisible(true);
        
        if (confirmed) {
            saveSettings();
            return settings;
        }
        
        return null;
    }
    
    /**
     * Check if dialog was confirmed
     */
    public boolean isConfirmed() {
        return confirmed;
    }
    
    /**
     * Get current settings
     */
    public PrintSettings getSettings() {
        if (confirmed) {
            saveSettings();
        }
        return settings;
    }
}
