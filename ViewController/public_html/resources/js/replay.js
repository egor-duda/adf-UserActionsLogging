var currentCommand = {type: null, component: null, value: null};
var executorRunning = false;

function runCommand (compId) {
    if (!AdfPage.PAGE.isSynchronizedWithServer()) {
        // console.log("waiting for page to render");
        executorRunning = true;
        setTimeout(function() {runCommand(compId);}, 100);
    } else {
        // console.log ('command: ' + currentCommand.type);
        executorRunning = false;
        if (currentCommand.type == 'ValueChange') {
            doValueChange();
        } else if (currentCommand.type == 'Action') {
            doAction();
        }
        if (currentCommand.type != null) {
            var comp = AdfPage.PAGE.findComponentByAbsoluteId ("d1");
            // console.log (comp);
            AdfCustomEvent.queue(comp,"replayDone",{},false);
        }        
    }
}

function doValueChange () {
    try {
        var comp = AdfPage.PAGE.findComponentByAbsoluteId (currentCommand.component);
        comp.setValue (currentCommand.value);
        AdfValueChangeEvent.queue (comp, comp.getValue(), currentCommand.value, true);
        AdfCustomEvent.queue(AdfPage.PAGE.findComponent("d1"),"replayDone",{},false);
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