package Frame;

import javax.swing.JPanel ;
import java.awt.image.BufferedImage ;

import javax.imageio.ImageIO;
import java.awt.Graphics ;
import java.io.File;
import java.io.IOException ;

public class LoadImage extends JPanel {
    BufferedImage bufferedImage ;
    public LoadImage()  {
        super();
        // If you want to load your image inside the resources, do this
        try {
            bufferedImage = ImageIO.read(new File("C:\\Users\\mcavina\\OneDrive - CEPI s.p.a\\Desktop\\Test_Excel\\Calcolo_Verghe\\app\\src\\resources\\barboncino.jpeg"));
        } catch (IOException e) {
            e.printStackTrace() ;
        }
        //setOpaque(false);
    }


    @Override
    public void paintComponent(Graphics g) {
        // for bufferedImage 
        if (bufferedImage != null) {
            g.drawImage(bufferedImage, 0, 0, null) ;
        }
    }
}