package com.smartmeter.config;

import java.util.LinkedHashMap;
import java.util.Map;

public class TariffConfig {

    private static final Map<Integer, Double> TARIFF_TABLE = new LinkedHashMap<>();

    static {
        TARIFF_TABLE.put(10, 1.0);
        TARIFF_TABLE.put(20, 2.0);
        TARIFF_TABLE.put(30, 3.0);
        TARIFF_TABLE.put(50, 5.0);
    }

    public static double calculateCost(double kwh) {
        for (Map.Entry<Integer, Double> entry : TARIFF_TABLE.entrySet()) {
            if (kwh <= entry.getKey()) {
                return entry.getValue();
            }
        }
        return kwh * 0.2;
    }

    public static Map<Integer, Double> getTariffTable() {
        return TARIFF_TABLE;
    }
}
