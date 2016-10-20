function Command (type, component, value) {
    this.type = type;
    this.component = component;
    this.value = value;
}

var commandWait = 0;

function runCommand (command) {
    if (!AdfPage.PAGE.isSynchronizedWithServer()) {
        commandWait = 1;
        setTimeout(function() {runCommand(command);}, 1000);
    } else {
        if (command.type == 'ValueChange') {
            doValueChange(command);
        } else if (command.type == 'Action') {
            doAction(command);
        } else if (command.type == 'Dialog') {
            doDialog(command);
        }
    }
}

function doValueChange (command) {
    try {
        var comp = AdfPage.PAGE.findComponentByAbsoluteId (command.component);
        comp.setValue (command.value);
        AdfValueChangeEvent.queue (comp, comp.getValue(), command.value, true);
        commandWait = 0;
    } catch (e) {
        alert (e);
    }
}

function enterValue (compId, value) {
    if (commandWait != 0) return;
    var command = new Command ('ValueChange', compId, value);
    runCommand (command);
}

function doAction (command) {
    try {
        var comp = AdfPage.PAGE.findComponentByAbsoluteId (command.component);
        AdfActionEvent.queue(comp, comp.getPartialSubmit());
        commandWait = 0;
    } catch (e) {
        alert (e);
    }    
}

function pressButton (compId) {
    if (commandWait != 0) return;
    var command = new Command ('Action', compId, null);
    runCommand (command);
}

function doDialog (command) {
    try {
        var comp = AdfPage.PAGE.findComponentByAbsoluteId (command.component);
        var event = new AdfDialogEvent (comp, command.value);
        event.queue (true);
        commandWait = 0;
    } catch (e) {
        alert (e);
    }
}

function processDialog (compId, value) {
    if (commandWait != 0) return;
    var command = new Command ('Dialog', compId, value);
    runCommand (command);
}
