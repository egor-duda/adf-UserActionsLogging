package corpit.test.audit.jsf.logging;

import corpit.test.audit.jsf.InjectedListener;

import java.util.logging.Level;

import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import oracle.adf.share.logging.ADFLogger;

public class LoggingValueChangeListener implements ValueChangeListener, InjectedListener {
    
    private static ADFLogger logger = ADFLogger.createADFLogger(LoggingValueChangeListener.class);
    
    private static final Level loggingLevel = Level.FINER;
    
    private final ValueChangeListener delegate;
    
    public LoggingValueChangeListener() {
        super();
        this.delegate = null;
    }

    public LoggingValueChangeListener(ValueChangeListener delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    public void processValueChange(ValueChangeEvent valueChangeEvent) throws AbortProcessingException {
        StringBuffer retVal = new StringBuffer ();
        UIComponent component = valueChangeEvent.getComponent();
        Object oldValue = valueChangeEvent.getOldValue();
        Object newValue = valueChangeEvent.getNewValue();
        retVal.append ("ValueChangeEvent on ");
        retVal.append (component == null ? "*null*" : component.getId());
        retVal.append (": ");
        
        retVal.append (oldValue == null ? "null" : oldValue.toString());
        retVal.append (" -> ");
        retVal.append (newValue == null ? "null" : newValue.toString());
        
        logger.log (loggingLevel, retVal.toString());
        if (delegate != null) delegate.processValueChange(valueChangeEvent);
    }

    @Override
    public boolean isListenerActive() {
        return logger.isLoggable (loggingLevel)  || (delegate instanceof InjectedListener && ((InjectedListener)delegate).isListenerActive());
    }
}
