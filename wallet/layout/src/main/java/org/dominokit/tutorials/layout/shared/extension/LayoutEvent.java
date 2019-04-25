package org.dominokit.tutorials.layout.shared.extension;

import org.dominokit.domino.api.shared.extension.ActivationEvent;
import org.dominokit.domino.api.shared.extension.ActivationEventContext;

public class LayoutEvent extends ActivationEvent {
    public LayoutEvent(Boolean status) {
        super(status);
    }
}
