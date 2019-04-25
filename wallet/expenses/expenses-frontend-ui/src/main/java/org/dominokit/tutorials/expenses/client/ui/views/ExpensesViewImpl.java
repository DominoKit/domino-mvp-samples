package org.dominokit.tutorials.expenses.client.ui.views;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.cards.HeaderAction;
import org.dominokit.domino.ui.datatable.ColumnConfig;
import org.dominokit.domino.ui.datatable.DataTable;
import org.dominokit.domino.ui.datatable.TableConfig;
import org.dominokit.domino.ui.datatable.plugins.DoubleClickPlugin;
import org.dominokit.domino.ui.datatable.plugins.GroupingPlugin;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.datepicker.DateBox;
import org.dominokit.domino.ui.forms.*;
import org.dominokit.domino.ui.forms.validations.ValidationResult;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.style.ColorScheme;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.ui.utils.HasValidation;
import org.dominokit.domino.ui.utils.TextNode;
import org.dominokit.domino.view.BaseElementView;

import org.dominokit.domino.api.client.annotations.UiView;

import org.dominokit.tutorials.budgets.shared.model.Budget;
import org.dominokit.tutorials.expenses.client.presenters.ExpensesProxy;
import org.dominokit.tutorials.expenses.client.views.ExpensesView;
import org.dominokit.tutorials.expenses.shared.model.Expense;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.dominokit.domino.ui.style.Unit.px;
import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.gwt.elemento.core.Elements.h;

@UiView(presentable = ExpensesProxy.class)
public class ExpensesViewImpl extends BaseElementView<HTMLDivElement> implements ExpensesView {

    private ExpensesViewUiHandler uiHandlers;

    private BigDecimalBox amountBox;
    private DateBox dateBox;
    private Select<Budget> budgetSelect;
    private TextArea descriptionBox;
    private FieldsGrouping fieldsGroup = FieldsGrouping.create();
    private LocalListDataStore<Expense> dataStore;

    @Override
    public void init(HTMLDivElement root) {

        HasValidation.Validator expenseValidator = () -> {
            if (nonNull(budgetSelect.getValue()) && nonNull(amountBox.getValue())) {
                if (amountBox.getValue().doubleValue() > budgetSelect.getValue().getRemaining().doubleValue()) {
                    return ValidationResult.invalid("Expense exceeds budget remaining amount");
                }
            }

            amountBox.clearInvalid();
            budgetSelect.clearInvalid();
            return ValidationResult.valid();
        };
        amountBox = BigDecimalBox.create("Amount")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGroup)
                .addValidator(expenseValidator);

        dateBox = DateBox.create("Date")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGroup);

        budgetSelect = Select.<Budget>create("Budget")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGroup)
                .addValidator(expenseValidator);

        descriptionBox = TextArea.create("Description")
                .setRows(1)
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGroup);


        TableConfig<Expense> tableConfig = new TableConfig<Expense>()
                .addColumn(ColumnConfig.<Expense>create("description", "Description")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getDescription())))

                .addColumn(ColumnConfig.<Expense>create("amount", "Amount")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getAmount().toString())))

                .addColumn(ColumnConfig.<Expense>create("date", "Date")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getDate().toString())))

                .addColumn(ColumnConfig.<Expense>create("budget", "Budget")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getBudget())))

                .addColumn(ColumnConfig.<Expense>create("actions", "Actions")
                        .setCellRenderer(cellInfo -> Icons.ALL.delete()
                                .clickable()
                                .addClickListener(evt -> uiHandlers.deleteExpense(cellInfo.getRecord()))
                                .asElement()));

        tableConfig.addPlugin(new GroupingPlugin<>(tableRow -> tableRow.getRecord().getBudget(), cellInfo -> {
            DominoElement.of(cellInfo.getElement())
                    .style()
                    .setProperty("border-bottom", "1px solid #afafaf")
                    .setPadding(px.of(5))
                    .add(ColorScheme.INDIGO.lighten_5().getBackground());
            return TextNode.of(cellInfo.getRecord().getBudget());
        }));

        dataStore = new LocalListDataStore<>();

        DataTable<Expense> dataTable = new DataTable<>(tableConfig, dataStore);


        DominoElement.of(root)
                .appendChild(Card.create("Expenses")
                        .addHeaderAction(HeaderAction.create(Icons.ALL.add())
                                .addClickListener(evt -> uiHandlers.onAddExpense())
                                .setTooltip("Add"))
                        .appendChild(Row.create()
                                .appendChild(Column.span6().appendChild(amountBox))
                                .appendChild(Column.span6().appendChild(dateBox))
                        )
                        .appendChild(Row.create()
                                .appendChild(Column.span6().appendChild(descriptionBox))
                                .appendChild(Column.span6().appendChild(budgetSelect))
                        ))
                .appendChild(Card.create()
                        .appendChild(dataTable));

        amountBox.focus();
    }

    @Override
    public Expense save() {
        Expense expense = new Expense();
        expense.setAmount(amountBox.getValue());
        expense.setDate(dateBox.getValue());
        expense.setDescription(descriptionBox.getValue());
        if (nonNull(budgetSelect.getValue())) {
            expense.setBudget(budgetSelect.getValue().getName());
        }
        return expense;
    }

    @Override
    public boolean isValid() {
        return fieldsGroup.validate().isValid();
    }

    @Override
    public void setExpenses(List<Expense> expensesList) {
        dataStore.setData(expensesList);
        dataStore.load();
    }

    @Override
    public void setBudgets(List<Budget> budgets) {
        budgetSelect.removeAllOptions()
                .apply(select -> budgets.forEach(budget -> select.appendChild(SelectOption.create(budget, budget.getName()))));
        budgetSelect.clearInvalid();
    }

    @Override
    public void onExpenseAdded(Expense expense) {
        dataStore.addRecord(expense);
        fieldsGroup.clear();
        fieldsGroup.clearInvalid();
        amountBox.focus();
        uiHandlers.updateBudgets();
    }

    @Override
    public void onExpenseDeleted(Expense expense) {
        dataStore.removeRecord(expense);
    }

    @Override
    public void setUiHandlers(ExpensesViewUiHandler uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public HTMLDivElement createRoot() {
        return div().asElement();
    }
}