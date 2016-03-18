package corpit.test.audit.jsf.view;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import test.audit.UserActionsDataControl;

public class ReplayUserInputPhaseListener implements PhaseListener {
    @SuppressWarnings("compatibility:-7409327685748085366")
    private static final long serialVersionUID = 1L;

    public ReplayUserInputPhaseListener() {
        super();
    }

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
        UserActionsDataControl.invokeNextAction();
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
