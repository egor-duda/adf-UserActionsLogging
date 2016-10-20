package test.audit.client;

import javax.faces.context.FacesContext;

import javax.faces.event.FacesEvent;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public abstract class ClientAction {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger(ClientAction.class);
    
    private String context;
    
    public ClientAction (String context) {
        this.context = context;
    }
    
    public void invoke() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        if (!isMatchingContext()) {
            String errorMessage = "incorrect context: " + FacesContext.getCurrentInstance().getViewRoot().getViewId() + 
                                  ", expecting " + context;
            logger.severe (errorMessage);
            throw new RuntimeException (errorMessage);
        }
        String script = getActionJS();
        Service.getRenderKitService(fctx, ExtendedRenderKitService.class).addScript(fctx, script);
    };
    
    public boolean isMatchingContext () {
        FacesContext fctx = FacesContext.getCurrentInstance();
        return (context == null || context.equals(fctx.getViewRoot().getViewId()));
    }

    protected abstract String getActionJS();
    
    public String getDescription () {
        return toString();
    }

    public abstract boolean eventMatches (FacesEvent event);
}
