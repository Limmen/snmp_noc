/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/

package kth.se.exjobb.util;

import java.util.logging.Level;

/**
 * LogManager that handles logging of application exceptions that occur in the business layer.
 * @author Kim Hammar
 */
public class LogManager {
    
    private static final java.util.logging.Logger
            LOGGER = java.util.logging.Logger.getLogger("kth.se.exjobb");
       
    /**
     * Class constructor
     */
    public LogManager(){
        
    }
   
    /**
     * Will logg to the application-server default log
     * @param message to log
     * @param level level to log
     */
    public static void log(String message, Level level){
        LOGGER.log(level, message);
    }
}
