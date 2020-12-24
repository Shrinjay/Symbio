package com.models;

//Java object defining fields for a request from the front end to fetch mail from inbox.
public class mailReq{

    private String email;
    private String pass;
    private String keyword;

    public mailReq(String email, String pass, String keyword)
    {
        this.email = email;
        this.pass = pass;
        this.keyword = keyword;
    }

    public String get_email() {return email;}
    public void set_email(String email) {this.email = email;}

    public String get_pass() {return pass;}
    public void set_pass(String pass) {this.pass = pass;}

    public String get_keyword() {return keyword;}
    public void set_keyword(String keyword) {this.keyword = keyword;}


}