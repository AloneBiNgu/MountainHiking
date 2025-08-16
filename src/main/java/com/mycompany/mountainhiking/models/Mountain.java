package com.mycompany.mountainhiking.models;
import java.io.Serializable;

public class Mountain implements Serializable {
    public final String code, name, location;
    public Mountain(String code, String name, String location){
        this.code = code;
        this.name = name;
        this.location = location;
    }
}
