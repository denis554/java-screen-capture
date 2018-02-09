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
package screenshot.capture.streaming;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.awt.AWTException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import screenshot.capture.Capture;

/**
 *
 * @author Amir Aslan Aslani
 */
public abstract class ServerHandler implements HttpHandler {
    
    protected final int height;
    protected final int width;
    protected final String format;
    
    /**
     * 
     * @param width
     * @param height
     * @param format 
     */
    public ServerHandler(int width,int height, String format){
        this.width = width;
        this.height = height;
        this.format = format;
    }   
}

/**
 * 
 * @author Amir Aslan Aslani
 */
class Base64Handler extends ServerHandler{
    /**
     * 
     * @param width
     * @param height
     * @param format 
     */
    public Base64Handler(int width, int height, String format) {
        super(width, height,format);
    }

    /**
     * 
     * @param t
     * @throws IOException 
     */
    @Override
    public void handle(HttpExchange t) throws IOException {
        try {
            Capture capture = new Capture(this.width, this.height);
            String response = capture.getBase64(this.format);
            t.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (AWTException ex) {
            Logger.getLogger(Base64Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/**
 * 
 * @author Amir Aslan Aslani
 */
class BytesHandler extends ServerHandler{
    /**
     * 
     * @param width
     * @param height
     * @param format 
     */
    public BytesHandler(int width, int height, String format) {
        super(width, height,format);
    }

    /**
     * 
     * @param t
     * @throws IOException 
     */
    @Override
    public void handle(HttpExchange t) throws IOException {
        try {
            Capture capture = new Capture(this.width, this.height);
            byte[] response = capture.getBytes(this.format).toByteArray();
            t.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);

            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        } catch (AWTException ex) {
            Logger.getLogger(Base64Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}