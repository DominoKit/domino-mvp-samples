package org.dominokit.tutorials.budgets.server;

import com.google.auto.service.AutoService;
import org.dominokit.domino.api.server.entrypoint.ServerAppEntryPoint;
import org.dominokit.domino.api.server.entrypoint.VertxContext;
import org.dominokit.tutorials.budgets.server.resources.BudgetsMapRepository;
import org.dominokit.tutorials.budgets.shared.model.Budget;
import org.dominokit.tutorials.budgets.shared.model.Budget_MapperImpl;
import org.dominokit.tutorials.expenses.shared.model.Expense;
import org.dominokit.tutorials.expenses.shared.model.Expense_MapperImpl;

@AutoService(ServerAppEntryPoint.class)
public class BudgetsEntryPoint implements ServerAppEntryPoint<VertxContext> {
    @Override
    public void onModulesLoaded(VertxContext vertxContext) {

        vertxContext.vertx()
                .eventBus()
                .consumer("expense-added", event -> {
                    Expense expense = Expense_MapperImpl.INSTANCE.read(event.body().toString());
                    Budget budget = BudgetsMapRepository.INSTANCE.getByName(expense.getBudget());
                    budget.setSpent(budget.getSpent().add(expense.getAmount()));
                    budget.setRemaining(budget.getRemaining().subtract(expense.getAmount()));
                    BudgetsMapRepository.INSTANCE.save(budget);
                    vertxContext.vertx().eventBus().publish("budget-updated", Budget_MapperImpl.INSTANCE.write(budget));
                });

        vertxContext.vertx()
                .eventBus()
                .consumer("expense-removed", event -> {
                    Expense expense = Expense_MapperImpl.INSTANCE.read(event.body().toString());
                    Budget budget = BudgetsMapRepository.INSTANCE.getByName(expense.getBudget());
                    budget.setSpent(budget.getSpent().subtract(expense.getAmount()));
                    budget.setRemaining(budget.getRemaining().add(expense.getAmount()));
                    BudgetsMapRepository.INSTANCE.save(budget);
                    vertxContext.vertx().eventBus().publish("budget-updated", Budget_MapperImpl.INSTANCE.write(budget));
                });
    }
}
