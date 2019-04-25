package org.dominokit.tutorials.expenses.server.resources;

import org.dominokit.domino.api.server.plugins.ResourceContext;
import org.dominokit.tutorials.expenses.shared.model.Expense;
import org.dominokit.tutorials.expenses.shared.model.Expense_MapperImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/service")
public class ExpensesResource {

    @Path("expenses")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Expense> listAll() {
        return ExpensesListRepository.INSTANCE.listAll();
    }

    @Path("expenses")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response save(Expense expense, @Context ResourceContext context) {
        ExpensesListRepository.INSTANCE.save(expense);

        context.getVertxContext()
                .vertx()
                .eventBus()
                .publish("expense-added", Expense_MapperImpl.INSTANCE.write(expense));

        return Response.status(Response.Status.OK).build();
    }

    @Path("expenses/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") String id, @Context ResourceContext context){
        if(ExpensesListRepository.contains(id)) {
            Expense deletedExpense = ExpensesListRepository.INSTANCE.delete(id);

            context.getVertxContext()
                    .vertx()
                    .eventBus()
                    .publish("expense-removed", Expense_MapperImpl.INSTANCE.write(deletedExpense));

            return Response.status(Response.Status.OK).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}