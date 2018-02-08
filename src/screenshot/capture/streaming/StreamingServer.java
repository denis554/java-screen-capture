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

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import screenshot.capture.Capture;

/**
 *
 * @author Amir Aslan Aslani
 */
public class StreamingServer extends Thread{
    private final int port,width,height;
    
    public StreamingServer(int port, int width, int height) throws IOException{
        super();
        this.port = port;
        this.height = height;
        this.width = width;
    }
    
    @Override
    public void run(){
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
            
            server.createContext(
                    "/", 
                    new BytesHandler(this.width, this.height, Capture.IMAGE_FORMAT_PNG)
            );
            
            server.createContext(
                    "/base64", 
                    new Base64Handler(this.width, this.height, Capture.IMAGE_FORMAT_PNG)
            );
            server.setExecutor(null); // creates a default executor
            
            System.out.println("Server is running on " + port + " port...");
            server.start();
            
        } catch (IOException ex) {
            Logger.getLogger(StreamingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
