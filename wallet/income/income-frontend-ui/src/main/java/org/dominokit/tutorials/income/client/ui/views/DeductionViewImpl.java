package org.dominokit.tutorials.income.client.ui.views;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.api.client.annotations.UiView;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.datatable.ColumnConfig;
import org.dominokit.domino.ui.datatable.DataTable;
import org.dominokit.domino.ui.datatable.TableConfig;
import org.dominokit.domino.ui.datatable.plugins.EmptyStatePlugin;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.forms.BigDecimalBox;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.grid.Row_12;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.style.Styles;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.ui.utils.TextNode;
import org.dominokit.domino.view.BaseElementView;
import org.dominokit.tutorials.income.client.presenters.DeductionProxy;
import org.dominokit.tutorials.income.client.views.DeductionView;
import org.dominokit.tutorials.income.shared.model.Deduction;

import java.math.BigDecimal;
import java.util.List;

import static org.jboss.gwt.elemento.core.Elements.div;


public abstract class DeductionViewImpl extends BaseElementView<HTMLDivElement> implements DeductionView {

    private Row_12 addDeductionRow;
    private TableConfig<Deduction> tableConfig;

    @UiView(presentable = DeductionProxy.NewIncomeDeductionProxy.class)
    public static class NewIncomeDeductionsViewImpl extends DeductionViewImpl{}

    @UiView(presentable = DeductionProxy.IncomeDeductionProxy.class)
    public static class IncomeDetailsDeductionsViewImpl extends DeductionViewImpl{}

    private DeductionsUiHandlers uiHandlers;

    private TextBox descriptionBox;
    private BigDecimalBox amountBox;

    private FieldsGrouping fieldsGrouping = FieldsGrouping.create();
    private LocalListDataStore<Deduction> dataStore;
    private DataTable<Deduction> dataTable;

    @Override
    protected void init(HTMLDivElement root) {

        descriptionBox = TextBox.create("Description")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping)
                .setPlaceholder("Description");

        amountBox = BigDecimalBox.create("Amount")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping)
                .setPlaceholder("Description")
                .value(BigDecimal.ZERO);

        tableConfig = new TableConfig<>();
        tableConfig
                .addColumn(ColumnConfig.<Deduction>create("description", "Description")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getDescription())))
                .addColumn(ColumnConfig.<Deduction>create("amount", "Amount")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getAmount().toString())))
                .addColumn(ColumnConfig.<Deduction>create("actions", "Actions")
                        .setCellRenderer(cellInfo -> Icons.ALL.delete()
                                .clickable()
                                .addClickListener(evt -> uiHandlers.onRemoveDeduction(cellInfo.getRecord()))
                                .asElement()
                        ));

        EmptyStatePlugin<Deduction> noDeductions = new EmptyStatePlugin<>(Icons.ALL.list(), "No Deductions");
        noDeductions.getEmptyState().style().remove(Styles.vertical_center);
        tableConfig.addPlugin(noDeductions);

        dataStore = new LocalListDataStore<>();

        dataTable = new DataTable<>(tableConfig, dataStore);

        addDeductionRow = Row.create();
        DominoElement.of(root)
                .appendChild(addDeductionRow
                        .appendChild(Column.span4().appendChild(descriptionBox))
                        .appendChild(Column.span4().appendChild(amountBox))
                        .appendChild(Column.span4().appendChild(Button.createPrimary("Add")
                                .addClickListener(evt -> {
                                    if (fieldsGrouping.validate().isValid()) {
                                        uiHandlers.onAddDeduction(descriptionBox.getValue(), amountBox.getValue());
                                    }
                                })))
                )
                .appendChild(Row.create()
                        .appendChild(Column.span12().appendChild(dataTable)
                        )
                );
    }

    @Override
    public HTMLDivElement createRoot() {
        return div().asElement();
    }

    @Override
    public void onDeductionAdded(Deduction deduction) {
        dataStore.addRecord(deduction);
        descriptionBox.clear();
        amountBox.setValue(BigDecimal.ZERO);
        descriptionBox.focus();
        descriptionBox.clearInvalid();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        addDeductionRow.toggleDisplay(!readOnly);
        tableConfig.getColumnByName("actions")
                .toggleDisplay(!readOnly);
    }

    @Override
    public void setDeductions(List<Deduction> deductions) {
        dataStore.addRecords(deductions);
    }

    @Override
    public void onDeductionRemoved(Deduction deduction) {
        dataStore.removeRecord(deduction);
    }

    @Override
    public void setUiHandlers(DeductionsUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
