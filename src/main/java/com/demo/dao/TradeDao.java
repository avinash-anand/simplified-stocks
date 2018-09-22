package com.demo.dao;

import com.demo.domain.Stock;
import com.demo.domain.Trade;

import java.util.List;

public interface TradeDao {

    public void add(Trade trade);

    public List<Trade> getTradesFor(Stock stock, int minutes);

    public List<Trade> getAllTrades();

}
