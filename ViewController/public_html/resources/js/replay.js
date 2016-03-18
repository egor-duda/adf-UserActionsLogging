function enterValue (fieldId, value) {
    try {
        var comp = AdfPage.PAGE.findComponentByAbsoluteId (fieldId);
        comp.setValue (value);        
        AdfValueChangeEvent.queue (comp, comp.getValue(), value, true);
    } catch (e) {
        alert (e);
    }
}

function pressButton (buttonId) {
    try {
        var comp = AdfPage.PAGE.findComponentByAbsoluteId (buttonId);
        AdfActionEvent.queue(comp, comp.getPartialSubmit());
    } catch (e) {
        alert (e);
    }    
}