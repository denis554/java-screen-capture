/*
 * Copyright (C) 2018 Amir Aslan Aslani
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package screenshot.capture;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

/**
 * This class created to capture the screenshot and resize that.
 * @author Amir Aslan Aslani
 */
public class Capture {
    public final static String  VALID_IMAGE_FORMATS[] = {
                                    "png",
                                    "gif",
                                    "jpg"
                                },
                                IMAGE_FORMAT_PNG = "png",
                                IMAGE_FORMAT_JPG = "jpg",
                                IMAGE_FORMAT_GIF = "gif";
    
    private final BufferedImage image;
    private final int width,height;
    
    /**
     * This constructor capture screenshot and save that in image property.
     * @param width Width of captured screenshot. If width is auto -1 given.
     * @param height Height of captured screenshot. If height is auto -1 given.
     * @throws AWTException 
     */
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
        
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(scaledImg, 0, 0, null);
        g.dispose();
    }
    
    /**
     * This method writes picture saved in image property to file with given address.
     * @param to Where picture should writes.
     * @param format Format of picture that should be write to file.
     * @throws IOException 
     */
    public void saveToFile(String to,String format) throws IOException{
        ImageIO.write(this.image, format, new File(to));
    }
    
    /**
     * Writes image to stream
     * @param format Format of picture that should be write to stream.
     * @param os Stream that should image write to.
     * @throws IOException 
     */
    private void writeToStream(String format, OutputStream os) throws IOException{
        ImageIO.write(this.image, format, os);
    }
    
    /**
     * This method writes picture saved in image property to standard output stream.
     * @param format Format of picture that should be write to standard output stream.
     * @throws IOException 
     */
    public void writeToStandardOutputStream(String format) throws IOException{
        this.writeToStream(format, System.out);
    }
    
    /**
     * 
     * @param format
     * @return
     * @throws IOException 
     */
    public ByteArrayOutputStream getBytes(String format) throws IOException{
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        this.writeToStream(format, os);
        return os;
    }
    
    /**
     * This method encodes picture to base64
     * @param format Format of picture that should be encoded to base64.
     * @return Base64 string of picture
     * @throws IOException 
     */
    public String getBase64(String format) throws IOException
    {
        return Base64.getEncoder().encodeToString(getBytes(format).toByteArray());
    }
}
