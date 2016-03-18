package test.audit.client;

public class ValueChangeAction extends ClientAction {
    
    private String compId;
    private Object value;
    
    public ValueChangeAction (String compId, Object value) {
        super();
        this.compId = compId;
        this.value = value;
    }

    @Override
    protected String getActionJS() {
        return "enterValue ('" + compId + "', '" + value + "');";
    }
}
