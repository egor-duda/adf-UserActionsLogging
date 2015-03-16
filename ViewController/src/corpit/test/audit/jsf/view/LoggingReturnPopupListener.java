package corpit.test.audit.jsf.view;

import javax.faces.event.AbortProcessingException;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.event.ReturnPopupEvent;
import oracle.adf.view.rich.event.ReturnPopupListener;

public class LoggingReturnPopupListener implements ReturnPopupListener {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger (LoggingReturnPopupListener.class);
        
    public LoggingReturnPopupListener() {
        super();
    }

    @Override
    public void processReturn(ReturnPopupEvent returnPopupEvent) throws AbortProcessingException {
        logger.fine ("input popup return event for " 
                     + returnPopupEvent.getComponent().getClientId());
        
        /* TODO 
         * dump returned  returnPopupEvent.getReturnValue
         * it can be either
         * 1. RowKeySetImpl
         * or
         * 2. ArrayList of single HashMap<String,Object> (attributeName->attributeValue)
         * or
         * 3. something else
         */
    }
}
