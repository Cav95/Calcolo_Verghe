package verghe.model.print;

import java.awt.BasicStroke;
import java.awt.Color;

/**
 * PrintSettings class that holds configuration for document printing.
 * This class encapsulates font settings, margins, and layout preferences.
 */
public class PrintSettings {
    
    // Font settings
    private String fontName;
    private float fontSize;
    private float titleFontSize;
    
    // Page settings
    private float marginTop;
    private float marginBottom;
    private float marginLeft;
    private float marginRight;
    
    // Colors
    private Color textColor;
    private Color titleColor;
    private Color headerColor;
    
    // Line spacing
    private float lineSpacing;
    
    // Page format
    private PageFormat pageFormat;
    
    /**
     * Enum for page format sizes
     */
    public enum PageFormat {
        A4(595, 842),
        LETTER(612, 792),
        A3(842, 1190);
        
        private final float width;
        private final float height;
        
        PageFormat(float width, float height) {
            this.width = width;
            this.height = height;
        }
        
        public float getWidth() {
            return width;
        }
        
        public float getHeight() {
            return height;
        }
    }
    
    /**
     * Constructor with default settings
     */
    public PrintSettings() {
        this.fontName = "Helvetica";
        this.fontSize = 10;
        this.titleFontSize = 14;
        this.marginTop = 40;
        this.marginBottom = 40;
        this.marginLeft = 40;
        this.marginRight = 40;
        this.textColor = Color.BLACK;
        this.titleColor = Color.BLACK;
        this.headerColor = new Color(70, 130, 180); // Steel blue
        this.lineSpacing = 1.2f;
        this.pageFormat = PageFormat.A4;
    }
    
    // Getters and Setters
    public String getFontName() {
        return fontName;
    }
    
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
    
    public float getFontSize() {
        return fontSize;
    }
    
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }
    
    public float getTitleFontSize() {
        return titleFontSize;
    }
    
    public void setTitleFontSize(float titleFontSize) {
        this.titleFontSize = titleFontSize;
    }
    
    public float getMarginTop() {
        return marginTop;
    }
    
    public void setMarginTop(float marginTop) {
        this.marginTop = marginTop;
    }
    
    public float getMarginBottom() {
        return marginBottom;
    }
    
    public void setMarginBottom(float marginBottom) {
        this.marginBottom = marginBottom;
    }
    
    public float getMarginLeft() {
        return marginLeft;
    }
    
    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }
    
    public float getMarginRight() {
        return marginRight;
    }
    
    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }
    
    public Color getTextColor() {
        return textColor;
    }
    
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    
    public Color getTitleColor() {
        return titleColor;
    }
    
    public void setTitleColor(Color titleColor) {
        this.titleColor = titleColor;
    }
    
    public Color getHeaderColor() {
        return headerColor;
    }
    
    public void setHeaderColor(Color headerColor) {
        this.headerColor = headerColor;
    }
    
    public float getLineSpacing() {
        return lineSpacing;
    }
    
    public void setLineSpacing(float lineSpacing) {
        this.lineSpacing = lineSpacing;
    }
    
    public PageFormat getPageFormat() {
        return pageFormat;
    }
    
    public void setPageFormat(PageFormat pageFormat) {
        this.pageFormat = pageFormat;
    }
    
    /**
     * Get available fonts on the system
     */
    public static String[] getAvailableFonts() {
        // Common fonts available on most systems
        return new String[]{
            "Helvetica",
            "Times New Roman",
            "Courier New",
            "Arial",
            "Verdana",
            "Georgia",
            "Comic Sans MS",
            "Trebuchet MS"
        };
    }
    
    /**
     * Create a copy of this PrintSettings
     */
    public PrintSettings copy() {
        PrintSettings copy = new PrintSettings();
        copy.fontName = this.fontName;
        copy.fontSize = this.fontSize;
        copy.titleFontSize = this.titleFontSize;
        copy.marginTop = this.marginTop;
        copy.marginBottom = this.marginBottom;
        copy.marginLeft = this.marginLeft;
        copy.marginRight = this.marginRight;
        copy.textColor = this.textColor;
        copy.titleColor = this.titleColor;
        copy.headerColor = this.headerColor;
        copy.lineSpacing = this.lineSpacing;
        copy.pageFormat = this.pageFormat;
        return copy;
    }
}
