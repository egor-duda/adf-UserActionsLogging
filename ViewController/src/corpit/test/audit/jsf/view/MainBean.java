package corpit.test.audit.jsf.view;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.event.ReturnEvent;

import test.audit.client.ClientAction;
import test.audit.client.CommandAction;
import test.audit.client.SkipAction;
import test.audit.client.ValueChangeAction;

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

    public void dialogReturnListener(ReturnEvent returnEvent) {
        logger.info (returnEvent.toString());
    }

    public ClientAction[] getReplayScript () {
        return new ClientAction [] {
            new ValueChangeAction ("dept_name_it", "dummy")
            , new ValueChangeAction ("loc_id_itcblov", "1200")
            , new CommandAction ("dialog_btn")
            , new CommandAction ("exit_btn")
            , new SkipAction ()
            , new ValueChangeAction ("dept_name_it", "not dummy")
            , new CommandAction ("next_btn")
            , new CommandAction ("first_btn")
        };
    }
}
