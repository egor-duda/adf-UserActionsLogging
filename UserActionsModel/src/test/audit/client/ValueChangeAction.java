package test.audit.client;

import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;

public class ValueChangeAction extends ClientAction {
    
    private String compId;
    private Object value;
    
    public ValueChangeAction (String context, String compId, Object value) {
        super(context);
        this.compId = compId;
        this.value = value;
    }

    @Override
    protected String getActionJS() {
        return "enterValue ('" + compId + "', '" + value + "');";
    }


    public String toString() {
        return "ValueChangeAction[" + compId + "]=" + value;
    }

    @Override
    public boolean eventMatches(FacesEvent event) {
        if (!(event instanceof ValueChangeEvent)) return false;
        ValueChangeEvent valueChangeEvent = (ValueChangeEvent)event;
        if (!valueChangeEvent.getComponent().getClientId().equals(compId)) return false;
        Object newValue = valueChangeEvent.getNewValue();
        if (newValue == null) {
            return value == null;
        } else {
            return newValue.equals(value);
        }
    }
}
