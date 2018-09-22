package com.demo;

import com.demo.dao.StockDaoImpl;
import com.demo.dao.TradeDaoImpl;
import com.demo.domain.CommonStock;
import com.demo.domain.PreferredStock;
import com.demo.domain.Stock;
import com.demo.domain.TradeIndicator;
import com.demo.service.StockService;
import com.demo.service.StockServiceImpl;
import com.demo.service.TradeService;
import com.demo.service.TradeServiceImpl;

import java.util.Optional;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class Main {

    private static StockService stockService = new StockServiceImpl(new StockDaoImpl());
    private static TradeService tradeService = new TradeServiceImpl(new TradeDaoImpl());
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeStocks();
        menu();
        int choice = getChoice();
        while (choice != 6) {
            switch (choice) {
                case 1: {
                    Stock stock = getStock();
                    double marketPrice = getMarketPrice();
                    printDividendYield(stock, marketPrice);
                    break;
                }
                case 2: {
                    Stock stock = getStock();
                    double marketPrice = getMarketPrice();
                    printPERatio(stock, marketPrice);
                    break;
                }
                case 3: {
                    Stock stock = getStock();
                    int quantity = getQuantity();
                    TradeIndicator indicator = getTradeIndicator();
                    double marketPrice = getMarketPrice();
                    saveTrade(stock, quantity, indicator, marketPrice);
                    break;
                }
                case 4: {
                    Stock stock = getStock();
                    printVolumeWeightStockPrice(stock);
                    break;
                }
                case 5: {
                    printGBCEAllShareIndex();
                    break;
                }
                case 6: {
                    print("Thanks you.");
                    scanner.close();
                    System.exit(0);
                    break;
                }
                default: {
                    System.err.println("Invalid Menu selected!");
                    break;
                }
            }
            menu();
            choice = getChoice();
        }
    }

    private static void initializeStocks() {
        stockService.add(new CommonStock("TEA", 0.0, null, 100.0));
        stockService.add(new CommonStock("POP", 0.08, null, 100.0));
        stockService.add(new CommonStock("ALE", 0.23, null, 60.0));
        stockService.add(new PreferredStock("GIN", 0.08, 0.02, 100.0));
        stockService.add(new CommonStock("JOE", 0.13, null, 250.0));
    }

    private static void menu() {
        System.out.println("Simple Stock Market.");
        System.out.println("Select Options:");
        System.out.println("1. Calculate dividend yield for a stock.");
        System.out.println("2. Calculate P/E value for a stock.");
        System.out.println("3. Record a trade for a stock.");
        System.out.println("4. Calculate volume weighted stock price for a stock in last 15 minutes.");
        System.out.println("5. Calculate All share index for GBCE(Global Beverage Corporate Exchange).");
        System.out.println("6. Exit.");
    }

    private static int getChoice() {
        System.out.println("Enter choice:");
        String input = scanner.nextLine();
        Integer choice = null;
        while (isNull(choice)) {
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Invalid menu option selected!");
                menu();
                System.out.println("Enter choice:");
                input = scanner.nextLine();
            }
        }
        return choice;
    }

    private static Stock getStock() {
        System.out.println("Please enter stock symbol (TEA|POP|ALE|GIN|JOE):");
        String stockSymbol = scanner.nextLine();
        Optional<Stock> stock = stockService.getBy(stockSymbol);
        while (!stock.isPresent()) {
            System.err.println("Invalid stock symbol entered!");
            System.out.println("Please enter stock symbol (TEA|POP|ALE|GIN|JOE):");
            stockSymbol = scanner.nextLine();
            stock = stockService.getBy(stockSymbol);
        }
        return stock.get();
    }

    private static double getMarketPrice() {
        System.out.println("Please enter market price");
        String input = scanner.nextLine();
        Double marketPrice = null;
        while (isNull(marketPrice)) {
            try {
                marketPrice = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.err.println("Invalid market price!");
                System.out.println("Please enter market price");
                input = scanner.nextLine();
            }
        }
        return marketPrice;
    }

    private static void printDividendYield(Stock stock, double marketPrice) {
        Optional<Double> dividendYield = stock.calculateDividendYield(marketPrice);
        print(dividendYield.map(y -> "Dividend Yield = " + y).orElse("Dividend yield is not defined."));
    }

    private static void printPERatio(Stock stock, double marketPrice) {
        Optional<Double> peRatio = stock.calculatePERatio(marketPrice);
        print(peRatio.map(r -> "P/E ratio = " + r).orElse("P/E ratio is not defined."));
    }

    private static int getQuantity() {
        System.out.println("Please enter quantity of stock:");
        String input = scanner.nextLine();
        Integer quantity = null;
        while (isNull(quantity)) {
            try {
                quantity = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Invalid menu option selected!");
                System.out.println("Please enter quantity of stock:");
                input = scanner.nextLine();
            }
        }
        return quantity;
    }

    private static TradeIndicator getTradeIndicator() {
        System.out.println("Please enter trade indicator(SELL/BUY):");
        String input = scanner.nextLine();
        TradeIndicator indicator = null;
        while (isNull(indicator)) {
            try {
                indicator = TradeIndicator.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid trade indicator entered! Please enter either SELL or BUY.");
            }
        }
        return indicator;
    }

    private static void saveTrade(Stock stock, int quantity, TradeIndicator indicator, double marketPrice) {
        tradeService.recordTrade(stock, quantity, indicator, marketPrice);
        System.out.println("Trade record saved successfully.");
    }

    private static void printVolumeWeightStockPrice(Stock stock) {
        Optional<Double> volumeWeightedStockPrice = tradeService.calculateVolumeWeightedStockPriceFor(stock, 15);
        print(volumeWeightedStockPrice
                .map(v -> "Volume weight stock price = " + v)
                .orElse("Volume weighted stock price is not defined for given stock.")
        );
    }

    private static void printGBCEAllShareIndex() {
        Optional<Double> allShareIndex = tradeService.calculateAllShareIndex();
        print(allShareIndex.map(i -> "GBCE All share index = " + i).orElse("GBCE All share index is not defined."));
    }

    private static void print(String message) {
        System.out.println(message);
    }

}
