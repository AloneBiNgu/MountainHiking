package com.mycompany.mountainhiking.services;

import java.util.regex.Pattern;

public class Validator {
    public boolean id(String id) { return Pattern.matches("^(SE|HE|DE|QE|CE)\\d{6}$", id); }
    public boolean name(String s) { String t = s.trim(); return t.length() >= 2 && t.length() <= 20; }
    public boolean phone(String p) { return Pattern.matches("^0\\d{9}$", p); }
    public boolean email(String e) { return Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$", e); }
}
