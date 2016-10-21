package corpit.test.audit.jsf.logging;

import corpit.test.audit.jsf.InjectedListener;

import java.util.logging.Level;

import javax.faces.event.AbortProcessingException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupCanceledListener;

public class LoggingPopupCancelledListener implements PopupCanceledListener, InjectedListener {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger (LoggingPopupCancelledListener.class);
    
    private static final Level loggingLevel = Level.FINE;
        
    public LoggingPopupCancelledListener() {
        super();
    }

    @Override
    public void processPopupCanceled(PopupCanceledEvent popupCanceledEvent) throws AbortProcessingException {
        logger.log (loggingLevel,
                     "popup cancelled event for " 
                     + popupCanceledEvent.getComponent().getClientId());
    }

    @Override
    public boolean isListenerActive() {
        return logger.isLoggable (loggingLevel);
    }
}
