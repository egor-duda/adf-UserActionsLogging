package corpit.test.audit.jsf.view;

import javax.faces.event.AbortProcessingException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.event.LaunchPopupListener;

public class LoggingLaunchPopupListener implements LaunchPopupListener {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger (LoggingLaunchPopupListener.class);
        
    public LoggingLaunchPopupListener() {
        super();
    }

    @Override
    public void processLaunch(LaunchPopupEvent launchPopupEvent) throws AbortProcessingException {
        logger.fine ("input popup launch event for " 
                     + launchPopupEvent.getComponent().getClientId()
                     + "of type " 
                     + launchPopupEvent.getPopupType()
                     + "with value "
                     + launchPopupEvent.getSubmittedValue());
    }
}
