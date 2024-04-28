package com.shilaeva.entities;

import java.math.BigDecimal;

public class BankAccount {
    private long id;
    private long userId;
    private BigDecimal amount;

    public BankAccount(long userId) {
        this.userId = userId;
        this.amount = BigDecimal.ZERO;
    }

    public BankAccount(long id, long userId, BigDecimal money) {
        this.id = id;
        this.userId =userId;
        this.amount = money;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUser(long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
