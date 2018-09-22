package com.demo.domain;

import java.util.Optional;

public abstract class Stock {

    private String symbol;
    private Double lastDividend;
    private Double fixedDividend;
    private Double parValue;

    public Stock(String symbol, Double lastDividend, Double fixedDividend, Double parValue) {
        this.symbol = symbol;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setLastDividend(Double lastDividend) {
        this.lastDividend = lastDividend;
    }

    public void setFixedDividend(Double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public void setParValue(Double parValue) {
        this.parValue = parValue;
    }

    public Double getLastDividend() {
        return lastDividend;
    }

    public Double getFixedDividend() {
        return fixedDividend;
    }

    public Double getParValue() {
        return parValue;
    }

    public Optional<Double> calculatePERatio(Double marketPrice) {
        return Optional.of(getLastDividend()).filter(dividend -> dividend > 0).map(dividend -> marketPrice / dividend);
    }

    public abstract Optional<Double> calculateDividendYield(Double marketPrice);

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", lastDividend=" + lastDividend +
                ", fixedDividend=" + fixedDividend +
                ", parValue=" + parValue +
                '}';
    }

}
