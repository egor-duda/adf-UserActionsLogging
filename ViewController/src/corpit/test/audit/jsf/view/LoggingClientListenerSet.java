package corpit.test.audit.jsf.view;

import java.util.logging.Level;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.ClientListenerSet;
import oracle.adf.view.rich.render.ClientEvent;

public class LoggingClientListenerSet extends ClientListenerSet {
    
    private static final ADFLogger logger = ADFLogger.createADFLogger (LoggingClientListenerSet.class);

    @SuppressWarnings("compatibility:-3436471002588019332")
    private static final long serialVersionUID = -1028545489124449652L;
    
    private ClientListenerSet cls;
    
    private static final Level loggingLevel = Level.FINE;

    public LoggingClientListenerSet (ClientListenerSet cls) {
        this.cls = cls;
        for (String listenerType: cls.getListenerTypes()) {
            for (String functionName: cls.getListeners(listenerType)) {
                super.addListener(listenerType, functionName);
            }
        }
    }

    public void addBehavior (String behavior) {
        super.addBehavior (behavior);
    }

    public void addBehavior (ValueExpression behavior) {
        super.addBehavior (behavior);
    }

    public void addCustomServerListener (String type, MethodExpression expression) {
        cls.addCustomServerListener (type, expression);
    }

    public void addFeatureDependency (String featureName) {
        super.addFeatureDependency (featureName);
    }

    public void addListener (String type, String functionName) {
        super.addListener(type, functionName);
    }

    public Iterable<String> getFeatureDependencies () {
        return super.getFeatureDependencies();
    }

    public void invokeCustomEventListeners (FacesContext facesContext, ClientEvent clientEvent) {
        logger.log (loggingLevel, "clientEvent " + clientEvent.getType() + " for " + clientEvent.getComponent().getClientId());
        cls.invokeCustomEventListeners(facesContext, clientEvent);
    }

    public static boolean isLoggingEnabled() {
        return logger.isLoggable(loggingLevel);
    }
}
