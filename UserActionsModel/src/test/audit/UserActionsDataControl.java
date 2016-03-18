package test.audit;

import oracle.adf.model.BindingContext;
import oracle.adf.model.adapter.AdapterDCService;

import test.audit.client.ClientAction;
import test.audit.client.ClientScript;

public class UserActionsDataControl {
    private ClientScript clientScript = new ClientScript();

    public void loadClientScript(ClientAction[] actions) {
        clientScript.loadScript(actions);
    }

    public ClientAction getNextAction() {
        return clientScript.getNextAction();
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
    
    public static void invokeNextAction () {
        UserActionsDataControl dc = UserActionsDataControl.getInstance();
        if (dc != null) {
            ClientAction nextAction = dc.getNextAction();
            if (nextAction != null) {
                nextAction.invoke();
            }
        }
    }
}
