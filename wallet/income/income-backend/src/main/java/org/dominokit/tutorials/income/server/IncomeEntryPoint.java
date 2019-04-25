package org.dominokit.tutorials.income.server;

import com.google.auto.service.AutoService;
import org.dominokit.domino.api.server.entrypoint.ServerAppEntryPoint;
import org.dominokit.domino.api.server.entrypoint.VertxContext;
import org.dominokit.tutorials.budgets.shared.model.Budget;
import org.dominokit.tutorials.budgets.shared.model.Budget_MapperImpl;
import org.dominokit.tutorials.budgets.shared.services.BudgetsServiceFactory;
import org.dominokit.tutorials.income.server.resources.IncomeMapRepository;
import org.dominokit.tutorials.income.shared.model.Income;

import java.math.BigDecimal;

@AutoService({ServerAppEntryPoint.class})
public class IncomeEntryPoint implements ServerAppEntryPoint<VertxContext> {
    @Override
    public void onModulesLoaded(VertxContext context) {

        context.vertx()
                .eventBus()
                .consumer("budget-updated", event -> {
                    Budget budget = Budget_MapperImpl.INSTANCE.read(event.body().toString());
                    Income income = IncomeMapRepository.INSTANCE.getByTitle(budget.getIncome());
                    BudgetsServiceFactory.INSTANCE.listAll()
                            .onSuccess(response -> {
                                double spentTotal = response.asList()
                                        .stream()
                                        .filter(budgetItem -> budget.getIncome().equals(income.getTitle()))
                                        .mapToDouble(budgetItem -> budgetItem.getSpent().doubleValue())
                                        .sum();
                                double deductions = income.getDeductions()
                                        .stream()
                                        .mapToDouble(deduction -> deduction.getAmount().doubleValue())
                                        .sum();
                                double total = spentTotal + deductions;

                                BigDecimal remaining = income.getAmount().subtract(BigDecimal.valueOf(total));

                                income.setSpent(BigDecimal.valueOf(spentTotal));
                                income.setRemaining(remaining);

                                IncomeMapRepository.INSTANCE.save(income);

                            })
                            .send();
                });

    }
}
