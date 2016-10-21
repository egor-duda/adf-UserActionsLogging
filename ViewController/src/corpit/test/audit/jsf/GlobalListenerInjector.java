package corpit.test.audit.jsf;

import corpit.test.audit.jsf.logging.LoggingClientListenerSet;
import corpit.test.audit.jsf.logging.LoggingDialogListener;
import corpit.test.audit.jsf.logging.LoggingLaunchPopupListener;
import corpit.test.audit.jsf.logging.LoggingPopupCancelledListener;
import corpit.test.audit.jsf.logging.LoggingReturnPopupListener;
import corpit.test.audit.jsf.logging.LoggingValueChangeListener;

import corpit.test.audit.jsf.replay.ReplayDialogListener;
import corpit.test.audit.jsf.replay.ReplayValueChangeListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import javax.faces.event.ValueChangeListener;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.UIXInputPopup;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.event.ClientListenerSet;
import oracle.adf.view.rich.event.DialogListener;
import oracle.adf.view.rich.event.LaunchPopupListener;
import oracle.adf.view.rich.event.PopupCanceledListener;
import oracle.adf.view.rich.event.ReturnPopupListener;
import oracle.adf.view.rich.render.ClientEvent;
import oracle.adf.view.rich.resource.PageResource;

public class GlobalListenerInjector implements SystemEventListener {
    
    private static ADFLogger logger = ADFLogger.createADFLogger(GlobalListenerInjector.class);
    static private LoggingValueChangeListener globalValueChangeListener = new LoggingValueChangeListener (new ReplayValueChangeListener());
    static private LoggingDialogListener globalDialogListener = new LoggingDialogListener (new ReplayDialogListener());
    static private LoggingPopupCancelledListener globalPopupCancelledListener = new LoggingPopupCancelledListener ();
    static private LoggingLaunchPopupListener globalLaunchPopupListener = new LoggingLaunchPopupListener ();
    static private LoggingReturnPopupListener globalReturnPopupListener = new LoggingReturnPopupListener ();
    
    public GlobalListenerInjector() {
        super();
    }

    private void recursiveAddGlobalListeners (UIComponent comp) {
        if (comp == null) return;
        if (comp instanceof EditableValueHolder && globalValueChangeListener.isListenerActive()) {
            EditableValueHolder editableComponent = (EditableValueHolder)comp;
            boolean alreadyInjected = false;
            for (ValueChangeListener valueChangeListener: editableComponent.getValueChangeListeners()) {
                if (valueChangeListener instanceof LoggingValueChangeListener) {
                    alreadyInjected = true;
                    break;
                }
            }
            if (!alreadyInjected) {
                ((EditableValueHolder)comp).addValueChangeListener(globalValueChangeListener);
                logger.finest ("attached valueChangeListener to " + comp.getClientId());
            }
        }
        if (comp instanceof RichDialog && globalDialogListener.isListenerActive()) {
            RichDialog dialogComponent = (RichDialog)comp;
            boolean alreadyInjected = false;
            for (DialogListener dialogListener: dialogComponent.getDialogListeners()) {
                if (dialogListener instanceof LoggingDialogListener) {
                    alreadyInjected = true;
                    break;
                }
            }
            if (!alreadyInjected) {
                dialogComponent.addDialogListener (globalDialogListener);
                logger.finest ("attached dialogListener to " + comp.getClientId());
            }
        }
        if (comp instanceof RichPopup && globalPopupCancelledListener.isListenerActive()) {
            RichPopup popupComponent = (RichPopup)comp;
            boolean alreadyInjected = false;
            for (PopupCanceledListener popupCancelledListener: popupComponent.getPopupCanceledListeners()) {
                if (popupCancelledListener instanceof LoggingPopupCancelledListener) {
                    alreadyInjected = true;
                    break;
                }
            }
            if (!alreadyInjected) {
                popupComponent.addPopupCanceledListener(globalPopupCancelledListener);
                logger.finest ("attached popupCancelledListener to " + comp.getClientId());
            }
        }
        if (comp instanceof UIXInputPopup && 
            ( globalLaunchPopupListener.isListenerActive() || globalReturnPopupListener.isListenerActive())) {
            UIXInputPopup popupComponent = (UIXInputPopup)comp;
            boolean launchPopupListenerAlreadyInjected = false;
            for (LaunchPopupListener launchPopupListener: popupComponent.getLaunchPopupListeners()) {
                if (launchPopupListener instanceof LoggingLaunchPopupListener) {
                    launchPopupListenerAlreadyInjected = true;
                    break;
                }
            }
            if (!launchPopupListenerAlreadyInjected) {
                popupComponent.addLaunchPopupListener(globalLaunchPopupListener);
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
                popupComponent.addReturnPopupListener(globalReturnPopupListener);
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
        if (!LoggingClientListenerSet.isLoggingEnabled()) return;
        if (comp instanceof RichDocument) {
            fixupRootDocument((RichDocument)comp);
        }        
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

    private static final String RESERVED_COMPONENT_ID = "__JSF_AUIDIT_HELPER";
    private static final String REPLAY_JS_PATH = "/resources/js/replay.js";

    private void fixupRootDocument(RichDocument root) {
        RichPopup helper = (RichPopup)findChild (root, RESERVED_COMPONENT_ID);
        if (helper != null) return;
        helper = new RichPopup ();
        helper.setId(RESERVED_COMPONENT_ID);
        ClientListenerSet newClientListenerSet = new ClientListenerSet();
        newClientListenerSet.addCustomServerListener("reportReplayError", getMethodExpression("#{helperBean.handleReplayError}"));
        helper.setClientListeners(newClientListenerSet);
        root.getChildren().add(helper);
        PageResource replayJS = new PageResource(PageResource.ResourceType.JAVASCRIPT, PageResource.ResourceLocation.URI, REPLAY_JS_PATH);
        root.addResource (replayJS);
    }
    
    private UIComponent findChild (UIComponent parent, String childId) {
        UIComponent retVal = null;
        for (UIComponent child: parent.getChildren()) {
            if (childId.equals(child.getId())) {
                retVal = child;
                break;
            }
        }
        return retVal;
    }

    @Override
    public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {
        if (globalDialogListener.isListenerActive()
            || globalLaunchPopupListener.isListenerActive()
            || globalPopupCancelledListener.isListenerActive()
            || globalReturnPopupListener.isListenerActive()
            || globalValueChangeListener.isListenerActive()) {
            recursiveAddGlobalListeners ((UIViewRoot)systemEvent.getSource());
        }
        if (LoggingClientListenerSet.isLoggingEnabled()) {
            recursiveAddGlobalClientListener ((UIViewRoot)systemEvent.getSource());
        }
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return (source instanceof UIViewRoot ? true : false);
    }
    
    private static MethodExpression getMethodExpression(String s) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elctx = fc.getELContext();
        ExpressionFactory elFactory = fc.getApplication().getExpressionFactory();
        MethodExpression methodExpr = elFactory.createMethodExpression(elctx, s, null, new Class<?>[] {
                                                                       ClientEvent.class });
        return methodExpr;

    }    
}
