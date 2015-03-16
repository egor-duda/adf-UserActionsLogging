package corpit.test.audit.jsf.view;

import javax.faces.event.AbortProcessingException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogListener;

public class LoggingDialogListener implements DialogListener {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger (LoggingDialogListener.class);
    
    public LoggingDialogListener () {
        super();
    }

    @Override
    public void processDialog (DialogEvent dialogEvent) throws AbortProcessingException {
        logger.fine ("dialog event for " 
                     + dialogEvent.getComponent().getClientId() 
                     + ", outcome: " 
                     + dialogEvent.getOutcome().toString());
    }
}
