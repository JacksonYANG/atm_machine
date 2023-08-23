package com.jacksonyang.atm_machine.Model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

//用户信息表
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //基本属性
    //用户名
    private String userName;
    //密码
    private String password;

    //姓名
    private String name;
    //余额
    private double balance;
    //手机号
    private String phone;
    //地址
    private String address;
    //账户创建时间
    private LocalDateTime createdTime;
    //账户修改时间
    private LocalDateTime lastModifiedTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @PrePersist
    public void prePersist(){
        createdTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        lastModifiedTime = LocalDateTime.now();
    }
}
