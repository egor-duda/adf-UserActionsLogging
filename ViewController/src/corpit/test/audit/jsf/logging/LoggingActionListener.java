package corpit.test.audit.jsf.logging;

import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import oracle.adf.controller.ControllerContext;
import oracle.adf.share.logging.ADFLogger;

public class LoggingActionListener implements ActionListener {
    
    private static ADFLogger logger = ADFLogger.createADFLogger(LoggingActionListener.class);
    private final ActionListener delegate;
    
    public LoggingActionListener() {
        super();
        this.delegate = null;
    }
    
    public LoggingActionListener (ActionListener delegate) {
        super();
        this.delegate = delegate;    
    }

    @Override
    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
        String compId = "*null*";
        String viewId = "*null*";
        UIComponent comp = actionEvent.getComponent();
        if (comp != null) {
            compId = comp.getId() + "/" + comp.getClass().getSimpleName();
        }
        ControllerContext controllerContext = ControllerContext.getInstance();
        if (controllerContext != null && 
            controllerContext.getCurrentViewPort() != null) {
            viewId = controllerContext.getCurrentViewPort().getViewId();
        }
        logger.fine("Action on " + compId + "@" + viewId);
        if (delegate != null) delegate.processAction (actionEvent);
    }
}