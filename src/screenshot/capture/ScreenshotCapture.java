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
 *
 * @author Amir Aslan Aslani
 */
public class ScreenshotCapture {

    public static void main(String[] args) {
        
        Options options = new Options();
        options.addOption("h","help", false, "Show help/usage");
        options.addOption("o","output", true, "Set output file name");
        options.addOption("e","extention", true, "Set output file extention (png|jpg|gif)");
        options.addOption("x","width", true, "Set output width");
        options.addOption("y","height", true, "Set output height");
        options.addOption("b","buffer", false, "Get screenshot in output buffer");
        
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
                String extention,
                       outputFile = cmd.getOptionValue("o");
                if(cmd.hasOption("e"))
                    extention = cmd.getOptionValue("e");
                else
                    extention = ScreenshotCapture.getFileExtention(outputFile);
                
                if(! isInValidImageFormats(extention))
                    throw new InvalidExtentionException(extention);
                
                screenshot.saveToFile(outputFile, extention);
            }
            
            if(cmd.hasOption("b")){
                String extention = Capture.IMAGE_FORMAT_PNG;
                if(cmd.hasOption("e"))
                    extention = cmd.getOptionValue("e");
                
                if(! isInValidImageFormats(extention))
                    throw new InvalidExtentionException(extention);
                
                screenshot.writeToStandardOutputStream(extention);
            }
        }
        catch (MissingArgumentException | UnrecognizedOptionException ex){
            System.out.println(ex.getMessage());
            help.printHelp("java -jar ScreenshotCapture.jar [options]", options);
        }
        catch (ParseException | AWTException | InvalidExtentionException ex) {
            System.err.println(ex.getMessage());
            Logger.getLogger(ScreenshotCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex){
            Logger.getLogger(ScreenshotCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getFileExtention(String file){
        String extention = "";
        for(int i = file.length() - 3;i < file.length();i ++){
            extention += file.charAt(i);
        }
        return extention;
    }
    
    public static boolean isInValidImageFormats(String format){
        return Arrays.asList(Capture.VALID_IMAGE_FORMATS).contains(format);
    }
}
