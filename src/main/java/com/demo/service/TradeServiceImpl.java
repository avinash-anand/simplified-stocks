package com.demo.service;

import com.demo.dao.TradeDao;
import com.demo.domain.Stock;
import com.demo.domain.Trade;
import com.demo.domain.TradeIndicator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class TradeServiceImpl implements TradeService {

    private TradeDao tradeDao;

    public TradeServiceImpl(TradeDao tradeDao) {
        this.tradeDao = tradeDao;
    }

    @Override
    public void recordTrade(Stock stock, int quantity, TradeIndicator indicator, double price) {
        Trade trade = new Trade(stock, LocalDateTime.now(), quantity, price, indicator);
        tradeDao.add(trade);
    }

    @Override
    public Optional<Double> calculateVolumeWeightedStockPriceFor(Stock stock, int minutes) {
        List<Trade> trades = tradeDao.getTradesFor(stock, minutes);
        double sumOfPricesForQuantity = trades.stream().mapToDouble(trade -> trade.getPrice() * trade.getQuantity()).sum();
        int sumOfQuantity = trades.stream().mapToInt(Trade::getQuantity).sum();
        return Optional.of(sumOfQuantity).filter(value -> value > 0).map(totalQuantity -> sumOfPricesForQuantity / totalQuantity);
    }

    @Override
    public Optional<Double> calculateAllShareIndex() {
        List<Trade> allTrades = tradeDao.getAllTrades();
        OptionalDouble productOfAllTradeMaybe = allTrades.stream().mapToDouble(Trade::getPrice).reduce((a, b) -> a * b);
        if (productOfAllTradeMaybe.isPresent()) {
            return Optional.of(productOfAllTradeMaybe.getAsDouble()).map(data -> Math.pow(data, 1D / allTrades.size()));
        }
        return Optional.empty();
    }

}
