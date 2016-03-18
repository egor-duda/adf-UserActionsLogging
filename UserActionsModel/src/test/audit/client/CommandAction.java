package test.audit.client;

public class CommandAction extends ClientAction {
    
    private String compId;

    public CommandAction (String compId) {
        super();
        this.compId = compId;
    }

    @Override
    protected String getActionJS() {
        return "pressButton ('" + compId + "');";
    }
}
