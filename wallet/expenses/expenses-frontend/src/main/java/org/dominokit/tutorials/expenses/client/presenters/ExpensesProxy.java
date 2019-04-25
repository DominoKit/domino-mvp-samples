package org.dominokit.tutorials.expenses.client.presenters;

import org.dominokit.domino.api.client.annotations.Aggregate;
import org.dominokit.domino.api.client.annotations.presenter.*;
import org.dominokit.domino.api.client.mvp.presenter.ViewBaseClientPresenter;
import org.dominokit.tutorials.budgets.shared.model.Budget;
import org.dominokit.tutorials.budgets.shared.services.BudgetsServiceFactory;
import org.dominokit.tutorials.expenses.client.views.ExpensesView;
import org.dominokit.tutorials.expenses.shared.extension.ExpensesEvent;
import org.dominokit.tutorials.expenses.shared.model.Expense;
import org.dominokit.tutorials.expenses.shared.services.ExpensesServiceFactory;
import org.dominokit.tutorials.layout.shared.extension.LayoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@PresenterProxy
@AutoRoute(token = "expenses")
@Slot("content")
@AutoReveal
@OnStateChanged(ExpensesEvent.class)
@DependsOn(@EventsGroup(LayoutEvent.class))
public class ExpensesProxy extends ViewBaseClientPresenter<ExpensesView> implements ExpensesView.ExpensesViewUiHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpensesProxy.class);

    ExpensesBudgetsAggregate expensesBudgetsAggregate;

    @Aggregate(name = "ExpensesBudgetsAggregate")
    public void onBudgetsAndExpensesLoaded(List<Budget> budgets, List<Expense> expenses) {
        view.setExpenses(expenses);
        view.setBudgets(budgets);
    }

    @OnReveal
    public void loadExpenses() {
        ExpensesServiceFactory.INSTANCE
                .listAll()
                .onSuccess(response -> expensesBudgetsAggregate.completeExpenses(response.asList()))
                .send();
    }

    @OnInit
    public void loadBudgets() {
        BudgetsServiceFactory.INSTANCE
                .listAll()
                .onSuccess(response -> expensesBudgetsAggregate.completeBudgets(response.asList()))
                .send();
    }

    @Override
    public void deleteExpense(Expense expense) {
        ExpensesServiceFactory.INSTANCE
                .delete(expense.getId())
                .onSuccess(response -> view.onExpenseDeleted(expense))
                .send();
    }

    @Override
    public void onAddExpense() {
        if (view.isValid()) {
            Expense expense = view.save();
            ExpensesServiceFactory.INSTANCE
                    .save(expense)
                    .onSuccess(response -> view.onExpenseAdded(expense))
                    .send();
        }
    }

    @Override
    public void updateBudgets() {
        BudgetsServiceFactory.INSTANCE
                .listAll()
                .onSuccess(response -> view.setBudgets(response.asList()))
                .send();
    }
}