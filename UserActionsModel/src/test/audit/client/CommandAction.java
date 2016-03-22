package test.audit.client;

public class CommandAction extends ClientAction {
    
    private String compId;

    public CommandAction (String context, String compId) {
        super(context);
        this.compId = compId;
    }

    @Override
    protected String getActionJS() {
        return "pressButton ('" + compId + "');";
    }

    public String toString() {
        return "CommandAction[" + compId + "]";
    }
}
