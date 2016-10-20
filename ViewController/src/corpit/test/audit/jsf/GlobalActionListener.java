package corpit.test.audit.jsf;

import corpit.test.audit.jsf.replay.ReplayActionListener;
import corpit.test.audit.jsf.logging.LoggingActionListener;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class GlobalActionListener implements ActionListener {
    
    private final ActionListener delegate;
    
    public GlobalActionListener () {
        super();
        this.delegate = new LoggingActionListener (new ReplayActionListener());    
    }

    public GlobalActionListener (ActionListener delegate) {
        super();
        this.delegate = new LoggingActionListener (new ReplayActionListener(delegate));
    }

    @Override
    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
        if (delegate != null) delegate.processAction (actionEvent);
    }
}
