package com.symbio.userService.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Java object to define the "sponsors" table
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long _id; //id field
    //Fields
    @Column(name="username", nullable=true)
    private String username;
    @Column(name="password", nullable=true)
    private String password;
    @Column(name="role", nullable=true)
    private String role;

    public User() {}
    //Constructor
    public User(long _id, String username, String password, String role) {
        this._id = _id;
        this.username = username;
        this.password = password;
    }


    //Getters and setters

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}