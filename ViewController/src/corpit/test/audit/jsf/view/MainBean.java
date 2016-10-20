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
import test.audit.client.DialogAction;
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
        if (clientEvent == null) return;
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
        final String MAIN_CONTEXT = "/main-taskflow/main";
        final String DIALOG_CONTEXT = "/dialog-taskflow/dialog";
        ClientAction[] test1 = new ClientAction [] {
            new CommandAction (MAIN_CONTEXT, "next_btn")
            , new CommandAction (MAIN_CONTEXT, "first_btn")
            , new ValueChangeAction (MAIN_CONTEXT, "dept_name_it", "dummy")
            , new ValueChangeAction (MAIN_CONTEXT, "loc_id_itcblov", 1200)
            , new CommandAction (MAIN_CONTEXT, "dialog_btn")
            , new CommandAction (DIALOG_CONTEXT, "exit_btn")
            , new ValueChangeAction (MAIN_CONTEXT, "dept_name_it", "not dummy")
            , new CommandAction (MAIN_CONTEXT, "next_btn")
            , new CommandAction (MAIN_CONTEXT, "first_btn")
        };
        ClientAction[] test2 = new ClientAction[] {
            new CommandAction (MAIN_CONTEXT, "popup_btn")
            , new DialogAction (MAIN_CONTEXT, "main_dlg", "OUTCOME_CANCEL")
        };
        return test1;
    }
}
