package com.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import com.models.actions;

import java.util.List;

/*This is a POJO, it acts as a model and defines the field using a java object which is instantiated*/

public class sponsors {
    @Id //Means that items will be identified by their id
    public ObjectId _id; //id field

    //Fields
    public String sponsorName;
    public String contactName;
    public String contactEmail;
    public String status;
    public List<actions> sponsorActions;
    public String image;


    //Constructor
    public sponsors(String image, ObjectId _id, String sponsorName, String contactName, String contactEmail, String status, List<actions> sponsorActions)
    {
        this._id = _id;
        this.sponsorName = sponsorName;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.status = status;
        this.sponsorActions = sponsorActions;
        this.image = image;
    } 

    //Getters and setters
    public String get_id() {return _id.toHexString();}
    public void set_id(ObjectId _id) {this._id = _id;}

    public String get_sponsorName() {return sponsorName;}
    public void set_sponsorName(String sponsorName) {this.sponsorName = sponsorName;}

    public String get_contactName() {return contactName;}
    public void set_contactName(String contactName) {this.contactName = contactName;}

    public String get_contactEmail() {return contactEmail;}
    public void set_contactEmail(String contactEmail) {this.contactEmail = contactEmail;}

    public String get_status() {return status;}
    public void set_status(String status) {this.status = status;}

    public List<actions> get_actions() {return sponsorActions;}
    public void set_actions(List<actions> sponsorActions) {this.sponsorActions = sponsorActions;}

    public String get_image() {return image;}
    public void set_image(String image) {this.image = image;}



}