package test.audit.client;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public abstract class ClientAction {
    public void invoke() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        StringBuilder script = new StringBuilder();
        script = script.append("setTimeout(function(){" + getActionJS() + "}, 1000);");
        Service.getRenderKitService(fctx, ExtendedRenderKitService.class).addScript(fctx, script.toString());
    };

    protected abstract String getActionJS();

}
