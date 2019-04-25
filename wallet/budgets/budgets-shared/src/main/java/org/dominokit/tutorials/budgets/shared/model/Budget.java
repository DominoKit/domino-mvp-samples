package org.dominokit.tutorials.budgets.shared.model;

import org.dominokit.jacksonapt.annotation.JSONMapper;

import java.math.BigDecimal;

@JSONMapper
public class Budget {

    private String name;
    private String income;
    private BigDecimal amount;
    private BigDecimal spent;
    private BigDecimal remaining;

    public Budget() {
    }

    public Budget(String name, String income, BigDecimal amount) {
        this.name = name;
        this.income = income;
        this.amount = amount;
        this.remaining = amount;
        this.spent = BigDecimal.ZERO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }
}
