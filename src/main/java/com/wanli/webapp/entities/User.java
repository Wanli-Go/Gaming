package com.wanli.webapp.entities;

public class User {
    private final int uid;
    private String uname;
    private String password;
    private int utype;

    // Constructors, getters and setters
    public User(int uid) {
        this.uid = uid;
    }

    public User(int uid, String uname, String password, int utype) {
        this.uid = uid;
        this.uname = uname;
        this.password = password;
        this.utype = utype;
    }

    public int getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUtype() {
        return utype;
    }

    public void setUtype(int utype) {
        this.utype = utype;
    }
}
