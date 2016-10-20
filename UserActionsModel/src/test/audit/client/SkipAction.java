package test.audit.client;

import javax.faces.event.FacesEvent;

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

    @Override
    public boolean eventMatches(FacesEvent event) {
        return true;
    }
}
