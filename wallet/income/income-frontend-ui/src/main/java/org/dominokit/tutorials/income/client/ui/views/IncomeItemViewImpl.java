package org.dominokit.tutorials.income.client.ui.views;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.api.client.annotations.UiView;
import org.dominokit.domino.api.client.mvp.slots.SlotsEntries;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.cards.HeaderAction;
import org.dominokit.domino.ui.forms.*;
import org.dominokit.domino.ui.forms.validations.ValidationResult;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.view.BaseElementView;
import org.dominokit.domino.view.slots.SingleElementSlot;
import org.dominokit.tutorials.income.client.presenters.IncomeItemProxy;
import org.dominokit.tutorials.income.client.views.IncomeItemView;
import org.dominokit.tutorials.income.shared.model.Income;
import org.dominokit.tutorials.income.shared.model.Schecule;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.jboss.gwt.elemento.core.Elements.div;

public class IncomeItemViewImpl extends BaseElementView<HTMLDivElement> implements IncomeItemView {

    private final HeaderAction saveAction = HeaderAction.create(Icons.ALL.save());
    private final HeaderAction editAction = HeaderAction.create(Icons.ALL.edit());

    @UiView(presentable = IncomeItemProxy.NewIncomeProxy.class)
    public static class NewIncomeView extends IncomeItemViewImpl {

    }

    @UiView(presentable = IncomeItemProxy.IncomeDetailsProxy.class)
    public static class IncomeDetailsView extends IncomeItemViewImpl {

    }

    private TextBox titleBox;
    private BigDecimalBox amountBox;
    private FloatBox taxBox;
    private Select<Schecule> scheduleSelect;
    private Income income;
    private NewIncomeUiHandlers uiHandlers;

    private HTMLDivElement deductionsContainer = div().asElement();

    private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

    @Override
    protected void init(HTMLDivElement root) {

        titleBox = TextBox.create("Title")
                .focus()
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping)
                .setPlaceholder("Title");

        amountBox = BigDecimalBox.create("Amount")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping)
                .setPlaceholder("Amount");

        taxBox = FloatBox.create("Tax")
                .setRequired(true)
                .setAutoValidation(true)
                .setPattern("0.###")
                .addValidator(() -> {
                    if (taxBox.getValue() > 1 || taxBox.getValue() < 0) {
                        return ValidationResult.invalid("Value sould be between 0 and 1");
                    }
                    return ValidationResult.valid();
                })
                .groupBy(fieldsGrouping)
                .setPlaceholder("Tax");

        scheduleSelect = Select.<Schecule>create("Schedule")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping)
                .appendChild(SelectOption.create(Schecule.yearly, "Yearly"))
                .appendChild(SelectOption.create(Schecule.monthly, "Monthly"))
                .appendChild(SelectOption.create(Schecule.weekly, "Weekly"))
                .appendChild(SelectOption.create(Schecule.once, "Once"));


        DominoElement.of(root)
                .appendChild(Card.create("New Income")
                        .showHeader()
                        .apply(card -> card
                                .addHeaderAction(saveAction
                                        .addClickListener(evt -> uiHandlers.onSaveIncome())
                                        .setTooltip("Save"))
                                .addHeaderAction(editAction
                                        .hide()
                                        .addClickListener(evt -> uiHandlers.onEditIncome())
                                        .setTooltip("Edit"))
                                .addHeaderAction(HeaderAction.create(Icons.ALL.close())
                                        .addClickListener(evt -> uiHandlers.onCancel())
                                        .setTooltip("Cancel"))
                        )
                        .appendChild(Row.create()
                                .appendChild(Column.span6()
                                        .appendChild(Row.create()
                                                .appendChild(Column.span12().appendChild(titleBox))
                                        )
                                        .appendChild(Row.create()
                                                .appendChild(Column.span12().appendChild(amountBox))
                                        )
                                        .appendChild(Row.create()
                                                .appendChild(Column.span12().appendChild(taxBox))
                                        )
                                        .appendChild(Row.create()
                                                .appendChild(Column.span12().appendChild(scheduleSelect))
                                        ))
                                .appendChild(Column.span6()
                                        .appendChild(deductionsContainer))
                        )
                );
    }

    @Override
    public SlotsEntries getSlots() {
        return SlotsEntries.create()
                .add("deductions", SingleElementSlot.of(deductionsContainer));
    }

    @Override
    public void setReadOnly(boolean readonly) {
        fieldsGrouping.setReadOnly(readonly);
        saveAction.toggleDisplay(!readonly);
        editAction.toggleDisplay(readonly);
    }

    @Override
    public void edit(Income income) {
        this.income = income;
        titleBox.setValue(income.getTitle());
        amountBox.setValue(income.getAmount());
        taxBox.setValue(income.getTax());
        scheduleSelect.setValue(income.getSchedule());
    }

    @Override
    public Income save() {

        this.income.setAmount(amountBox.getValue());
        this.income.setTitle(titleBox.getValue());
        this.income.setTax(taxBox.getValue());
        this.income.setSchedule(scheduleSelect.getValue());
        this.income.setDeductions(new ArrayList<>());
        this.income.setRemaining(amountBox.getValue());
        this.income.setSpent(BigDecimal.ZERO);

        return this.income;
    }

    @Override
    public boolean isValid() {
        return fieldsGrouping.validate().isValid();
    }

    @Override
    public void setUiHandlers(NewIncomeUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public HTMLDivElement createRoot() {
        return div().asElement();
    }
}
