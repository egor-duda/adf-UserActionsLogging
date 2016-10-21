package test.audit;

import java.util.Map;

import javax.faces.event.FacesEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.adapter.AdapterDCService;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.render.ClientEvent;

import test.audit.client.ClientAction;
import test.audit.client.ClientScript;

public class UserActionsDataControl {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger(UserActionsDataControl.class);
    
    private ClientScript clientScript = new ClientScript();

    public void loadClientScript(ClientAction[] actions) {
        clientScript.loadScript(actions);
    }

    public ClientAction getAction() {
        return clientScript.getAction();
    }
    
    public void nextAction () {
        clientScript.nextAction();
    }
    
    private static UserActionsDataControl getInstance () {
        BindingContext bc = BindingContext.getCurrent();
        Object adapterObject = bc.get("UserActionsDataControl");
        if (adapterObject != null && adapterObject instanceof AdapterDCService) {
            AdapterDCService adapter = (AdapterDCService)adapterObject;
            Object dcObject = adapter.getDataProvider();
            if (dcObject != null && dcObject instanceof UserActionsDataControl) {
                UserActionsDataControl dc = (UserActionsDataControl)dcObject;
                return dc;
            }
        }
        return null;
    }
    
    public static void invokeAction () {
        UserActionsDataControl dc = UserActionsDataControl.getInstance();
        if (dc != null) {
            ClientAction currentAction = dc.getAction();
            if (currentAction != null) {
                logger.fine ("setting action: " + currentAction);
                currentAction.invoke();
            }
        }
    }
    
    public ClientScript getClientScript() {
        return clientScript;
    }
    
    public static void processEvent (FacesEvent event) {
        UserActionsDataControl dc = UserActionsDataControl.getInstance();
        if (dc != null) {
            ClientAction currentAction = dc.getAction();
            if (currentAction != null) {
                if (currentAction.eventMatches (event)) {
                    dc.nextAction();
                };
            }
        }
    }
    
    public static void processError (ClientEvent event) {
        UserActionsDataControl dc = UserActionsDataControl.getInstance();
        if (dc != null) {
            Map<String, Object> parameters = event.getParameters();
            String action = (String)parameters.get("type");
            String componentId = (String)parameters.get("component");
            String reason = (String)parameters.get("reason");
            logger.warning ("Warning: " + action + " on " + componentId + " skipped. Reason: " + reason);
            dc.nextAction();
        }
    }    
}
