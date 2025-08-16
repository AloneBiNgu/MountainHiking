package com.mycompany.mountainhiking.models;
import java.io.Serializable;

public class Registration implements Serializable {
    public Student student; public String mountainCode; public long tuitionFee;
    public Registration(Student studen, String mountainCode, long fee){
        this.student = studen;
        this.mountainCode = mountainCode;
        this.tuitionFee = fee;
    }
}
