package org.dominokit.tutorials.income.client.views;

import org.dominokit.domino.api.client.mvp.view.ContentView;
import org.dominokit.domino.api.client.mvp.view.HasUiHandlers;
import org.dominokit.domino.api.client.mvp.view.UiHandlers;
import org.dominokit.tutorials.income.shared.model.Income;

public interface IncomeItemView extends ContentView, HasUiHandlers<IncomeItemView.NewIncomeUiHandlers> {
    void edit(Income income);
    Income save();

    boolean isValid();

    void setReadOnly(boolean readonly);

    interface NewIncomeUiHandlers extends UiHandlers {
        void onSaveIncome();
        void onCancel();
        void onEditIncome();
    }
}
