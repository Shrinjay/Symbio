package com.models;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import com.models.sponsors;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Java object defining fields for an action
@Entity
@Table(name="actions")
public class actions {
   
    @Id
    @GeneratedValue
    private long _id;
  

    @Column(name="actiontype")
    private String actiontype;
    @Column(name="actiondate")
    private String actiondate;
    @Column(name="actionuser")
    private String actionuser;
    @Column(name="actiondetails")
    private String actiondetails;

    public actions() {}

    public actions(long _id, String actiontype, String actiondate, String actionuser, String actiondetails)
    {
        this._id = _id;
        this.actiontype = actiontype;
        this.actiondate = actiondate; 
        this.actionuser = actionuser; 
        this.actiondetails = actiondetails;
    }



    public long get_id(){return _id;}
    public void set_id(long _id) {this._id = _id;}

    public String get_actiontype() {return actiontype;}
    public void set_actiontype(String actiontype) {this.actiontype = actiontype;}

    public String get_actiondate() {return actiondate;}
    public void set_actiondate(String actiondate) {this.actiondate = actiondate;}

    public String get_actionuser() {return actionuser;}
    public void set_actionuser(String actionuser) {this.actionuser = actionuser;}

    public String get_actiondetails() {return actiondetails;}
    public void set_actiondetails(String actiondetails) {this.actiondetails = actiondetails;}
}
