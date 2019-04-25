package org.dominokit.tutorials.income.client.presenters;

import org.dominokit.domino.api.client.annotations.presenter.*;
import org.dominokit.domino.api.client.mvp.presenter.ViewBaseClientPresenter;
import org.dominokit.tutorials.income.client.views.IncomeView;
import org.dominokit.tutorials.income.shared.extension.IncomeEvent;
import org.dominokit.tutorials.income.shared.model.Income;
import org.dominokit.tutorials.income.shared.services.IncomeServiceFactory;
import org.dominokit.tutorials.layout.shared.extension.LayoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@PresenterProxy
@AutoRoute(token = "income")
@Slot("content")
@AutoReveal
@OnStateChanged(IncomeEvent.class)
@DependsOn(@EventsGroup(LayoutEvent.class))
public class IncomeProxy extends ViewBaseClientPresenter<IncomeView> implements IncomeView.IncomeUiHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeProxy.class);

    @OnReveal
    public void loadIncomes() {
        IncomeServiceFactory.INSTANCE
                .listAll()
                .onSuccess(response -> {
                    List<Income> incomes = response.asList();
                    view.setIncomes(incomes);
                })
                .onFailed(failedResponse -> {
                    LOGGER.error("Failed to load incomes", failedResponse.getThrowable());
                })
                .send();
    }

    @Override
    public void onNewIncomeRequest() {
        history().fireState("new-income");
    }

    @Override
    public void onIncomeSelected(Income income) {
        history().fireState("income/"+income.getTitle());
    }
}