package test.audit.client;

public class SkipAction extends ClientAction {
    
    public SkipAction () {
        super();
    }

    @Override
    protected String getActionJS() {
        return ";";
    }
}
