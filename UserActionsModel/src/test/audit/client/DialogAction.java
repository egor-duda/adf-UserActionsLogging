package test.audit.client;

import javax.faces.event.FacesEvent;

import oracle.adf.view.rich.event.DialogEvent;

public class DialogAction extends ClientAction {
    
    private String compId;
    private String outcome;
    
    public DialogAction(String context, String compId, String outcome) {
        super(context);
        this.compId = compId;
        this.outcome = outcome;
    }

    @Override
    protected String getActionJS() {
        return " processDialog ('" + compId + "', AdfDialogEvent." + outcome + ");";
    }
    
    @Override
    public String toString() {
        return "DialogAction[" + compId + "]=" + outcome;
    }

    @Override
    public boolean eventMatches(FacesEvent event) {
        if (!(event instanceof DialogEvent)) return false;
        DialogEvent dialogEvent = (DialogEvent)event;
        if (!dialogEvent.getComponent().getClientId().equals(compId)) return false;
        return outcome.equals(dialogEvent.getOutcome().toString());
    }
}
