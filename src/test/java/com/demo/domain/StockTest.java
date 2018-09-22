package com.demo.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StockTest {

    private static final String STOCK_SYMBOL_1 = "SYM1";

    private Stock commonStock;
    private Stock preferredStock;

    @Before
    public void beforeEach() {
        commonStock = new CommonStock(STOCK_SYMBOL_1, 10.0, null, 100.0);
        preferredStock = new PreferredStock(STOCK_SYMBOL_1, 10.0, 2.0, 100.0);
    }

    @Test
    public void calculatePERatioForCommonStockIsMarketPriceByDividend() {
        Optional<Double> peRatio = commonStock.calculatePERatio(10.0);
        assertThat(peRatio).isNotEmpty();
        assertThat(peRatio.get()).isEqualTo(1.0);
    }

    @Test
    public void calculatePERatioForPreferredStockIsMarketPriceByDividend() {
        Optional<Double> peRatio = preferredStock.calculatePERatio(10.0);
        assertThat(peRatio).isNotEmpty();
        assertThat(peRatio.get()).isEqualTo(1.0);
    }

    @Test
    public void calculatePERatioForAnyStockIsMarketPriceByDividend() {
        Optional<Double> commonPEeRatio = commonStock.calculatePERatio(10.0);
        Optional<Double> preferredPERatio = preferredStock.calculatePERatio(10.0);
        assertThat(commonPEeRatio).isNotEmpty();
        assertThat(preferredPERatio).isNotEmpty();
        assertThat(commonPEeRatio).isEqualTo(preferredPERatio);
    }

    @Test
    public void calculatePERatioIsNotDefinedForAnyStockWithZeroDividend() {
        commonStock.setLastDividend(0.0);
        preferredStock.setLastDividend(0.0);
        Optional<Double> commonPEeRatio = commonStock.calculatePERatio(10.0);
        Optional<Double> preferredPERatio = preferredStock.calculatePERatio(10.0);
        assertThat(commonPEeRatio).isEmpty();
        assertThat(preferredPERatio).isEmpty();
        assertThat(commonPEeRatio).isEqualTo(preferredPERatio);
    }

    @Test
    public void calculateDividendYieldForCommonStockIsLastDividendByMarketPrice() {
        Optional<Double> dividendYield = commonStock.calculateDividendYield(10.0);
        assertThat(dividendYield).isNotEmpty();
        assertThat(dividendYield.get()).isEqualTo(1);
    }

    @Test
    public void calculateDividendYieldForCommonStockIsUndefinedForZeroMarketPrice() {
        Optional<Double> dividendYield = commonStock.calculateDividendYield(0.0);
        assertThat(dividendYield).isEmpty();
    }

    @Test
    public void calculateDividendYieldForPreferredStockIsUndefinedForZeroMarketPrice() {
        Optional<Double> dividendYield = preferredStock.calculateDividendYield(0.0);
        assertThat(dividendYield).isEmpty();
    }

}
