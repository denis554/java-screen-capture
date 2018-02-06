/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenshot.capture;

/**
 *
 * @author Amir Aslan Aslani
 */
public class InvalidExtentionException extends Exception {
    public InvalidExtentionException(String extention){
        super("\"" + extention + "\" is invalid extention");
    }
}
