package org.dominokit.tutorials.layout.client.presenters;

import org.dominokit.domino.api.client.annotations.presenter.*;
import org.dominokit.domino.api.client.mvp.presenter.ViewBaseClientPresenter;
import org.dominokit.tutorials.layout.client.views.LayoutView;
import org.dominokit.tutorials.layout.shared.extension.LayoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.dominokit.domino.api.client.mvp.presenter.ViewBaseClientPresenter.DOCUMENT_BODY;

@PresenterProxy
@AutoRoute(routeOnce = true)
@Slot(DOCUMENT_BODY)
@Singleton
@AutoReveal
@OnStateChanged(LayoutEvent.class)
public class LayoutProxy extends ViewBaseClientPresenter<LayoutView> implements LayoutView.LayoutUiHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(LayoutProxy.class);

    @OnInit
    public void onLayoutInit() {
        LOGGER.info("Layout initialized");
    }

    @OnReveal
    public void onLayoutRevealed() {
        LOGGER.info("Layout view revealed");
    }

    @Override
    public void onMenuItemSelected(String token) {
        history().fireState(token);
    }
}