package org.dominokit.tutorials.budgets.server.resources;

import org.dominokit.tutorials.budgets.shared.model.Budget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetsMapRepository {

    public static final BudgetsMapRepository INSTANCE = new BudgetsMapRepository();
    private static final Map<String, Budget> budgetsMap = new HashMap<>();

    static {
        Budget budget1 = new Budget("clothes", "Salary", BigDecimal.valueOf(100));
        budgetsMap.put(budget1.getName(), budget1);

        Budget budget2 = new Budget("Food", "Shop", BigDecimal.valueOf(150));
        budgetsMap.put(budget2.getName(), budget2);
    }

    private BudgetsMapRepository() {
    }

    public List<Budget> listAll() {
        return new ArrayList<>(budgetsMap.values());
    }

    public void save(Budget budget) {
        budgetsMap.put(budget.getName(), budget);
    }

    public Budget getByName(String name) {
        return budgetsMap.get(name);
    }
}
