package test.audit.client;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public abstract class ClientAction {
    
    private String context;
    
    public ClientAction (String context) {
        this.context = context;
    }
    
    public void invoke() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        if (!isMatchingContext()) return;
        String script = getActionJS();
        Service.getRenderKitService(fctx, ExtendedRenderKitService.class).addScript(fctx, script);
    };
    
    public boolean isMatchingContext () {
        FacesContext fctx = FacesContext.getCurrentInstance();
        return (context == null || context.equals(fctx.getViewRoot().getViewId()));
    }

    protected abstract String getActionJS();

}
