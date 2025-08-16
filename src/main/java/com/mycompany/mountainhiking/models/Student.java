package com.mycompany.mountainhiking.models;
import java.io.Serializable;

public class Student implements Serializable {
    private String id, name, phone, email;
    public Student(String id, String name, String phone, String email){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    public String getId(){ return id; }
    public String getName(){ return name; }
    public String getPhone(){ return phone; }
    public String getEmail(){ return email; }
    public void setName(String name){ name = name; }
    public void setPhone(String phone){ phone = phone; }
    public void setEmail(String email){ email = email; }
    public String campus(){ return id.substring(0,2); } // SE/HE/DE/QE/CE
}
