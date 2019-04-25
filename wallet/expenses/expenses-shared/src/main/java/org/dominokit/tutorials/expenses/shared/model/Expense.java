package org.dominokit.tutorials.expenses.shared.model;

import org.dominokit.jacksonapt.annotation.JSONMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@JSONMapper
public class Expense {

    private String description;
    private Date date;
    private BigDecimal amount;
    private String budget;
    private String id;

    public Expense() {
        this.id = UUID.randomUUID().toString();
    }

    public Expense(String description, Date date, BigDecimal amount, String budget) {
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.budget = budget;
        this.id = UUID.randomUUID().toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(description, expense.description) &&
                Objects.equals(date, expense.date) &&
                Objects.equals(amount, expense.amount) &&
                Objects.equals(budget, expense.budget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, date, amount, budget);
    }
}
