package fr.tikione.steam.cleaner.util;

import fr.tikione.steam.cleaner.util.conf.Config;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Log file handler.
 */
public class Log {
    
    /** Default logger. */
    private static Logger messagesLogger;
    
    static {
        try {
            Properties conf = new Properties();
            File backupLog4j = new File("conf/backup/tikione-steam-cleaner_log4j.properties");
            File userprofile = new File(Config.getProfilePath());
            //noinspection ResultOfMethodCallIgnored
            userprofile.mkdirs();
            File userLog4j = new File(userprofile.getAbsolutePath() + "/tikione-steam-cleaner_log4j.properties");
            if (!userLog4j.exists()) {
                org.apache.commons.io.FileUtils.copyFile(backupLog4j, userLog4j);
            }
            conf.load(new FileReader(userLog4j));
            conf.setProperty("log4j.appender.messages.File", Config.getProfilePath() + "/log/steamcleaner_messages.log");
            PropertyConfigurator.configure(conf);
            messagesLogger = Logger.getLogger("fr.tikione.steam.cleaner.log.info");
        } catch (IOException ex) {
            throw new RuntimeException("Cannot instantiate Log4j", ex);
        }
    }
    
    /** Suppresses default constructor, ensuring non-instantiability. */
    private Log() {
    }
    
    /**
     * Log a message object with the INFO Level.
     *
     * @param message the emssage to log.
     */
    public static void info(String message) {
        messagesLogger.info(message);
    }
    
    /**
     * Log a message object with the ERROR Level.
     *
     * @param ex the exception to log.
     */
    public static void error(Throwable ex) {
        messagesLogger.error("", ex);
    }
    
    /**
     * Log a message object with the ERROR Level.
     *
     * @param message the emssage to log.
     * @param ex the exception to log.
     */
    public static void error(String message, Throwable ex) {
        messagesLogger.error(message, ex);
    }
}
