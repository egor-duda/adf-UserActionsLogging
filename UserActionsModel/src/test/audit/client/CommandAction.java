package test.audit.client;

import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;

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

    @Override
    public boolean eventMatches(FacesEvent event) {
        if (!(event instanceof ActionEvent)) return false;
        ActionEvent actionEvent = (ActionEvent)event;
        return actionEvent.getComponent().getClientId().equals (compId);
    }
}
