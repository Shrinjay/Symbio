package com.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class actions {
    @Id
    public ObjectId _id;

    public String actionType;
    public String actionDate;
    public String actionUser;
    public String actionDetails;

    public actions(ObjectId _id, String actionType, String actionDate, String actionUser, String actionDetails)
    {
        
        this._id = _id;
        this.actionType = actionType;
        this.actionDate = actionDate; 
        this.actionUser = actionUser; 
        this.actionDetails = actionDetails;
    }

    public String get_id(){return _id.toHexString();}
    public void set_id(ObjectId _id) {this._id = _id;}

    public String get_actionType() {return actionType;}
    public void set_actionType(String actionType) {this.actionType = actionType;}

    public String get_actionDate() {return actionDate;}
    public void set_actionDate(String actionDate) {this.actionDate = actionDate;}

    public String get_actionUser() {return actionUser;}
    public void set_actionUser(String actionUser) {this.actionUser = actionUser;}

    public String get_actionDetails() {return actionDetails;}
    public void set_actionDetails(String actionDetails) {this.actionDetails = actionDetails;}
}