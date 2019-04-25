package org.dominokit.tutorials.expenses.client.views;

import org.dominokit.domino.api.client.mvp.view.ContentView;
import org.dominokit.domino.api.client.mvp.view.HasUiHandlers;
import org.dominokit.domino.api.client.mvp.view.UiHandlers;
import org.dominokit.tutorials.budgets.shared.model.Budget;
import org.dominokit.tutorials.expenses.shared.model.Expense;

import java.util.List;

public interface ExpensesView extends ContentView, HasUiHandlers<ExpensesView.ExpensesViewUiHandler> {
    void setExpenses(List<Expense> expensesList);

    void setBudgets(List<Budget> budgets);

    Expense save();

    boolean isValid();

    void onExpenseAdded(Expense expense);

    void onExpenseDeleted(Expense expense);

    interface ExpensesViewUiHandler extends UiHandlers{

        void deleteExpense(Expense expense);

        void onAddExpense();

        void updateBudgets();
    }
}