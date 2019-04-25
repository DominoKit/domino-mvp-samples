package org.dominokit.tutorials.income.shared.services;

import org.dominokit.domino.api.shared.request.ArrayResponse;
import org.dominokit.domino.api.shared.request.Response;
import org.dominokit.domino.api.shared.request.VoidResponse;
import org.dominokit.domino.api.shared.request.service.annotations.RequestBody;
import org.dominokit.domino.api.shared.request.service.annotations.RequestFactory;
import org.dominokit.tutorials.income.shared.model.Deduction;
import org.dominokit.tutorials.income.shared.model.Income;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


@RequestFactory
public interface IncomeService {

    @Path("income")
    @GET
    Response<ArrayResponse<Income>> listAll();

    @Path("income")
    @POST
    Response<VoidResponse> create(@RequestBody Income income);

    @Path("income/:title")
    @GET
    Response<Income> getIncome(String title);

    @Path("income/:title/deductions")
    @GET
    Response<ArrayResponse<Deduction>> getIncomeDeductions(String title);
}
