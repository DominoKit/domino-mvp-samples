package org.dominokit.tutorials.income.client;

import com.google.gwt.core.client.EntryPoint;
import org.dominokit.domino.api.client.ModuleConfigurator;
import org.dominokit.domino.api.client.annotations.ClientModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name="Income")
public class IncomeClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(IncomeClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing Income frontend module ...");
		new ModuleConfigurator().configureModule(new IncomeModuleConfiguration());
	}
}
