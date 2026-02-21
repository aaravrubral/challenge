package com.blackrock.challenge.util;

public class FinanceUtils {
    public static double ceiling(double amount) {
        return Math.ceil(amount / 100.0) * 100;
    }
}
