package corpit.test.audit.jsf.replay;

import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import test.audit.UserActionsDataControl;

public class ReplayValueChangeListener implements ValueChangeListener {
    
    private final ValueChangeListener delegate;
    
    public ReplayValueChangeListener() {
        super();
        this.delegate = null;
    }

    public ReplayValueChangeListener(ValueChangeListener delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    public void processValueChange(ValueChangeEvent valueChangeEvent) {
        UserActionsDataControl.processEvent (valueChangeEvent);
        if (delegate != null) delegate.processValueChange(valueChangeEvent);
    }
}
