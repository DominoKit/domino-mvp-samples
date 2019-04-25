package org.dominokit.tutorials.expenses.shared.extension;

import org.dominokit.domino.api.shared.extension.ActivationEvent;

public class ExpensesEvent extends ActivationEvent {
    public ExpensesEvent(boolean state) {
        super(state);
    }
}
