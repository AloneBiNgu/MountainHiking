package com.mycompany.mountainhiking.enums;

import java.util.Set;


public enum Telco {
    VIETTEL("Viettel", Set.of("086","096","097","098","032","033","034","035","036","037","038","039"), 0.35),
    VNPT("VNPT/VinaPhone", Set.of("088","091","094","081","082","083","084","085"), 0.35),
    MOBIFONE("MobiFone", Set.of("089","090","093","070","079","077","076","078"), 0.00),
    VIETNAMMOBILE("Vietnamobile", Set.of("092","056","058"), 0.00),
    GMOBILE("Gmobile", Set.of("099","059"), 0.00),
    UNKNOWN("Unknown", Set.of(), 0.00);

    private final String displayName;
    private final Set<String> prefixes;
    private final double discountRate;

    Telco(String displayName, Set<String> prefixes, double discountRate) {
        this.displayName = displayName;
        this.prefixes = prefixes;
        this.discountRate = discountRate;
    }

    public String displayName() { return displayName; }
    public Set<String> prefixes() { return prefixes; }
    public double discountRate() { return discountRate; }
    public static Telco fromPhone(String phone) {
        if (phone == null || phone.length() < 3) return UNKNOWN;
        String p3 = phone.substring(0, 3);
        for (Telco t : values()) {
            if (t == UNKNOWN) continue;
            if (t.prefixes.contains(p3)) return t;
        }
        return UNKNOWN;
    }
}
