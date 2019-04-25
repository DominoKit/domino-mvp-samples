package org.dominokit.tutorials.income.shared.extension;

import org.dominokit.domino.api.shared.extension.ActivationEvent;

public class IncomeEvent extends ActivationEvent {
    public IncomeEvent(boolean state) {
        super(state);
    }
}
