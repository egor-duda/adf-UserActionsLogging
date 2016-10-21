package corpit.test.audit.jsf.logging;

import corpit.test.audit.jsf.InjectedListener;

import java.util.logging.Level;

import javax.faces.event.AbortProcessingException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogListener;

public class LoggingDialogListener implements DialogListener, InjectedListener {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger (LoggingDialogListener.class);
    
    private static final Level loggingLevel = Level.FINE;
    
    private final DialogListener delegate;
    
    public LoggingDialogListener () {
        super();
        this.delegate = null;
    }
    
    public LoggingDialogListener (DialogListener delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    public void processDialog (DialogEvent dialogEvent) throws AbortProcessingException {
        logger.log (loggingLevel,
                     "dialog event for " 
                     + dialogEvent.getComponent().getClientId() 
                     + ", outcome: " 
                     + dialogEvent.getOutcome().toString());
        if (delegate != null) delegate.processDialog(dialogEvent);
    }

    @Override
    public boolean isListenerActive() {
        return logger.isLoggable(loggingLevel) || (delegate instanceof InjectedListener && ((InjectedListener)delegate).isListenerActive());
    }
}
