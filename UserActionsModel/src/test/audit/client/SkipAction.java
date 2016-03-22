package test.audit.client;

public class SkipAction extends ClientAction {
    
    public SkipAction (String context) {
        super(context);
    }

    @Override
    protected String getActionJS() {
        return ";";
    }

    public String toString() {
        return "SkipAction";
    }
}
