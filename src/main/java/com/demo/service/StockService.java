package com.demo.service;

import com.demo.domain.Stock;

import java.util.Optional;

public interface StockService {

    public void add(Stock stock);

    public Optional<Stock> getBy(String symbol);

}
