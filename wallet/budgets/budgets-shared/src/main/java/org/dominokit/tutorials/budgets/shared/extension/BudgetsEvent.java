package org.dominokit.tutorials.budgets.shared.extension;

import org.dominokit.domino.api.shared.extension.ActivationEvent;

public class BudgetsEvent extends ActivationEvent {
    public BudgetsEvent(boolean state) {
        super(state);
    }
}
