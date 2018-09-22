package com.demo.domain;

import java.time.LocalDateTime;

public class Trade {

    private final Stock stock;
    private final LocalDateTime timestamp;
    private final int quantity;
    private final double price;
    private final TradeIndicator indicator;

    public Trade(Stock stock, LocalDateTime timestamp, int quantity, double price, TradeIndicator indicator) {
        this.stock = stock;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.price = price;
        this.indicator = indicator;
    }

    public Stock getStock() {
        return stock;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public TradeIndicator getIndicator() {
        return indicator;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "stock=" + stock +
                ", timestamp=" + timestamp +
                ", quantity=" + quantity +
                ", price=" + price +
                ", indicator=" + indicator +
                '}';
    }
}
