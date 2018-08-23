package com.n.sell.utils;

public class MathUtil {

    private static Double MONEY_RANGE = 0.001;

    public static Boolean equals(Double d1, Double d2){
        return Math.abs(d1 - d2 ) < MONEY_RANGE;
    }
}
