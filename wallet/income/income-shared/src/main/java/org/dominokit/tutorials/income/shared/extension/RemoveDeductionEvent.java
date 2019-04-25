package org.dominokit.tutorials.income.shared.extension;

import org.dominokit.domino.api.shared.extension.DominoEvent;
import org.dominokit.tutorials.income.shared.model.Deduction;

public class RemoveDeductionEvent implements DominoEvent<DeductionEventContext> {

    private final DeductionEventContext context;

    public RemoveDeductionEvent(Deduction deduction) {
        this.context = new DeductionEventContext(deduction);
    }

    @Override
    public DeductionEventContext context() {
        return context;
    }
}
