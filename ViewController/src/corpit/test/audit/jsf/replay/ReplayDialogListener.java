package corpit.test.audit.jsf.replay;

import javax.faces.event.AbortProcessingException;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogListener;

import test.audit.UserActionsDataControl;

public class ReplayDialogListener implements DialogListener {
    
    private final DialogListener delegate;
    
    public ReplayDialogListener () {
        super();
        this.delegate = null;
    }

    public ReplayDialogListener (DialogListener delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    public void processDialog (DialogEvent dialogEvent) throws AbortProcessingException {
        UserActionsDataControl.processEvent (dialogEvent);
        if (delegate != null) delegate.processDialog(dialogEvent);
    }
}
