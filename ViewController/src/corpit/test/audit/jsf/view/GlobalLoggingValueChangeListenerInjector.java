package corpit.test.audit.jsf.view;

import java.util.Iterator;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import javax.faces.event.ValueChangeListener;

import oracle.adf.share.logging.ADFLogger;

public class GlobalLoggingValueChangeListenerInjector implements SystemEventListener {
    
    private static ADFLogger logger = ADFLogger.createADFLogger(GlobalLoggingValueChangeListenerInjector.class);
    static private LoggingValueChangeListener globalLoggingValueChangeListener = new LoggingValueChangeListener ();
    
    public GlobalLoggingValueChangeListenerInjector() {
        super();
    }

    private void recursiveAddGlobalValueChangeListener (UIComponent comp) {
        if (comp == null) return;
        if (comp instanceof EditableValueHolder) {
            EditableValueHolder editableComponent = (EditableValueHolder)comp;
            boolean alreadyInjected = false;
            for (ValueChangeListener valueChangeListener: editableComponent.getValueChangeListeners()) {
                if (valueChangeListener instanceof LoggingValueChangeListener) {
                    alreadyInjected = true;
                    break;
                }
            }
            if (!alreadyInjected) {
                ((EditableValueHolder)comp).addValueChangeListener(globalLoggingValueChangeListener);
                logger.finest ("attached valueChangeListener to " + comp.getId());
            }
            return;
        }
        Iterator<UIComponent> children = comp.getFacetsAndChildren();
        while (children.hasNext()) {
            recursiveAddGlobalValueChangeListener(children.next());
        }
        return;
    }

    @Override
    public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {
        recursiveAddGlobalValueChangeListener ((UIViewRoot)systemEvent.getSource());
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return (source instanceof UIViewRoot ? true : false);
    }
}
