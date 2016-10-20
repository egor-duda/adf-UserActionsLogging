package test.audit.client;

public class ClientScript {
    
    private int counter;
    
    private ClientAction[] actionsQueue = new ClientAction[0];

    public ClientAction getAction () {
        if (counter >= actionsQueue.length) return null;
        ClientAction retVal = actionsQueue [counter];
        if (retVal.isMatchingContext()) {
            return retVal;
        } else {
            return null;
        }
    }
    
    public void nextAction () {
        counter ++;
    }
    
    public void loadScript (ClientAction[] actions) {
        this.actionsQueue = actions;
        counter = 0;
    }

    public ClientAction[] getActionsQueue() {
        return actionsQueue;
    }
}
