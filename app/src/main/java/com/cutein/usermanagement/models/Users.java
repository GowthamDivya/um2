package com.cutein.usermanagement.models;


public class Users {
    private String id;
    private String Name;
    private String Date;
    private String Salary;
    private String Email;

    public Users(){ }

    public Users(String id, String name, String date, String salary, String email) {
        this.id = id;
        Name = name;
        Date = date;
        Salary = salary;
        Email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getDate() {
        return Date;
    }

    public String getSalary() {
        return Salary;
    }

    public String getEmail() {
        return Email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public void setEmail(String email) {
        Email = email;
    }
}