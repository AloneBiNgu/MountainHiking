package com.mycompany.mountainhiking.services;

import com.mycompany.mountainhiking.enums.Telco;

public class TelcoPolicy {
    public boolean discount(String phone) {
        return Telco.fromPhone(phone).discountRate() > 0.0;
    }
    public double discountRate(String phone) {
        return Telco.fromPhone(phone).discountRate();
    }
    public String carrierName(String phone) {
        return Telco.fromPhone(phone).displayName();
    }
}
