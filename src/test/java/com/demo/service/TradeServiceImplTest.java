package com.demo.service;

import com.demo.dao.TradeDao;
import com.demo.domain.CommonStock;
import com.demo.domain.Stock;
import com.demo.domain.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.demo.domain.TradeIndicator.SELL;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceImplTest {

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    private TradeDao tradeDao;

    private LocalDateTime timeToUse = LocalDateTime.now();
    private static final int QUANTITY = 5;
    private static final double PRICE = 5.0;
    private Stock stock = new CommonStock("ABC", 0.01, 0.01, 100.0);
    private Trade trade1 = new Trade(stock, timeToUse, QUANTITY, PRICE, SELL);


    @Test
    public void recordTradeShouldSaveRecordInDb() {
        ArgumentCaptor<Trade> captor = ArgumentCaptor.forClass(Trade.class);
        doNothing().when(tradeDao).add(any(Trade.class));
        tradeService.recordTrade(stock, QUANTITY, SELL, PRICE);
        verify(tradeDao).add(captor.capture());
        Trade captorValue = captor.getValue();
        assertThat(captorValue.getPrice()).isEqualTo(PRICE);
        assertThat(captorValue.getQuantity()).isEqualTo(QUANTITY);
        assertThat(captorValue.getStock()).isEqualTo(stock);
        assertThat(captorValue.getIndicator()).isEqualTo(SELL);
    }

    @Test
    public void calculateVolumeWeightedStockPriceForShouldReturnVWSPIfTradesHappenedWithinGivenTime() {
        when(tradeDao.getTradesFor(eq(stock), eq(15))).thenReturn(asList(trade1, trade1));
        Optional<Double> volWeightStockPrice = tradeService.calculateVolumeWeightedStockPriceFor(stock, 15);
        assertThat(volWeightStockPrice).isNotEmpty();
        assertThat(volWeightStockPrice.get()).isPositive();
        assertThat(volWeightStockPrice.get()).isEqualTo(5.0);
    }

    @Test
    public void calculateVolumeWeightedStockPriceForShouldReturnNothingIfNoTradesHappenedWithinGivenTime() {
        when(tradeDao.getTradesFor(eq(stock), eq(15))).thenReturn(emptyList());
        Optional<Double> volWeightStockPrice = tradeService.calculateVolumeWeightedStockPriceFor(stock, 15);
        assertThat(volWeightStockPrice).isEmpty();
    }

    @Test
    public void calculateAllShareIndexShouldReturnShareIndexIfTradesHappened() {
        when(tradeDao.getAllTrades()).thenReturn(asList(trade1, trade1));
        Optional<Double> allShareIndex = tradeService.calculateAllShareIndex();
        assertThat(allShareIndex).isNotEmpty();
        assertThat(allShareIndex.get()).isEqualTo(5.0);
    }

    @Test
    public void calculateAllShareIndexShouldReturnNothingIfNotTradesHappened() {
        when(tradeDao.getAllTrades()).thenReturn(emptyList());
        Optional<Double> allShareIndex = tradeService.calculateAllShareIndex();
        assertThat(allShareIndex).isEmpty();
    }

}
