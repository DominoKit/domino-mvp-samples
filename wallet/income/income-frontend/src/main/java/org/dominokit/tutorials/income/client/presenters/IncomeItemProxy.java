package org.dominokit.tutorials.income.client.presenters;

import org.dominokit.domino.api.client.annotations.presenter.*;
import org.dominokit.domino.api.client.mvp.presenter.ViewBaseClientPresenter;
import org.dominokit.domino.api.shared.history.TokenFilter;
import org.dominokit.tutorials.income.client.views.IncomeItemView;
import org.dominokit.tutorials.income.shared.extension.*;
import org.dominokit.tutorials.income.shared.model.Deduction;
import org.dominokit.tutorials.income.shared.model.Income;
import org.dominokit.tutorials.income.shared.services.IncomeServiceFactory;
import org.dominokit.tutorials.layout.shared.extension.LayoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class IncomeItemProxy extends ViewBaseClientPresenter<IncomeItemView> implements IncomeItemView.NewIncomeUiHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeItemProxy.class);
    protected List<Deduction> deductions;

    @PresenterProxy
    @AutoRoute(token = "new-income")
    @AutoReveal
    @Slot("content")
    @DependsOn(@EventsGroup(LayoutEvent.class))
    @OnStateChanged(NewIncomeEvent.class)
    public static class NewIncomeProxy extends IncomeItemProxy {

        @OnReveal
        public void editNewIncome() {
            view.edit(new Income());
            this.deductions = new ArrayList<>();
            view.setReadOnly(false);
        }
    }

    @PresenterProxy
    @AutoRoute(token = "income/:title")
    @AutoReveal
    @Slot("content")
    @DependsOn(@EventsGroup(LayoutEvent.class))
    @OnStateChanged(IncomeItemEvent.class)
    public static class IncomeDetailsProxy extends IncomeItemProxy {

        @PathParameter
        protected String title;

        @RoutingTokenFilter
        public static TokenFilter onRoutingFilter(String token) {
            return TokenFilter.startsWithPathFilter(token);
        }

        @OnReveal
        public void loadIncome() {
            IncomeServiceFactory.INSTANCE
                    .getIncome(title)
                    .onSuccess(income -> {
                        view.edit(income);
                        deductions = income.getDeductions();
                        view.setReadOnly(true);
                    })
                    .onFailed(failedResponse -> LOGGER.error("Failed to load income [" + title + "]"))
                    .send();
        }
    }

    @Override
    public void onSaveIncome() {
        if (view.isValid()) {
            Income income = view.save();
            income.setDeductions(deductions);
            IncomeServiceFactory.INSTANCE
                    .create(income)
                    .onSuccess(response -> {
                        fireEvent(EditIncomeEvent.class, new EditIncomeEvent(false));
                    })
                    .onFailed(failedResponse -> LOGGER.error("Could not create new income"))
                    .send();
        }
    }

    @ListenTo(event = AddDedcutionEvent.class)
    public void onAddDeduction(DeductionEventContext context) {
        deductions.add(context.getDeduction());
    }

    @ListenTo(event = RemoveDeductionEvent.class)
    public void onRemoveDeduction(DeductionEventContext context) {
        deductions.remove(context.getDeduction());
    }

    @Override
    public void onCancel() {
        history().fireState("income");
    }

    @ListenTo(event = EditIncomeEvent.class)
    public void onEditIncome(EditIncomeEventContext context) {
        view.setReadOnly(!context.isEditable());
    }

    @Override
    public void onEditIncome() {
        fireEvent(EditIncomeEvent.class, new EditIncomeEvent(true));
    }
}
