package org.dominokit.tutorials.expenses.client;

import com.google.gwt.core.client.EntryPoint;
import org.dominokit.domino.api.client.ModuleConfigurator;
import org.dominokit.domino.api.client.annotations.ClientModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name="ExpensesUI")
public class ExpensesUIClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpensesUIClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing Expenses frontend UI module ...");
		new ModuleConfigurator().configureModule(new ExpensesUIModuleConfiguration());
	}
}
