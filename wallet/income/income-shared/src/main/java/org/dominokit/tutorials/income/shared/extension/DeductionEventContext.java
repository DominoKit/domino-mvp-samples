package org.dominokit.tutorials.income.shared.extension;

import org.dominokit.domino.api.shared.extension.EventContext;
import org.dominokit.tutorials.income.shared.model.Deduction;

public class DeductionEventContext implements EventContext {
    private final Deduction deduction;

    public DeductionEventContext(Deduction deduction) {
        this.deduction = deduction;
    }

    public Deduction getDeduction() {
        return deduction;
    }
}
