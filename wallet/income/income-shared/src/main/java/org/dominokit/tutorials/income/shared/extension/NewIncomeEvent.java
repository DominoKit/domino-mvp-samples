package org.dominokit.tutorials.income.shared.extension;

import org.dominokit.domino.api.shared.extension.ActivationEvent;

public class NewIncomeEvent extends ActivationEvent {

    public NewIncomeEvent(boolean status) {
        super(status);
    }
}
