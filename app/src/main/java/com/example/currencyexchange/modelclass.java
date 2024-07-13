package com.example.currencyexchange;

import java.util.Map;

public class modelclass {
    private Map<String, Double> data;

    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CurrencyExchangeRates{" +
                "data=" + data +
                '}';
    }
}

