package org.dominokit.tutorials.income.client.views;

import org.dominokit.domino.api.client.mvp.view.ContentView;
import org.dominokit.domino.api.client.mvp.view.HasUiHandlers;
import org.dominokit.domino.api.client.mvp.view.UiHandlers;
import org.dominokit.tutorials.income.shared.model.Income;

import java.util.List;

public interface IncomeView extends ContentView, HasUiHandlers<IncomeView.IncomeUiHandlers> {
    void setIncomes(List<Income> incomes);

    interface IncomeUiHandlers extends UiHandlers {
        void onNewIncomeRequest();

        void onIncomeSelected(Income income);
    }
}