package com.demo.dao;

import com.demo.domain.CommonStock;
import com.demo.domain.Stock;
import com.demo.domain.Trade;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.demo.domain.TradeIndicator.SELL;
import static org.assertj.core.api.Assertions.assertThat;

public class TradeDaoImplTest {

    private LocalDateTime timeToUse = LocalDateTime.now();
    private static final int QUANTITY = 5;
    private static final double PRICE = 5.0;
    private Stock stock = new CommonStock("ABC", 0.01, 0.01, 100.0);
    private Trade trade1 = new Trade(stock, timeToUse, QUANTITY, PRICE, SELL);

    private TradeDao tradeDao;

    @Before
    public void beforeEach() {
        tradeDao = new TradeDaoImpl();
    }

    @Test
    public void addShouldStoreTradeInMemory() {
        tradeDao.add(trade1);
        assertThat(tradeDao.getAllTrades()).isNotEmpty();
        assertThat(tradeDao.getAllTrades()).hasSize(1);
        assertThat(tradeDao.getAllTrades().get(0)).isEqualTo(trade1);
    }

    @Test
    public void getTradesForShouldReturnTradesForAStockWithinGivenTime() {
        tradeDao.add(trade1);
        tradeDao.add(new Trade(stock, timeToUse.minusMinutes(2), QUANTITY, PRICE, SELL));
        List<Trade> tradesFor1Min = tradeDao.getTradesFor(stock, 1);
        assertThat(tradesFor1Min).isNotEmpty();
        assertThat(tradesFor1Min).hasSize(1);
        assertThat(tradesFor1Min.get(0)).isEqualTo(trade1);
    }

    @Test
    public void getTradesForShouldReturnEmptyIfNoTradesArePresentForAStockWithinGivenTime() {
        tradeDao.add(new Trade(stock, timeToUse.minusMinutes(2), QUANTITY, PRICE, SELL));
        List<Trade> tradesFor1Min = tradeDao.getTradesFor(stock, 1);
        assertThat(tradesFor1Min).isEmpty();
    }

    @Test
    public void getAllTradesShouldReturnAddedTrades() {
        tradeDao.add(trade1);
        assertThat(tradeDao.getAllTrades()).isNotEmpty();
        assertThat(tradeDao.getAllTrades()).hasSize(1);
    }

    @Test
    public void getAllTradesShouldReturnEmptyIfNoTradesDone() {
        assertThat(tradeDao.getAllTrades()).isEmpty();
    }

}
