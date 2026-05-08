package verghe.model.print;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * PdfDocumentGenerator class that generates formatted PDF documents using iText7.
 * Supports customization of fonts, sizes, colors, and margins.
 */
public class PdfDocumentGenerator {
    
    private static final Logger logger = LoggerFactory.getLogger(PdfDocumentGenerator.class);
    private static final String FONT_PATH = "fonts/";
    private PrintSettings settings;
    
    /**
     * Constructor with default settings
     */
    public PdfDocumentGenerator() {
        this.settings = new PrintSettings();
    }
    
    /**
     * Constructor with custom settings
     */
    public PdfDocumentGenerator(PrintSettings settings) {
        this.settings = settings != null ? settings : new PrintSettings();
    }
    
    /**
     * Generate PDF document from text content
     * 
     * @param textContent the content to be written to PDF
     * @param outputPath the file path where the PDF will be saved
     * @param title the document title
     * @return true if generation was successful, false otherwise
     */
    public boolean generatePdf(String textContent, String outputPath, String title) {
        try {
            // Create directory if it doesn't exist
            Path path = Paths.get(outputPath).getParent();
            if (path != null && !Files.exists(path)) {
                Files.createDirectories(path);
            }
            
            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(outputPath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            
            // Set page size based on settings
            PageSize pageSize = new PageSize(
                settings.getPageFormat().getWidth(),
                settings.getPageFormat().getHeight()
            );
            pdfDocument.setDefaultPageSize(pageSize);
            
            Document document = new Document(pdfDocument);
            document.setMargins(
                settings.getMarginTop(),
                settings.getMarginRight(),
                settings.getMarginBottom(),
                settings.getMarginLeft()
            );
            
            // Add title
            addTitle(document, title);
            
            // Add metadata
            addMetadata(document);
            
            // Add content
            addContent(document, textContent);
            
            // Close document
            document.close();
            
            logger.info("PDF generated successfully: {}", outputPath);
            return true;
            
        } catch (IOException e) {
            logger.error("Error generating PDF: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Generate PDF and print to default printer
     * 
     * @param textContent the content to be printed
     * @param title the document title
     * @return true if the process was successful
     */
    public boolean generateAndPrint(String textContent, String title) {
        try {
            // Create temporary file
            String tempDir = System.getProperty("java.io.tmpdir");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String outputPath = tempDir + File.separator + "calcolo_verghe_" + timestamp + ".pdf";
            
            if (!generatePdf(textContent, outputPath, title)) {
                return false;
            }
            
            // Open PDF with default system viewer (print dialog can be triggered from there)
            // For true printing, we could use a process to open with print command
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;
            
            if (os.contains("win")) {
                pb = new ProcessBuilder("cmd.exe", "/c", "start", outputPath);
            } else if (os.contains("mac")) {
                pb = new ProcessBuilder("open", outputPath);
            } else {
                pb = new ProcessBuilder("xdg-open", outputPath);
            }
            
            pb.start();
            logger.info("PDF opened for printing: {}", outputPath);
            return true;
            
        } catch (IOException e) {
            logger.error("Error during print process: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Add title section to the document
     */
    private void addTitle(Document document, String title) throws IOException {
        PdfFont font = createFont(settings.getFontName(), true);
        
        Paragraph titlePara = new Paragraph(title)
            .setFont(font)
            .setFontSize(settings.getTitleFontSize())
            .setFontColor(new DeviceRgb(
                settings.getTitleColor().getRed(),
                settings.getTitleColor().getGreen(),
                settings.getTitleColor().getBlue()
            ))
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(20);
        
        document.add(titlePara);
        
        // Add separator line
        document.add(new Paragraph()
            .setMarginBottom(10));
    }
    
    /**
     * Add metadata section (date, time, user)
     */
    private void addMetadata(Document document) throws IOException {
        PdfFont font = createFont(settings.getFontName(), false);
        
        String metadata = "Generated: " + LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        ) + " | User: " + System.getProperty("user.name", "Unknown");
        
        Paragraph metaPara = new Paragraph(metadata)
            .setFont(font)
            .setFontSize(settings.getFontSize() - 2)
            .setFontColor(new DeviceRgb(120, 120, 120))
            .setTextAlignment(TextAlignment.RIGHT)
            .setMarginBottom(15);
        
        document.add(metaPara);
    }
    
    /**
     * Add main content to the document
     */
    private void addContent(Document document, String textContent) throws IOException {
        PdfFont font = createFont(settings.getFontName(), false);
        
        // Split content into lines to preserve formatting
        String[] lines = textContent.split("\n");
        
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                // Add spacing for empty lines
                document.add(new Paragraph("").setFixedLeading(settings.getFontSize() * settings.getLineSpacing()));
            } else {
                Paragraph para = new Paragraph(line)
                    .setFont(font)
                    .setFontSize(settings.getFontSize())
                    .setFontColor(new DeviceRgb(
                        settings.getTextColor().getRed(),
                        settings.getTextColor().getGreen(),
                        settings.getTextColor().getBlue()
                    ))
                    .setFixedLeading(settings.getFontSize() * settings.getLineSpacing());
                
                document.add(para);
            }
        }
    }
    
    /**
     * Create PdfFont from font name
     * Attempts to use system fonts, falls back to standard PDF fonts
     */
    private PdfFont createFont(String fontName, boolean bold) throws IOException {
        try {
            // Try to load system font
            String fontPath = getFontPath(fontName, bold);
            if (fontPath != null) {
                FontProgram fontProgram = FontProgramFactory.createFont(fontPath);
                return PdfFontFactory.createFont(fontProgram, "Cp1252");
            }
        } catch (IOException e) {
            logger.warn("Could not load system font {}, falling back to standard font", fontName);
        }
        
        // Fallback to standard PDF fonts
        return PdfFontFactory.createFont(getStandardFontName(fontName, bold));
    }
    
    /**
     * Get system font path based on operating system
     */
    private String getFontPath(String fontName, boolean bold) {
        String os = System.getProperty("os.name").toLowerCase();
        String fontFileName = fontName.toLowerCase().replace(" ", "") + (bold ? "-bold" : "") + ".ttf";
        
        if (os.contains("win")) {
            String fontPath = "C:\\Windows\\Fonts\\" + fontFileName;
            if (Files.exists(Paths.get(fontPath))) {
                return fontPath;
            }
        } else if (os.contains("mac")) {
            String fontPath = "/Library/Fonts/" + fontFileName;
            if (Files.exists(Paths.get(fontPath))) {
                return fontPath;
            }
        } else {
            String fontPath = "/usr/share/fonts/truetype/" + fontFileName;
            if (Files.exists(Paths.get(fontPath))) {
                return fontPath;
            }
        }
        
        return null;
    }
    
    /**
     * Get standard PDF font name
     */
    private String getStandardFontName(String fontName, boolean bold) {
        String lowerName = fontName.toLowerCase();
        String suffix = bold ? "-Bold" : "";
        
        if (lowerName.contains("times")) {
            return "Times-Roman" + suffix;
        } else if (lowerName.contains("courier")) {
            return "Courier" + suffix;
        } else if (lowerName.contains("arial") || lowerName.contains("helvetica")) {
            return "Helvetica" + suffix;
        }
        
        return "Helvetica" + suffix;
    }
    
    /**
     * Update print settings
     */
    public void setSettings(PrintSettings settings) {
        this.settings = settings != null ? settings : new PrintSettings();
    }
    
    /**
     * Get current print settings
     */
    public PrintSettings getSettings() {
        return settings;
    }
}
