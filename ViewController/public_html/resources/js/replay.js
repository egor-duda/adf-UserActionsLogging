var currentCommand = {type: null, component: null, value: null};
var executorRunning = false;
var enableCallBack = false;

function runCommand (compId) {
    if (!AdfPage.PAGE.isSynchronizedWithServer()) {
        executorRunning = true;
        setTimeout(function() {runCommand(compId);}, 100);
    } else {
        executorRunning = false;
        if (currentCommand.type == 'ValueChange') {
            doValueChange();
        } else if (currentCommand.type == 'Action') {
            doAction();
        } else if (currentCommand.type == 'Dialog') {
            doDialog();
        }
        if (enableCallBack && currentCommand.type != null) {
            var comp = AdfPage.PAGE.findComponentByAbsoluteId ("d1");
            if (comp != null) AdfCustomEvent.queue(comp,"replayDone",{},false);
        }        
    }
}

function doValueChange () {
    try {
        var comp = AdfPage.PAGE.findComponentByAbsoluteId (currentCommand.component);
        comp.setValue (currentCommand.value);
        AdfValueChangeEvent.queue (comp, comp.getValue(), currentCommand.value, true);
    } catch (e) {
        alert (e);
    }
}

function enterValue (compId, value) {
    currentCommand.type = 'ValueChange';
    currentCommand.component = compId;
    currentCommand.value = value;
    if (!executorRunning) { runCommand (compId); }
}

function doAction () {
    try {
        var comp = AdfPage.PAGE.findComponentByAbsoluteId (currentCommand.component);
        AdfActionEvent.queue(comp, comp.getPartialSubmit());
    } catch (e) {
        alert (e);
    }    
}

function pressButton (compId) {
    currentCommand.type = 'Action';
    currentCommand.component = compId;
    currentCommand.value = null;
    if (!executorRunning) { runCommand (compId); }
}

function doDialog () {
    try {
        var comp = AdfPage.PAGE.findComponentByAbsoluteId (currentCommand.component);
        var event = new AdfDialogEvent (comp, currentCommand.value);
        event.queue (true);
    } catch (e) {
        alert (e);
    }
}

function processDialog (compId, value) {
    currentCommand.type = 'Dialog';
    currentCommand.component = compId;
    currentCommand.value = value;
    if (!executorRunning) { runCommand (compId); }
}
