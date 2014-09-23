package corpit.test.audit.jsf.view;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.logging.ADFLogger;

public class MainBean {
    
    private static ADFLogger logger = ADFLogger.createADFLogger(MainBean.class);
    
    public MainBean() {
    }

    public void handleDepartmentNameChange(ValueChangeEvent valueChangeEvent) {
        logger.fine ("Department name changed to " + valueChangeEvent.getNewValue());
    }
}
