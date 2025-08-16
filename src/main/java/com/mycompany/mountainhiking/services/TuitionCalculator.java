package com.mycompany.mountainhiking.services;

public class TuitionCalculator {
    public static final long BASE = 6_000_000L;
    private final TelcoPolicy policy;

    public TuitionCalculator(TelcoPolicy policy) {
        this.policy = policy;
    }
    public long calc(String phone) {
        double rate = policy.discountRate(phone);
        return Math.round(BASE * (1.0 - rate));
    }
}
