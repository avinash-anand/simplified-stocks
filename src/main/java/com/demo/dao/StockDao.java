package com.demo.dao;

import com.demo.domain.Stock;

import java.util.Optional;

public interface StockDao {

    public void add(Stock stock);

    public Optional<Stock> getBy(String symbol);

}
