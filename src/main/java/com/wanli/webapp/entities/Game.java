package com.wanli.webapp.entities;

import java.sql.Date;

public class Game {
    private int gid;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Game() {
    }

    public Game(int gid, String gname, String image, int publisher, String pubName, Date rdate) {
        this.gid = gid;
        this.gname = gname;
        this.image = image;
        this.publisher = publisher;
        this.pubName = pubName;
        this.rdate = rdate;
    }

    private String gname;
    private String image;
    private int publisher;

    private String pubName;

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pub_name) {
        this.pubName = pub_name;
    }

    private Date rdate;

    private String description;

    // Constructors, getters and setters
}
