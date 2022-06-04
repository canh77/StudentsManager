package com.example.studentsmanager.models;

//lớp classManager sẽ bị lớp DAO quản lí
public class ClassManager {
    private  String id;
    private  String name;

    public ClassManager() {
    }

    public ClassManager(String id, String name) {
        this.id = id;
        this.name = name;
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

    //khai báo toString thì dữu liệu sẽ tạo ra rõ ràng hơn <> object
    @Override
    public String toString() {
        return getId() + " | " +getName();
    }
}
