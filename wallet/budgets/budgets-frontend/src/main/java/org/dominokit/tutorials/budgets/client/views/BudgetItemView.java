package org.dominokit.tutorials.budgets.client.views;

import org.dominokit.domino.api.client.mvp.view.ContentView;
import org.dominokit.domino.api.client.mvp.view.HasUiHandlers;
import org.dominokit.domino.api.client.mvp.view.UiHandlers;
import org.dominokit.tutorials.budgets.shared.model.Budget;
import org.dominokit.tutorials.income.shared.model.Income;

import java.util.List;

public interface BudgetItemView extends ContentView, HasUiHandlers<BudgetItemView.BudgetItemViewUiHandlers> {

    void edit(Budget budget);
    Budget save();
    boolean isValid();

    void setIncomes(List<Income> incomes);

    void setReadonly(boolean readonly);

    void validateBudgetAmount(double totalBudgets);

    interface BudgetItemViewUiHandlers extends UiHandlers{

        void onClose();

        void onEdit();

        void onSave();

        void onIncomeSelected(Income value);
    }
}
