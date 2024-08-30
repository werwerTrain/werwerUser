package com.buaa.werweruser.entity;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class User {
    // 身份证号
    private String id;

    // 姓名
    private String name;
    private String password;
    private String email;

    // 无参构造方法
    public User() {
    }

    // 带参构造方法
    public User(String id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    // @TableField(value = "id")
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public User(String id, String name, String password, String email) {
//        this.id = id;
//        this.name = name;
//        this.password = password;
//        this.email = email;
//    }

//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
//
//    public List<Passenger> getPassengers() {
//        return passengers;
//    }
//
//    public void setPassengers(List<Passenger> passengers) {
//        this.passengers = passengers;
//    }
}
