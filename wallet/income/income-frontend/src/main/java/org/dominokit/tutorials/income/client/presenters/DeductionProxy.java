package org.dominokit.tutorials.income.client.presenters;

import org.dominokit.domino.api.client.annotations.presenter.*;
import org.dominokit.domino.api.client.mvp.presenter.ViewBaseClientPresenter;
import org.dominokit.domino.api.shared.history.TokenFilter;
import org.dominokit.tutorials.income.client.views.DeductionView;
import org.dominokit.tutorials.income.shared.extension.*;
import org.dominokit.tutorials.income.shared.model.Deduction;
import org.dominokit.tutorials.income.shared.services.IncomeServiceFactory;

import java.math.BigDecimal;


public class DeductionProxy extends ViewBaseClientPresenter<DeductionView> implements DeductionView.DeductionsUiHandlers {

    @PresenterProxy
    @AutoRoute(token = "new-income")
    @AutoReveal
    @Slot("deductions")
    @DependsOn(@EventsGroup({NewIncomeEvent.class}))
    public static class NewIncomeDeductionProxy extends DeductionProxy {
        @OnReveal
        public void setEditable() {
            view.setReadOnly(false);
        }
    }

    @PresenterProxy
    @AutoRoute(token = "income/:title")
    @AutoReveal
    @Slot("deductions")
    @DependsOn(@EventsGroup({IncomeItemEvent.class}))
    public static class IncomeDeductionProxy extends DeductionProxy {

        @PathParameter
        String title;

        @RoutingTokenFilter
        public static TokenFilter onRoutingFilter(String token) {
            return TokenFilter.startsWithPathFilter(token);
        }

        @OnReveal
        public void loadIncomeDeductions() {
            IncomeServiceFactory.INSTANCE.getIncomeDeductions(title)
                    .onSuccess(response -> {
                        view.setDeductions(response.asList());
                        view.setReadOnly(true);
                    })
                    .send();
        }
    }

    @Override
    public void onAddDeduction(String description, BigDecimal amount) {
        Deduction deduction = new Deduction(description, amount);
        fireEvent(AddDedcutionEvent.class, new AddDedcutionEvent(deduction));
        view.onDeductionAdded(deduction);
    }

    @Override
    public void onRemoveDeduction(Deduction deduction) {
        fireEvent(RemoveDeductionEvent.class, new RemoveDeductionEvent(deduction));
        view.onDeductionRemoved(deduction);
    }

    @ListenTo(event = EditIncomeEvent.class)
    public void onEditIncome(EditIncomeEventContext context) {
        view.setReadOnly(!context.isEditable());
    }
}
