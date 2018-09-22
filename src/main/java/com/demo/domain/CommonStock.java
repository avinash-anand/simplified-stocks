package com.demo.domain;

import java.util.Optional;

public class CommonStock extends Stock {

    public CommonStock(String symbol, Double lastDividend, Double fixedDividend, Double parValue) {
        super(symbol, lastDividend, fixedDividend, parValue);
    }

    @Override
    public Optional<Double> calculateDividendYield(Double marketPrice) {
        return Optional.of(marketPrice).filter(price -> price > 0).map(price -> getLastDividend() / price);
    }

}
