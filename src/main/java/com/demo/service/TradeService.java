package com.demo.service;

import com.demo.domain.Stock;
import com.demo.domain.TradeIndicator;

import java.util.Optional;

public interface TradeService {

    public void recordTrade(Stock stock, int quantity, TradeIndicator indicator, double price);

    public Optional<Double> calculateVolumeWeightedStockPriceFor(Stock stock, int minutes);

    public Optional<Double> calculateAllShareIndex();

}
