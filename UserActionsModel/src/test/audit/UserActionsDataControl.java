package test.audit;

import oracle.adf.model.BindingContext;
import oracle.adf.model.adapter.AdapterDCService;

import oracle.adf.share.logging.ADFLogger;

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
    
    private void nextAction () {
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
    
    public static void replayDone () {
        UserActionsDataControl dc = UserActionsDataControl.getInstance();
        dc.nextAction();
    }
    
    public static void invokeAction () {
        UserActionsDataControl dc = UserActionsDataControl.getInstance();
        if (dc != null) {
            ClientAction currentAction = dc.getAction();
            if (currentAction != null) {
                logger.finest ("setting action: " + currentAction);
                currentAction.invoke();
            }
        }
    }
}
