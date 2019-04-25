package org.dominokit.tutorials.expenses.shared.services;

import org.dominokit.domino.api.shared.request.ArrayResponse;
import org.dominokit.domino.api.shared.request.Response;
import org.dominokit.domino.api.shared.request.VoidResponse;
import org.dominokit.domino.api.shared.request.service.annotations.RequestBody;
import org.dominokit.domino.api.shared.request.service.annotations.RequestFactory;
import org.dominokit.tutorials.expenses.shared.model.Expense;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RequestFactory
public interface ExpensesService {
    @Path("expenses")
    @GET
    Response<ArrayResponse<Expense>> listAll();

    @Path("expenses")
    @POST
    Response<VoidResponse> save(@RequestBody Expense expense);

    @Path("expenses/:id")
    @DELETE
    Response<VoidResponse> delete(String id);
}
