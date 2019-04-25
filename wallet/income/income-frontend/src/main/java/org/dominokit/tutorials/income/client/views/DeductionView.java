package org.dominokit.tutorials.income.client.views;

import org.dominokit.domino.api.client.mvp.view.ContentView;
import org.dominokit.domino.api.client.mvp.view.HasUiHandlers;
import org.dominokit.domino.api.client.mvp.view.UiHandlers;
import org.dominokit.tutorials.income.shared.model.Deduction;

import java.math.BigDecimal;
import java.util.List;

public interface DeductionView extends ContentView, HasUiHandlers<DeductionView.DeductionsUiHandlers> {

    void onDeductionAdded(Deduction deduction);
    void onDeductionRemoved(Deduction deduction);

    void setDeductions(List<Deduction> deductions);

    void setReadOnly(boolean readOnly);

    interface DeductionsUiHandlers extends UiHandlers {
        void onAddDeduction(String description, BigDecimal amount);
        void onRemoveDeduction(Deduction deduction);
    }
}
