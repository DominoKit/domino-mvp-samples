package org.dominokit.tutorials.budgets.client.ui.views;

import elemental2.dom.HTMLDivElement;
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

import org.dominokit.domino.api.client.annotations.UiView;

import org.dominokit.tutorials.budgets.client.presenters.BudgetsProxy;
import org.dominokit.tutorials.budgets.client.views.BudgetsView;
import org.dominokit.tutorials.budgets.shared.model.Budget;

import java.util.List;

import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.gwt.elemento.core.Elements.h;

@UiView(presentable = BudgetsProxy.class)
public class BudgetsViewImpl extends BaseElementView<HTMLDivElement> implements BudgetsView{

    private BudgetsViewUiHandlers uiHandlers;
    private LocalListDataStore<Budget> dataStore;

    @Override
    public void init(HTMLDivElement root) {
        TableConfig<Budget> tableConfig = new TableConfig<Budget>()
                .addColumn(ColumnConfig.<Budget>create("name", "Name")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getName())))

                .addColumn(ColumnConfig.<Budget>create("amount", "Amount")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getAmount().toString())))

                .addColumn(ColumnConfig.<Budget>create("income", "Income")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getIncome())))

                .addColumn(ColumnConfig.<Budget>create("spent", "Spent")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getSpent().toString())))

                .addColumn(ColumnConfig.<Budget>create("remaining", "Remaining")
                        .setCellRenderer(cellInfo -> TextNode.of(cellInfo.getRecord().getRemaining().toString())))
                .addPlugin(new DoubleClickPlugin<>(tableRow -> uiHandlers.onBudgetSelected(tableRow.getRecord())));

        dataStore = new LocalListDataStore<>();

        DataTable<Budget> dataTable = new DataTable<>(tableConfig, dataStore);

        root.appendChild(Card.create("Budgets")
                .showHeader()
                .apply(card -> card.getHeaderBar()
                        .appendChild(Button.create(Icons.ALL.add()
                                .setColor(Color.WHITE))
                                .setButtonType(StyleType.PRIMARY)
                                .setContent("New budget")
                                .addClickListener(evt -> this.uiHandlers.onNewBudgetRequest())
                        ))
                .appendChild(dataTable)
                .asElement());
    }

    @Override
    public void setBudgets(List<Budget> budgets) {
        dataStore.setData(budgets);
        dataStore.load();
    }

    @Override
    public HTMLDivElement createRoot() {
        return div().asElement();
    }

    @Override
    public void setUiHandlers(BudgetsViewUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}