package com.example.patricia.contactlist;

public class Contact {

    public int id;

    public String contactName;
    public String familyName;

    public String contactPhone;
    public String additionalPhone;

    public String email;

    public String address;

    /*
        Default constructor
     */
    public Contact(){

    }

    /*
        Full constructor.
     */
    public Contact(String name, String familyName, String phone, String additionalPhone, String email, String address){
        this.contactName = name;
        this.familyName = familyName;
        this.contactPhone = phone;
        this.additionalPhone = additionalPhone;
        this.email = email;
        this.address = address;
    }

    /*
        Getters and setters of the object
     */
    public void setId(int id) { this.id = id; }

    public void setContactName(String name){
        this.contactName = name;
    }

    public void setFamilyName(String familyName){
        this.familyName = familyName;
    }

    public void setContactPhone(String phone){
        this.contactPhone = phone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setAdditionalPhone(String additionalPhone){
        this.additionalPhone = additionalPhone;
    }

    public int getId(){ return id; }

    public String getContactName() {
        return contactName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getAdditionalPhone() {
        return additionalPhone;
    }
}
