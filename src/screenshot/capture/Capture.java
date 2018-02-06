package screenshot.capture;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Amir Aslan Aslani
 */
public class Capture {
    final static String VALID_IMAGE_FORMATS[] = {"png","gif","jpg"},
                        IMAGE_FORMAT_PNG = "png",
                        IMAGE_FORMAT_JPG = "jpg",
                        IMAGE_FORMAT_GIF = "gif";
    
    private final BufferedImage image;
    private final int width,height;
    
    public Capture(int width, int height) throws AWTException{
        BufferedImage bimg = new Robot().createScreenCapture(
                new Rectangle(
                        Toolkit.getDefaultToolkit().getScreenSize()
                )
        );

        if(width < 0 && height < 0){
            width = bimg.getWidth();
            height = bimg.getHeight();
        }
        else if(width < 0){
            width = height * bimg.getWidth() / bimg.getHeight();
        }
        else if(height < 0){
            height = width * bimg.getHeight() / bimg.getWidth();
        }
        
        this.width = width;
        this.height = height;
        
        Image scaledImg = bimg.getScaledInstance(width, height, BufferedImage.SCALE_AREA_AVERAGING);
        
        image = new BufferedImage(width, height, bimg.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(scaledImg, 0, 0, null);
        g.dispose();
    }
    
    public void saveToFile(String to,String format) throws IOException{
        ImageIO.write(this.image, format, new File(to));
    }
    
    public void writeToStandardOutputStream(String format) throws IOException{
        ImageIO.write(this.image, format, System.out);
    }
}
