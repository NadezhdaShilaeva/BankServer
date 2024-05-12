package com.shilaeva.logger;

import java.io.IOException;
import java.util.logging.*;

public class FileLoggerProvider {
    private FileLoggerProvider() {}

    public static Logger getConfiguredLogger(String loggerName, String filePath) {
        Logger logger = Logger.getLogger(loggerName);

        try {
            FileHandler fh = new FileHandler(filePath, true);
            fh.setFormatter(new DateMessageFormatter());
            logger.addHandler(fh);

            logger.info("Start log message");
        } catch (IOException e) {
            logger.severe(String.format("Error creating FileHandler logger: %s", e));
        }

        return logger;
    }
}
