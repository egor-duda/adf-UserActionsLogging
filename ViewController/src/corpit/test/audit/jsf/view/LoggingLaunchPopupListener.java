package corpit.test.audit.jsf.view;

import java.util.logging.Level;

import javax.faces.event.AbortProcessingException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.event.LaunchPopupListener;

public class LoggingLaunchPopupListener implements LaunchPopupListener, LoggingListener {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger (LoggingLaunchPopupListener.class);
    
    private static final Level loggingLevel = Level.FINE;
        
    public LoggingLaunchPopupListener() {
        super();
    }

    @Override
    public void processLaunch(LaunchPopupEvent launchPopupEvent) throws AbortProcessingException {
        logger.log (loggingLevel,
                     "input popup launch event for " 
                     + launchPopupEvent.getComponent().getClientId()
                     + "of type " 
                     + launchPopupEvent.getPopupType()
                     + "with value "
                     + launchPopupEvent.getSubmittedValue());
    }

    @Override
    public boolean isLoggingEnabled() {
        return logger.isLoggable (loggingLevel);
    }
}
