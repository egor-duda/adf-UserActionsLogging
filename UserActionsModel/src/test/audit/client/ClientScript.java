package test.audit.client;

public class ClientScript {
    
    private int counter;
    
    private ClientAction[] actionsQueue = new ClientAction[0];

    public ClientAction getNextAction() {
        if (counter >= actionsQueue.length) return null;
        return actionsQueue [counter++];
    }
    
    public void loadScript (ClientAction[] actions) {
        this.actionsQueue = actions;
        counter = 0;
    }
}
