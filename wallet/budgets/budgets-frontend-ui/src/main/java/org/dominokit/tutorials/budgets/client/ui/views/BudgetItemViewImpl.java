package org.dominokit.tutorials.budgets.client.ui.views;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.api.client.annotations.UiView;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.cards.HeaderAction;
import org.dominokit.domino.ui.forms.*;
import org.dominokit.domino.ui.forms.validations.ValidationResult;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.ui.utils.HasValidation;
import org.dominokit.domino.view.BaseElementView;
import org.dominokit.tutorials.budgets.client.presenters.BudgetItemProxy;
import org.dominokit.tutorials.budgets.client.views.BudgetItemView;
import org.dominokit.tutorials.budgets.shared.model.Budget;
import org.dominokit.tutorials.income.shared.model.Income;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.jboss.gwt.elemento.core.Elements.div;

public class BudgetItemViewImpl extends BaseElementView<HTMLDivElement> implements BudgetItemView {

    protected HeaderAction saveAction = HeaderAction.create(Icons.ALL.save()).setTooltip("Save");
    protected HeaderAction editAction = HeaderAction.create(Icons.ALL.edit()).setTooltip("Edit");
    private Budget budget;
    private List<Income> incomes;
    private double totalBudgets = Double.MAX_VALUE;

    @UiView(presentable = BudgetItemProxy.NewBudgetProxy.class)
    public static class NewBudgetViewImpl extends BudgetItemViewImpl {

        @Override
        protected void init(HTMLDivElement root) {
            super.init(root);
            spentBox.hide();
            remainingBox.hide();
            saveAction.show();
            editAction.hide();
            nameBox.focus();
        }
    }

    @UiView(presentable = BudgetItemProxy.BudgetDetailsProxy.class)
    public static class BudgetDetailsViewImpl extends BudgetItemViewImpl {

    }

    private BudgetItemViewUiHandlers uiHandlers;

    protected TextBox nameBox;
    protected Select<Income> incomeSelect;
    protected BigDecimalBox amountBox;
    protected BigDecimalBox spentBox;
    protected BigDecimalBox remainingBox;

    private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

    @Override
    protected void init(HTMLDivElement root) {

        HasValidation.Validator budgetAmountValidator = () -> {
            if(nonNull(amountBox.getValue()) && nonNull(incomeSelect.getValue())){
                if((amountBox.getValue().doubleValue()+totalBudgets)> incomeSelect.getValue().getAmount().doubleValue()){
                    return ValidationResult.invalid("Budget exceeds income limit");
                }
            }
            amountBox.clearInvalid();
            incomeSelect.clearInvalid();
            return ValidationResult.valid();
        };

        nameBox = TextBox.create("Name")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping);

        incomeSelect = Select.<Income>create("Income")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping)
                .addValidator(budgetAmountValidator)
                .addChangeHandler(value -> uiHandlers.onIncomeSelected(value));

        amountBox = BigDecimalBox.create("Amount")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping)
                .addValidator(budgetAmountValidator);

        remainingBox = BigDecimalBox.create("Remaining")
                .setReadOnly(true);

        spentBox = BigDecimalBox.create("Spent")
                .setReadOnly(true);

        DominoElement.of(root)
                .appendChild(Card.create("Budget")
                        .apply(card -> card
                                .addHeaderAction(saveAction.addClickListener(evt -> uiHandlers.onSave()))
                                .addHeaderAction(editAction.addClickListener(evt -> uiHandlers.onEdit()))
                                .addHeaderAction(HeaderAction.create(Icons.ALL.close())
                                        .addClickListener(evt -> uiHandlers.onClose())
                                        .setTooltip("Close"))
                        )
                        .appendChild(Row.create().appendChild(Column.span6().appendChild(nameBox)))
                        .appendChild(Row.create().appendChild(Column.span6().appendChild(incomeSelect)))
                        .appendChild(Row.create().appendChild(Column.span6().appendChild(amountBox)))
                        .appendChild(Row.create().appendChild(Column.span6().appendChild(remainingBox)))
                        .appendChild(Row.create().appendChild(Column.span6().appendChild(spentBox)))
                );
    }

    @Override
    public void edit(Budget budget) {
        nameBox.setValue(budget.getName());
        amountBox.setValue(budget.getAmount());
        incomeSelect.setKey(budget.getIncome());
        remainingBox.setValue(budget.getRemaining());
        spentBox.setValue(budget.getSpent());

        this.budget = budget;
    }

    @Override
    public Budget save() {
        budget.setName(nameBox.getValue());
        budget.setAmount(amountBox.getValue());
        budget.setIncome(incomeSelect.getValue().getTitle());
        return budget;
    }

    @Override
    public boolean isValid() {
        return fieldsGrouping.validate().isValid();
    }

    @Override
    public HTMLDivElement createRoot() {
        return div().asElement();
    }

    @Override
    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
        incomes.forEach(income -> incomeSelect.appendChild(SelectOption.<Income>create(income, income.getTitle())));
    }

    @Override
    public void setReadonly(boolean readonly) {
        saveAction.toggleDisplay(!readonly);
        editAction.toggleDisplay(readonly);
        fieldsGrouping.setReadOnly(readonly);

        if (!readonly) {
            nameBox.focus();
        } else {
            remainingBox.show();
            spentBox.show();
        }
    }

    @Override
    public void validateBudgetAmount(double totalBudgets) {
        this.totalBudgets = totalBudgets;
    }

    @Override
    public void setUiHandlers(BudgetItemViewUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
