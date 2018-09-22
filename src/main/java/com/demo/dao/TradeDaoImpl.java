package com.demo.dao;

import com.demo.domain.Stock;
import com.demo.domain.Trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class TradeDaoImpl implements TradeDao {

    private Map<String, List<Trade>> tradeDb = new HashMap<>();

    @Override
    public void add(Trade trade) {
        List<Trade> existingTradesForStock = ofNullable(tradeDb.get(trade.getStock().getSymbol())).orElse(new ArrayList<>());
        existingTradesForStock.add(trade);
        tradeDb.put(trade.getStock().getSymbol(), existingTradesForStock);
    }

    @Override
    public List<Trade> getTradesFor(Stock stock, int minutes) {
        List<Trade> allTradesOfStock = ofNullable(tradeDb.get(stock.getSymbol())).orElse(new ArrayList<>());
        return allTradesOfStock.stream().filter(trade -> hasTimeWithin(trade, minutes)).collect(toList());
    }

    @Override
    public List<Trade> getAllTrades() {
        return tradeDb.values().stream().flatMap(List::stream).collect(toList());
    }

    private boolean hasTimeWithin(Trade trade, int minutes) {
        LocalDateTime tradeTimeSince = LocalDateTime.now().minusMinutes(minutes);
        return !trade.getTimestamp().isBefore(tradeTimeSince);
    }

}
