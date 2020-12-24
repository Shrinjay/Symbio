package com.models;


import com.models.actions;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


//Java object to define the "sponsors" table
@Entity
@Table(name="sponsors")
public class sponsors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long _id; //id field
    //Fields
    @Column(name="sponsorname", nullable=true)
    private String sponsorname;
    @Column(name="contactname", nullable=true)
    private String contactname;
    @Column(name="contactemail", nullable=true)
    private String contactemail;
    @Column(name="status", nullable=true)
    private String status;
    @Column(name="image", nullable=true, columnDefinition="text")
    private String image;

    @Column(nullable=true)
    @OneToMany(targetEntity=actions.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<actions> sponsoractions = new ArrayList<actions>();

    public sponsors() {

    }

    //Constructor
    public sponsors(String image, long _id, String sponsorname, String contactname, String contactemail, String status, List<actions> sponsoractions)
    {
        this._id = _id;
        this.sponsorname = sponsorname;
        this.contactname = contactname;
        this.contactemail = contactemail;
        this.status = status;
        this.sponsoractions = sponsoractions;
        this.image = image;
    } 

    //Getters and setters
    
    public long get_id() {return _id;}
    public void set_id(long _id) {this._id = _id;}

   
    public String get_sponsorname() {return sponsorname;}
    public void set_sponsorname(String sponsorname) {this.sponsorname = sponsorname;}

  
    public String get_contactname() {return contactname;}
    public void set_contactname(String contactname) {this.contactname = contactname;}

    
    public String get_contactemail() {return contactemail;}
    public void set_contactemail(String contactemail) {this.contactemail = contactemail;}

  
    public String get_status() {return status;}
    public void set_status(String status) {this.status = status;}

    
    public List<actions> get_actions() {return sponsoractions;}
    public void set_actions(List<actions> sponsoractions) {this.sponsoractions = sponsoractions;}

   
    public String get_image() {return image;}
    public void set_image(String image) {this.image = image;}
    
    
   

}