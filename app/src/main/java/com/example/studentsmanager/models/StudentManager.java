package com.example.studentsmanager.models;

public class   StudentManager {
    private String id;
    private  String name;
    private  String day;
    private  String email;
    private  String idClass;

    public StudentManager() {
    }

    public StudentManager(String id, String name, String day, String email, String idClass) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.email = email;
        this.idClass = idClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    @Override
    public String toString() {
        return getId()+" | "+getName()+" | "+getDay()+" | "+getEmail()+" | "+getIdClass();
    }
}

