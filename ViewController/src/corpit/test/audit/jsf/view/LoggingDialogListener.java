package corpit.test.audit.jsf.view;

import java.util.logging.Level;

import javax.faces.event.AbortProcessingException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogListener;

public class LoggingDialogListener implements DialogListener, LoggingListener {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger (LoggingDialogListener.class);
    
    private static final Level loggingLevel = Level.FINE;
    
    public LoggingDialogListener () {
        super();
    }

    @Override
    public void processDialog (DialogEvent dialogEvent) throws AbortProcessingException {
        logger.log (loggingLevel,
                     "dialog event for " 
                     + dialogEvent.getComponent().getClientId() 
                     + ", outcome: " 
                     + dialogEvent.getOutcome().toString());
    }

    @Override
    public boolean isLoggingEnabled() {
        return logger.isLoggable(loggingLevel);
    }
}
