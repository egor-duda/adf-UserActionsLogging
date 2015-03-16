package corpit.test.audit.jsf.view;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

public class MainBean {
    
    private static ADFLogger logger = ADFLogger.createADFLogger(MainBean.class);
    private RichPopup dialogPopupComponent;

    public MainBean() {
    }

    public void handleDepartmentNameChange(ValueChangeEvent valueChangeEvent) {
        logger.fine ("Department name changed to " + valueChangeEvent.getNewValue());
    }
    
    public void onLoad (ClientEvent clientEvent) {
        clientEvent.hashCode();
    }

    public void callDialog (ActionEvent actionEvent) {
        actionEvent.hashCode();
        dialogPopupComponent.show(new RichPopup.PopupHints());
    }

    public void setDialogPopupComponent (RichPopup dialogPopupComponent) {
        this.dialogPopupComponent = dialogPopupComponent;
    }

    public RichPopup getDialogPopupComponent () {
        return dialogPopupComponent;
    }

    public void dialogListener (DialogEvent dialogEvent) {
        logger.info (dialogEvent.getOutcome().toString());
    }
}
