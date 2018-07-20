package com.piolob.feecalculator.model;

import com.univocity.parsers.annotations.Parsed;

import java.math.BigDecimal;
import java.util.List;

public class CustomerFee {

    public CustomerFee() {
    }

    @Parsed(index = 0)
    private String id;

    @Parsed(index = 1)
    private String currency;

    @Parsed(index = 2)
    private BigDecimal fee;

    public CustomerFee(String id, String currency, BigDecimal fee) {
        this.id = id;
        this.currency = currency;
        this.fee = fee;
    }

    public CustomerFee(List<Object> objects) {
    }

    public String getCurrency() {
        return currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
