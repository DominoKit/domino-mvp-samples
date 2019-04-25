package org.dominokit.tutorials.budgets.client.presenters;

import org.dominokit.domino.api.client.annotations.Aggregate;
import org.dominokit.domino.api.client.annotations.presenter.*;
import org.dominokit.domino.api.client.mvp.presenter.ViewBaseClientPresenter;
import org.dominokit.tutorials.budgets.client.views.BudgetItemView;
import org.dominokit.tutorials.budgets.shared.model.Budget;
import org.dominokit.tutorials.budgets.shared.services.BudgetsServiceFactory;
import org.dominokit.tutorials.income.shared.model.Income;
import org.dominokit.tutorials.income.shared.services.IncomeServiceFactory;
import org.dominokit.tutorials.layout.shared.extension.LayoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Objects.isNull;

public abstract class BudgetItemProxy extends ViewBaseClientPresenter<BudgetItemView> implements BudgetItemView.BudgetItemViewUiHandlers {

    public static final Logger LOGGER = LoggerFactory.getLogger(BudgetItemProxy.class);

    BudgetIncomesAggregate budgetIncomesAggregate;

    @PresenterProxy
    @AutoRoute(token = "new-budget")
    @Slot("content")
    @AutoReveal
    @DependsOn(@EventsGroup(LayoutEvent.class))
    public static class NewBudgetProxy extends BudgetItemProxy {

        @OnReveal
        public void editNewBudget() {
            budgetIncomesAggregate.completeBudget(new Budget());
        }

        @Override
        public void onSave() {
            if (view.isValid()) {
                Budget budget = view.save();
                if (isNull(budget.getRemaining())) {
                    budget.setRemaining(budget.getAmount());
                    budget.setSpent(BigDecimal.ZERO);
                }
            }
            super.onSave();
        }
    }

    @PresenterProxy
    @AutoRoute(token = "budgets/:name")
    @Slot("content")
    @AutoReveal
    @DependsOn(@EventsGroup(LayoutEvent.class))
    public static class BudgetDetailsProxy extends BudgetItemProxy {

        @PathParameter
        String name;

        @OnReveal
        public void loadBudget() {
            BudgetsServiceFactory.INSTANCE
                    .getBudgetByName(name)
                    .onSuccess(budget -> {
                        view.setReadonly(true);
                        budgetIncomesAggregate.completeBudget(budget);
                    })
                    .onFailed(failedResponse -> LOGGER.error("Failed to load budget", failedResponse.getThrowable()))
                    .send();
        }
    }

    @OnInit
    public void loadIncomes() {
        IncomeServiceFactory.INSTANCE
                .listAll()
                .onSuccess(response -> budgetIncomesAggregate.completeIncomes(response.asList()))
                .onFailed(failedResponse -> LOGGER.error("Failed to load incomes", failedResponse.getThrowable()))
                .send();
    }

    @Aggregate(name = "BudgetIncomesAggregate")
    public void onBudgetAndIncomesLoaded(Budget budget, List<Income> incomes) {
        view.setIncomes(incomes);
        view.edit(budget);
    }

    @Override
    public void onClose() {
        history().back();
    }

    @Override
    public void onEdit() {
        view.setReadonly(false);
    }

    @Override
    public void onSave() {

        if (view.isValid()) {
            Budget budget = view.save();
            BudgetsServiceFactory.INSTANCE
                    .save(budget)
                    .onSuccess(response -> {
                        view.setReadonly(true);
                        view.edit(budget);
                    })
                    .onFailed(failedResponse -> LOGGER.error("Failed to save budget.", failedResponse.getThrowable()))
                    .send();
        }
    }

    @Override
    public void onIncomeSelected(Income income) {
        BudgetsServiceFactory.INSTANCE
                .getBudgetsByIncome(income.getTitle())
                .onSuccess(budgets -> {
                            double totalBudgets = budgets.asList()
                                    .stream()
                                    .mapToDouble(budget -> budget.getAmount().doubleValue())
                                    .sum();
                            view.validateBudgetAmount(totalBudgets);
                        }
                )
                .send();
    }
}
