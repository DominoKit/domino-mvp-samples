package org.dominokit.tutorials.income.shared.model;

import org.dominokit.jacksonapt.annotation.JSONMapper;

import java.math.BigDecimal;
import java.util.List;

@JSONMapper
public class Income {

    private String title;
    private BigDecimal amount;
    private float tax;
    private List<Deduction> deductions;
    private Schecule schedule;
    private BigDecimal remaining;
    private BigDecimal spent;

    public Income() {
    }

    public Income(String title, BigDecimal amount, float tax, List<Deduction> deductions, Schecule schedule) {
        this.title = title;
        this.amount = amount;
        this.tax = tax;
        this.deductions = deductions;
        this.schedule = schedule;
        this.remaining = amount;
        this.spent = BigDecimal.ZERO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public List<Deduction> getDeductions() {
        return deductions;
    }

    public void setDeductions(List<Deduction> deductions) {
        this.deductions = deductions;
    }

    public Schecule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schecule schedule) {
        this.schedule = schedule;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }
}
