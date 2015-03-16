package corpit.test.audit.jsf.view;

import javax.faces.event.FacesEvent;

import oracle.adf.share.logging.ADFLogger;

import oracle.adfinternal.view.faces.component.AdfViewRoot;

public class CustomViewRoot extends AdfViewRoot {
    
    private static ADFLogger logger = ADFLogger.createADFLogger (CustomViewRoot.class);
    public CustomViewRoot () {
        super();
    }

    public void queueEvent (FacesEvent facesEvent) {
        super.queueEvent(facesEvent);
        logger.info ("event of class " + facesEvent.getClass().getName() + " queued for " + facesEvent.getComponent().getId());
    }
}
