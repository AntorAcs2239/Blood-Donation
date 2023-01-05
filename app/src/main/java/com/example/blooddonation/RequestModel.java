package com.example.blooddonation;

import android.text.BoringLayout;

import com.google.firebase.Timestamp;

public class RequestModel {

    String bloodGroup,contact,date,district,location,problem,time,uid,reqid;
    Boolean isManaged;
    int numBag;
    int numManaged;
    Timestamp createdAt;

    public RequestModel() {
    }
    public RequestModel(String bloodGroup, String contact, String date, String district, String location, String problem, String time, String uid, Boolean isManaged, int numBag, int numManaged, Timestamp createdAt,String reqid) {

        this.bloodGroup = bloodGroup;
        this.contact = contact;
        this.date = date;
        this.district = district;
        this.location = location;
        this.problem = problem;
        this.time = time;
        this.uid = uid;
        this.isManaged = isManaged;
        this.numBag = numBag;
        this.numManaged = numManaged;
        this.createdAt = createdAt;
        this.reqid=reqid;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Boolean getManaged() {
        return isManaged;
    }

    public void setManaged(Boolean managed) {
        isManaged = managed;
    }

    public int getNumBag() {
        return numBag;
    }

    public void setNumBag(int numBag) {
        this.numBag = numBag;
    }

    public int getNumManaged() {
        return numManaged;
    }

    public void setNumManaged(int numManaged) {
        this.numManaged = numManaged;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
