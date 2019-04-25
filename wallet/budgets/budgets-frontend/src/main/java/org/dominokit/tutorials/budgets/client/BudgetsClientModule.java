package org.dominokit.tutorials.budgets.client;

import com.google.gwt.core.client.EntryPoint;
import org.dominokit.domino.api.client.ModuleConfigurator;
import org.dominokit.domino.api.client.annotations.ClientModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name="Budgets")
public class BudgetsClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(BudgetsClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing Budgets frontend module ...");
		new ModuleConfigurator().configureModule(new BudgetsModuleConfiguration());
	}
}
