package org.dominokit.tutorials.budgets.client.views;

import org.dominokit.domino.api.client.mvp.view.ContentView;
import org.dominokit.domino.api.client.mvp.view.HasUiHandlers;
import org.dominokit.domino.api.client.mvp.view.UiHandlers;
import org.dominokit.tutorials.budgets.shared.model.Budget;

import java.util.List;

public interface BudgetsView extends ContentView, HasUiHandlers<BudgetsView.BudgetsViewUiHandlers> {

    void setBudgets(List<Budget> budgets);

    interface BudgetsViewUiHandlers extends UiHandlers {

        void onBudgetSelected(Budget budget);

        void onNewBudgetRequest();

    }
}