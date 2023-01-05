package com.example.blooddonation;

import com.google.firebase.Timestamp;

public class UsersModel {
    String name;
    String phone;
    String password;
    String bloodGroup;
    String area;
    String district;
    String photoURL;
    String lastDonation;



    String status;
    int numofdonation;
    Boolean isAdmin,isDonar;
    Timestamp timeStamp;
    public UsersModel() {
    }
    public UsersModel(String name, String phone, String password, String bloodGroup, String area, String district, String photoURL, String lastDonation, String status, int numofdonation, Boolean isAdmin, Boolean isDonar) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.bloodGroup = bloodGroup;
        this.area = area;
        this.district = district;
        this.photoURL = photoURL;
        this.lastDonation = lastDonation;
        this.status = status;
        this.numofdonation = numofdonation;
        this.isAdmin = isAdmin;
        this.isDonar = isDonar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getLastDonation() {
        return lastDonation;
    }

    public void setLastDonation(String lastDonation) {
        this.lastDonation = lastDonation;
    }

    public int getNumofdonation() {
        return numofdonation;
    }

    public void setNumofdonation(int numofdonation) {
        this.numofdonation = numofdonation;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getDonar() {
        return isDonar;
    }

    public void setDonar(Boolean donar) {
        isDonar = donar;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
