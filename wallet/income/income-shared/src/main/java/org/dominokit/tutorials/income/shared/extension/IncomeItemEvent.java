package org.dominokit.tutorials.income.shared.extension;

import org.dominokit.domino.api.shared.extension.ActivationEvent;

public class IncomeItemEvent extends ActivationEvent {

    public IncomeItemEvent(boolean status) {
        super(status);
    }
}
