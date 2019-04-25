package org.dominokit.tutorials.layout.client.views.ui;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.api.client.mvp.slots.SlotsEntries;
import org.dominokit.domino.ui.layout.Layout;
import org.dominokit.domino.ui.tree.Tree;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.domino.view.BaseElementView;

import org.dominokit.domino.api.client.annotations.UiView;

import org.dominokit.domino.view.slots.SingleElementSlot;
import org.dominokit.tutorials.layout.client.presenters.LayoutProxy_Presenter;
import org.dominokit.tutorials.layout.client.views.LayoutView;

import static org.jboss.gwt.elemento.core.Elements.h;

@UiView(presentable = LayoutProxy_Presenter.class)
public class LayoutViewImpl extends BaseElementView<HTMLDivElement> implements LayoutView {

    private Layout layout;
    private LayoutUiHandlers uiHandlers;

    @Override
    public void init(HTMLDivElement root) {
        Tree menu = Tree.create("Menu")
                .appendChild(TreeItem.create("Income")
                        .addClickListener(evt -> {
                            uiHandlers.onMenuItemSelected("income");
                        })
                )
                .appendChild(TreeItem.create("Budgets")
                        .addClickListener(evt -> {
                            uiHandlers.onMenuItemSelected("budgets");
                        })
                )
                .appendChild(TreeItem.create("Expenses")
                        .addClickListener(evt -> {
                            uiHandlers.onMenuItemSelected("expenses");
                        })
                );

        layout
                .autoFixLeftPanel()
                .getLeftPanel()
                .appendChild(menu);
    }

    @Override
    public SlotsEntries getSlots() {
        return SlotsEntries.create()
                .add("content", SingleElementSlot.of(layout.getContentPanel()));
    }

    @Override
    public HTMLDivElement createRoot() {
        this.layout = Layout.create("Wallet");
        return layout.asElement();
    }

    @Override
    public void setUiHandlers(LayoutUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}