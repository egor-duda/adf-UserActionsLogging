package corpit.test.audit.jsf.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Iterator;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import javax.faces.event.ValueChangeListener;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.UIXInputPopup;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.ClientListenerSet;
import oracle.adf.view.rich.event.DialogListener;
import oracle.adf.view.rich.event.LaunchPopupListener;
import oracle.adf.view.rich.event.PopupCanceledListener;
import oracle.adf.view.rich.event.ReturnPopupListener;

public class GlobalLoggingListenerInjector implements SystemEventListener {
    
    private static ADFLogger logger = ADFLogger.createADFLogger(GlobalLoggingListenerInjector.class);
    static private LoggingValueChangeListener globalLoggingValueChangeListener = new LoggingValueChangeListener ();
    static private LoggingDialogListener globalLoggingDialogListener = new LoggingDialogListener ();
    static private LoggingPopupCancelledListener globalLoggingPopupCancelledListener = new LoggingPopupCancelledListener ();
    static private LoggingLaunchPopupListener globalLoggingLaunchPopupListener = new LoggingLaunchPopupListener ();
    static private LoggingReturnPopupListener globalLoggingReturnPopupListener = new LoggingReturnPopupListener ();
    
    public GlobalLoggingListenerInjector() {
        super();
    }

    private void recursiveAddGlobalListeners (UIComponent comp) {
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
                logger.finest ("attached valueChangeListener to " + comp.getClientId());
            }
        }
        if (comp instanceof RichDialog) {
            RichDialog dialogComponent = (RichDialog)comp;
            boolean alreadyInjected = false;
            for (DialogListener dialogListener: dialogComponent.getDialogListeners()) {
                if (dialogListener instanceof LoggingDialogListener) {
                    alreadyInjected = true;
                    break;
                }
            }
            if (!alreadyInjected) {
                dialogComponent.addDialogListener (globalLoggingDialogListener);
                logger.finest ("attached dialogListener to " + comp.getClientId());
            }
        }
        if (comp instanceof RichPopup) {
            RichPopup popupComponent = (RichPopup)comp;
            boolean alreadyInjected = false;
            for (PopupCanceledListener popupCancelledListener: popupComponent.getPopupCanceledListeners()) {
                if (popupCancelledListener instanceof LoggingPopupCancelledListener) {
                    alreadyInjected = true;
                    break;
                }
            }
            if (!alreadyInjected) {
                popupComponent.addPopupCanceledListener(globalLoggingPopupCancelledListener);
                logger.finest ("attached popupCancelledListener to " + comp.getClientId());
            }
        }
        if (comp instanceof UIXInputPopup) {
            UIXInputPopup popupComponent = (UIXInputPopup)comp;
            boolean launchPopupListenerAlreadyInjected = false;
            for (LaunchPopupListener launchPopupListener: popupComponent.getLaunchPopupListeners()) {
                if (launchPopupListener instanceof LoggingLaunchPopupListener) {
                    launchPopupListenerAlreadyInjected = true;
                    break;
                }
            }
            if (!launchPopupListenerAlreadyInjected) {
                popupComponent.addLaunchPopupListener(globalLoggingLaunchPopupListener);
                logger.finest ("attached launchPopupListener to " + comp.getClientId());
            }
            
            boolean returnPopupListenerAlreadyInjected = false;
            for (ReturnPopupListener returnPopupListener: popupComponent.getReturnPopupListeners()) {
                if (returnPopupListener instanceof LoggingReturnPopupListener) {
                    returnPopupListenerAlreadyInjected = true;
                    break;
                }
            }
            if (!returnPopupListenerAlreadyInjected) {
                popupComponent.addReturnPopupListener(globalLoggingReturnPopupListener);
                logger.finest ("attached returnPopupListener to " + comp.getClientId());
            }
        }
        Iterator<UIComponent> children = comp.getFacetsAndChildren();
        while (children.hasNext()) {
            recursiveAddGlobalListeners (children.next());
        }
        return;
    }
    
    private void recursiveAddGlobalClientListener (UIComponent comp) {
        if (comp == null) return;
        try {
            Method getClientListenersMethod = comp.getClass().getMethod("getClientListeners");
            Method setClientListenersMethod = comp.getClass().getMethod("setClientListeners", ClientListenerSet.class);
            ClientListenerSet originalClientListenerSet = (ClientListenerSet) getClientListenersMethod.invoke (comp);
            if (originalClientListenerSet != null && !(originalClientListenerSet instanceof LoggingClientListenerSet)) {
                LoggingClientListenerSet newClientListenerSet = new LoggingClientListenerSet (originalClientListenerSet);
                setClientListenersMethod.invoke(comp, newClientListenerSet);
                logger.finest ("attached clientListener to " + comp.getClientId());
            }
        } catch (NoSuchMethodException e1) {
            ; // no client listeners mechanism for this component, skip
        } catch (InvocationTargetException e2) {
            logger.severe ("InvocationTargetException trying to modify client listeners for class " + comp.getClass().getName() , e2);
        } catch (IllegalAccessException e3) {
            logger.severe ("IllegalAccessException trying to modify client listeners for class " + comp.getClass().getName() , e3);                
        }
        Iterator<UIComponent> children = comp.getFacetsAndChildren();
        while (children.hasNext()) {
            recursiveAddGlobalClientListener(children.next());
        }
        return;
    }


    @Override
    public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {
        recursiveAddGlobalListeners ((UIViewRoot)systemEvent.getSource());
        recursiveAddGlobalClientListener ((UIViewRoot)systemEvent.getSource());
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return (source instanceof UIViewRoot ? true : false);
    }
}
