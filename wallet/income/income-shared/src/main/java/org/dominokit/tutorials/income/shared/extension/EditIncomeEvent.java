package org.dominokit.tutorials.income.shared.extension;

import org.dominokit.domino.api.shared.extension.DominoEvent;

public class EditIncomeEvent implements DominoEvent<EditIncomeEventContext> {
    private final EditIncomeEventContext context;

    public EditIncomeEvent(boolean editable) {
        this.context = new EditIncomeEventContext(editable);
    }

    @Override
    public EditIncomeEventContext context() {
        return this.context;
    }
}
