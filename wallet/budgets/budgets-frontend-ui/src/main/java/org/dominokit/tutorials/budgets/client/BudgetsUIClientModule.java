package org.dominokit.tutorials.budgets.client;

import com.google.gwt.core.client.EntryPoint;
import org.dominokit.domino.api.client.ModuleConfigurator;
import org.dominokit.domino.api.client.annotations.ClientModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name="BudgetsUI")
public class BudgetsUIClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(BudgetsUIClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing Budgets frontend UI module ...");
		new ModuleConfigurator().configureModule(new BudgetsUIModuleConfiguration());
	}
}
