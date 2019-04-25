package org.dominokit.tutorials.income.server.resources;

import org.dominokit.tutorials.income.shared.model.Income;
import org.dominokit.tutorials.income.shared.model.Schecule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomeMapRepository {

    public static final IncomeMapRepository INSTANCE = new IncomeMapRepository();
    private static final Map<String, Income> incomesMap = new HashMap<>();

    static {
        Income income1 = new Income("Salary", BigDecimal.valueOf(1000), 0.2f, new ArrayList<>(), Schecule.monthly);
        incomesMap.put(income1.getTitle(), income1);

        Income income2 = new Income("Shop", BigDecimal.valueOf(500), 0.1f, new ArrayList<>(), Schecule.weekly);
        incomesMap.put(income2.getTitle(), income2);
    }

    private IncomeMapRepository() {
    }

    public List<Income> listAll() {
        return new ArrayList<>(incomesMap.values());
    }

    public void save(Income income) {
        incomesMap.put(income.getTitle(), income);
    }

    public Income getByTitle(String title) {
        return incomesMap.get(title);
    }
}
