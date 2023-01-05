package com.example.blooddonation;

public class Faqmodel {
    String title ,description;
    Boolean isvisible;

    public Faqmodel(String title, String description,Boolean isvisible) {
        this.title = title;
        this.description = description;
        this.isvisible=isvisible;
    }
    public Faqmodel(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public Faqmodel() {

    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(Boolean isvisible) {
        this.isvisible = isvisible;
    }
}
