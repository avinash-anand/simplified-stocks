package com.demo.service;

import com.demo.dao.StockDao;
import com.demo.domain.Stock;

import java.util.Optional;

public class StockServiceImpl implements StockService {

    private final StockDao stockDao;

    public StockServiceImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public void add(Stock stock) {
        stockDao.add(stock);
    }

    @Override
    public Optional<Stock> getBy(String symbol) {
        return stockDao.getBy(symbol);
    }

}
