package org.dominokit.tutorials.budgets.shared.services;

import org.dominokit.domino.api.shared.request.ArrayResponse;
import org.dominokit.domino.api.shared.request.Response;
import org.dominokit.domino.api.shared.request.VoidResponse;
import org.dominokit.domino.api.shared.request.service.annotations.RequestBody;
import org.dominokit.domino.api.shared.request.service.annotations.RequestFactory;
import org.dominokit.tutorials.budgets.shared.model.Budget;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RequestFactory
public interface BudgetsService {
    @Path("budget")
    @GET
    Response<ArrayResponse<Budget>> listAll();

    @Path("budget/:name")
    @GET
    Response<Budget> getBudgetByName(String name);

    @Path("budget?income=:title")
    @GET
    Response<ArrayResponse<Budget>> getBudgetsByIncome(String title);

    @Path("budget")
    @POST
    Response<VoidResponse> save(@RequestBody Budget budget);
}
