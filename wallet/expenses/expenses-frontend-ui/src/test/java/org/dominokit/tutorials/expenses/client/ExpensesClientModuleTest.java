package org.dominokit.tutorials.expenses.client;

import org.dominokit.domino.api.client.annotations.ClientModule;
import org.dominokit.domino.test.api.client.annotations.PresenterSpy;
import org.dominokit.domino.test.api.client.annotations.TestConfig;
import org.dominokit.domino.test.api.client.DominoTestCase;
import org.dominokit.domino.test.api.client.DominoTestRunner;
import org.dominokit.tutorials.expenses.client.presenters.ExpensesPresenterSpy;
import org.dominokit.tutorials.expenses.client.presenters.ExpensesProxy;
import org.dominokit.tutorials.expenses.client.views.ExpensesViewSpy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DominoTestRunner.class)
@ClientModule(name = "TestExpenses")
@TestConfig(modules= {ExpensesModuleConfiguration.class})
public class ExpensesClientModuleTest extends DominoTestCase {

    @PresenterSpy(ExpensesProxy.class)
    ExpensesPresenterSpy presenterSpy;

    public ExpensesClientModuleTest() {
        super(new ExpensesClientModuleTest_Config());
    }

    @Test
    public void nothing() throws Exception {

    }

}
