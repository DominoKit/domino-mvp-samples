package org.dominokit.tutorials.layout.client.views;

import org.dominokit.domino.api.client.mvp.view.ContentView;
import org.dominokit.domino.api.client.mvp.view.HasUiHandlers;
import org.dominokit.domino.api.client.mvp.view.UiHandlers;

public interface LayoutView extends ContentView, HasUiHandlers<LayoutView.LayoutUiHandlers> {

    interface LayoutUiHandlers extends UiHandlers{
        void onMenuItemSelected(String token);
    }
}