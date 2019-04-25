package org.dominokit.tutorials.budgets.client.presenters;

import org.dominokit.domino.api.client.annotations.presenter.*;
import org.dominokit.domino.api.client.mvp.presenter.ViewBaseClientPresenter;
import org.dominokit.tutorials.budgets.client.views.BudgetsView;
import org.dominokit.tutorials.budgets.shared.extension.BudgetsEvent;
import org.dominokit.tutorials.budgets.shared.model.Budget;
import org.dominokit.tutorials.budgets.shared.services.BudgetsServiceFactory;
import org.dominokit.tutorials.layout.shared.extension.LayoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PresenterProxy
@AutoRoute(token = "budgets")
@Slot("content")
@AutoReveal
@DependsOn(@EventsGroup({LayoutEvent.class}))
@OnStateChanged(BudgetsEvent.class)
public class BudgetsProxy extends ViewBaseClientPresenter<BudgetsView> implements BudgetsView.BudgetsViewUiHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetsProxy.class);

    @OnReveal
    public void loadBudgets() {
        BudgetsServiceFactory.INSTANCE
                .listAll()
                .onSuccess(response -> view.setBudgets(response.asList()))
                .onFailed(failedResponse -> LOGGER.error("Failed to load budgets", failedResponse.getThrowable()))
                .send();
    }

    @Override
    public void onBudgetSelected(Budget budget) {
        history().fireState(history().currentToken().appendPath(budget.getName()).value());
    }

    @Override
    public void onNewBudgetRequest() {
        history().fireState("new-budget");
    }
}