package com.demo.dao;

import com.demo.domain.Stock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class StockDaoImpl implements StockDao {

    private Map<String, Stock> stockDb = new HashMap<>();

    @Override
    public void add(Stock stock) {
        stockDb.put(stock.getSymbol(), stock);
    }

    @Override
    public Optional<Stock> getBy(String symbol) {
        return ofNullable(stockDb.get(symbol));
    }
}
