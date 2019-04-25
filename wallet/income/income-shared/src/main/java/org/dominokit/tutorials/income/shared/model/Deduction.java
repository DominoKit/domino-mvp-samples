package org.dominokit.tutorials.income.shared.model;

import org.dominokit.jacksonapt.annotation.JSONMapper;

import java.math.BigDecimal;

@JSONMapper
public class Deduction {

    private String description;
    private BigDecimal amount;

    public Deduction() {
    }

    public Deduction(String description, BigDecimal amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
