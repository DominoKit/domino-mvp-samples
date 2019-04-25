package org.dominokit.tutorials.budgets.server.resources;


import org.dominokit.tutorials.budgets.shared.model.Budget;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Path("/service")
public class BudgetsResource {

    @Path("budget")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Budget> getBudgets(@QueryParam("income") String title) {
        if(nonNull(title) && !title.isEmpty()){
            return BudgetsMapRepository.INSTANCE.listAll()
                    .stream()
                    .filter(budget -> budget.getIncome().equals(title))
                    .collect(Collectors.toList());
        }else{
            return BudgetsMapRepository.INSTANCE.listAll();
        }
    }

    @Path("budget/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Budget getBudgetByName(@PathParam("name") String name) {
        return BudgetsMapRepository.INSTANCE.getByName(name);
    }

    @Path("budget")
    @POST
    public Response saveBudget(Budget budget) {
        BudgetsMapRepository.INSTANCE.save(budget);
        return Response.status(Response.Status.OK).build();
    }
}