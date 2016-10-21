package corpit.test.audit.jsf.replay;

import corpit.test.audit.jsf.InjectedListener;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import test.audit.UserActionsDataControl;

public class ReplayActionListener implements ActionListener, InjectedListener {
    
    private final ActionListener delegate;
    
    public ReplayActionListener() {
        super();
        this.delegate = null;
    }
        
    public ReplayActionListener (ActionListener delegate) {
        this.delegate = delegate;    
    }
    
    @Override
    public void processAction(ActionEvent actionEvent) {
        UserActionsDataControl.processEvent (actionEvent);
        if (delegate != null) delegate.processAction(actionEvent);
    }

    @Override
    public boolean isListenerActive() {
        return true || (delegate instanceof InjectedListener && ((InjectedListener)delegate).isListenerActive());
    }
}
