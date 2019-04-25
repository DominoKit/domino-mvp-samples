package org.dominokit.tutorials.income.shared.extension;

import org.dominokit.domino.api.shared.extension.EventContext;

public class EditIncomeEventContext implements EventContext {
    private final boolean editable;

    public EditIncomeEventContext(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }
}
