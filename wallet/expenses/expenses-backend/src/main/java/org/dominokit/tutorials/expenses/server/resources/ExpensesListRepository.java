package org.dominokit.tutorials.expenses.server.resources;

import org.dominokit.tutorials.expenses.shared.model.Expense;

import java.math.BigDecimal;
import java.util.*;

public class ExpensesListRepository {

    public static final ExpensesListRepository INSTANCE = new ExpensesListRepository();
    private static final Map<String, Expense> expensesList = new HashMap<>();

    static {
        Expense expense1 = new Expense("t-shirt", new Date(), BigDecimal.valueOf(25), "clothes");
        expensesList.put(expense1.getId(), expense1);

        Expense expense2 = new Expense("burgers", new Date(), BigDecimal.valueOf(10), "Food");
        expensesList.put(expense2.getId(), expense2);
    }

    private ExpensesListRepository() {
    }

    public static boolean contains(String id) {
        return expensesList.containsKey(id);
    }

    public List<Expense> listAll() {
        return new ArrayList<>(expensesList.values());
    }

    public void save(Expense expense) {
        expensesList.put(expense.getId(), expense);
    }

    public Expense delete(String id) {
        Expense  expense = expensesList.get(id);
        expensesList.remove(id);
        return expense;
    }
}
