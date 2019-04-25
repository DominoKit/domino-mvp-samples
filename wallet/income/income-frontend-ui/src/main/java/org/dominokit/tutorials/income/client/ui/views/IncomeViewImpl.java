package org.dominokit.tutorials.income.client.ui.views;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.api.client.annotations.UiView;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.datatable.ColumnConfig;
import org.dominokit.domino.ui.datatable.DataTable;
import org.dominokit.domino.ui.datatable.TableConfig;
import org.dominokit.domino.ui.datatable.plugins.DoubleClickPlugin;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.style.StyleType;
import org.dominokit.domino.ui.utils.TextNode;
import org.dominokit.domino.view.BaseElementView;
import org.dominokit.tutorials.income.client.presenters.IncomeProxy;
import org.dominokit.tutorials.income.client.views.IncomeView;
import org.dominokit.tutorials.income.shared.model.Deduction;
import org.dominokit.tutorials.income.shared.model.Income;

import java.util.List;

import static org.jboss.gwt.elemento.core.Elements.div;

@UiView(presentable = IncomeProxy.class)
public class IncomeViewImpl extends BaseElementView<HTMLDivElement> implements IncomeView {

    private IncomeUiHandlers uiHandlers;
    private LocalListDataStore<Income> dataStore;

    @Override
    public void init(HTMLDivElement root) {

        TableConfig<Income> tableConfig = new TableConfig<Income>()
                .addColumn(ColumnConfig.<Income>create("title", "Title")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getTitle())))

                .addColumn(ColumnConfig.<Income>create("amount", "Amount")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getAmount().toString())))

                .addColumn(ColumnConfig.<Income>create("tax", "Tax")
                        .setCellRenderer(cellInfo -> TextNode.of((cellInfo.getRecord().getTax() * 100) + "%")))

                .addColumn(ColumnConfig.<Income>create("deductions", "Total deduction")
                        .setCellRenderer(cellInfo -> {
                            double[] sum = new double[1];
                            List<Deduction> deductions = cellInfo.getRecord().getDeductions();
                            deductions.forEach(deduction -> sum[0] += deduction.getAmount().doubleValue());
                            return TextNode.of(sum[0] + "");

                        }))

                .addColumn(ColumnConfig.<Income>create("schedule", "Schedule")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getSchedule().toString())))

                .addColumn(ColumnConfig.<Income>create("spent", "Spent")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getSpent().toString())))

                .addColumn(ColumnConfig.<Income>create("remaining", "Remaining")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getRemaining().toString())))
                .addPlugin(new DoubleClickPlugin<>(tableRow -> uiHandlers.onIncomeSelected(tableRow.getRecord())));

        dataStore = new LocalListDataStore<>();

        DataTable<Income> dataTable = new DataTable<>(tableConfig, dataStore);

        root.appendChild(Card.create("Incomes")
                .showHeader()
                .apply(card -> card.getHeaderBar()
                        .appendChild(Button.create(Icons.ALL.add()
                                .setColor(Color.WHITE))
                                .setButtonType(StyleType.PRIMARY)
                                .setContent("New income")
                                .addClickListener(evt -> this.uiHandlers.onNewIncomeRequest())
                        ))
                .appendChild(dataTable)
                .asElement());
    }

    @Override
    public HTMLDivElement createRoot() {
        return div().asElement();
    }

    @Override
    public void setUiHandlers(IncomeUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public void setIncomes(List<Income> incomes) {
        dataStore.setData(incomes);
        dataStore.load();
    }
}