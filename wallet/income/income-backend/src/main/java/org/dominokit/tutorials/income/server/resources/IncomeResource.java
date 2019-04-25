package org.dominokit.tutorials.income.server.resources;

import org.dominokit.tutorials.income.shared.model.Deduction;
import org.dominokit.tutorials.income.shared.model.Income;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/service")
public class IncomeResource {

    @Path("income")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Income> loadIncomes() {
        return IncomeMapRepository.INSTANCE.listAll();
    }

    @Path("income")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public void create(Income income) {
        IncomeMapRepository.INSTANCE.save(income);
    }

    @Path("income/{title}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Income getIncome(@PathParam("title") String title){
        return IncomeMapRepository.INSTANCE.getByTitle(title);
    }

    @Path("income/{title}/deductions")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Deduction> getIncomeDeductions(@PathParam("title") String title){
        return IncomeMapRepository.INSTANCE.getByTitle(title).getDeductions();
    }
}