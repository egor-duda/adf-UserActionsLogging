package corpit.test.audit.jsf.view;

import javax.faces.event.AbortProcessingException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupCanceledListener;

public class LoggingPopupCancelledListener implements PopupCanceledListener {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger (LoggingPopupCancelledListener.class);
        
    public LoggingPopupCancelledListener() {
        super();
    }

    @Override
    public void processPopupCanceled(PopupCanceledEvent popupCanceledEvent) throws AbortProcessingException {
        logger.fine ("popup cancelled event for " 
                     + popupCanceledEvent.getComponent().getClientId());
    }
}
