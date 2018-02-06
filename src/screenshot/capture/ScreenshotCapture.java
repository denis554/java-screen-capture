/*
 * Copyright (C) 2018 mrse
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
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

/**
 * This class is decides on arguments and sets parameters that Capture class called from that.
 * @author Amir Aslan Aslani
 */
public class ScreenshotCapture {

    public static void main(String[] args) {
        
        Options options = new Options();
        options.addOption("h","help", false, "Show help/usage");
        options.addOption("o","output", true, "Set output file name");
        options.addOption("e","extension", true, "Set output file extension (png|jpg|gif)");
        options.addOption("x","width", true, "Set output width");
        options.addOption("y","height", true, "Set output height");
        options.addOption("s","standardstream", false, "Get screenshot in standard output stream");
        
        HelpFormatter help = new HelpFormatter();
        help.setDescPadding(5);
        help.setLeftPadding(2);
        
        CommandLineParser parser = new DefaultParser();
        
        try {
            CommandLine cmd = parser.parse(options, args);
            int width = -1,
                height = -1;
            
            if(cmd.hasOption("h")){
                help.printHelp("java -jar ScreenshotCapture.jar [options]", options);
                return;
            }
            
            if(cmd.hasOption("x"))
                width = Integer.valueOf(cmd.getOptionValue("x"));
            
            if(cmd.hasOption("y"))
                height = Integer.valueOf(cmd.getOptionValue("y"));
            
            Capture screenshot = new Capture(width,height);
            
            if(cmd.hasOption("o")){
                String extension,
                       outputFile = cmd.getOptionValue("o");
                if(cmd.hasOption("e"))
                    extension = cmd.getOptionValue("e");
                else
                    extension = ScreenshotCapture.getFileExtension(outputFile);
                
                if(! isInValidImageFormats(extension))
                    throw new InvalidExtensionException(extension);
                
                screenshot.saveToFile(outputFile, extension);
            }
            
            if(cmd.hasOption("s")){
                String extension = Capture.IMAGE_FORMAT_PNG;
                if(cmd.hasOption("e"))
                    extension = cmd.getOptionValue("e");
                
                if(! isInValidImageFormats(extension))
                    throw new InvalidExtensionException(extension);
                
                screenshot.writeToStandardOutputStream(extension);
            }
        }
        catch (MissingArgumentException | UnrecognizedOptionException ex){
            System.out.println(ex.getMessage());
            help.printHelp("java -jar ScreenshotCapture.jar [options]", options);
        }
        catch (ParseException | AWTException | InvalidExtensionException ex) {
            System.err.println(ex.getMessage());
            Logger.getLogger(ScreenshotCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex){
            Logger.getLogger(ScreenshotCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method gets a file path and return 3-Characters extension of that file. 
     * @param file File path that we want to get that's extension.
     * @return 3-Character extension of file at given path. (Last 3 character of file path)
     */
    public static String getFileExtension(String file){
        String extension = "";
        for(int i = file.length() - 3;i < file.length();i ++){
            extension += file.charAt(i);
        }
        return extension;
    }
    
    /**
     * This method checks given extension is valid or not.
     * @param format That extension we want to know is valid or not.
     * @return If given extension is valid returns True else returns False.
     */
    public static boolean isInValidImageFormats(String format){
        return Arrays.asList(Capture.VALID_IMAGE_FORMATS).contains(format);
    }
}
