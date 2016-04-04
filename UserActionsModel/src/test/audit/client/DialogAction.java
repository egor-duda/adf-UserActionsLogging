package test.audit.client;

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
}
