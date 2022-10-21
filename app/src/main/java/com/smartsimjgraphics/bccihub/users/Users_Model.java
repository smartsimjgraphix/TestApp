package com.smartsimjgraphics.bccihub.users;

public class Users_Model {

    private String custID, cust_type, cust_fname, cust_lname, cust_username,
            cust_password, cust_phone, cust_address, cust_city, cust_occupation,
            cust_email, cust_fax;

    public Users_Model() {
    }

    public Users_Model(String custID, String cust_type, String cust_fname,
                       String cust_lname, String cust_username, String cust_password,
                       String cust_phone, String cust_address, String cust_city,
                       String cust_occupation, String cust_email, String cust_fax) {

        this.custID = custID;
        this.cust_type = cust_type;
        this.cust_fname = cust_fname;
        this.cust_lname = cust_lname;
        this.cust_username = cust_username;
        this.cust_password = cust_password;
        this.cust_phone = cust_phone;
        this.cust_address = cust_address;
        this.cust_city = cust_city;
        this.cust_occupation = cust_occupation;
        this.cust_email = cust_email;
        this.cust_fax = cust_fax;
    }


    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getCust_type() {
        return cust_type;
    }

    public void setCust_type(String cust_type) {
        this.cust_type = cust_type;
    }

    public String getCust_fname() {
        return cust_fname;
    }

    public void setCust_fname(String cust_fname) {
        this.cust_fname = cust_fname;
    }

    public String getCust_lname() {
        return cust_lname;
    }

    public void setCust_lname(String cust_lname) {
        this.cust_lname = cust_lname;
    }

    public String getCust_username() {
        return cust_username;
    }

    public void setCust_username(String cust_username) {
        this.cust_username = cust_username;
    }

    public String getCust_password() {
        return cust_password;
    }

    public void setCust_password(String cust_password) {
        this.cust_password = cust_password;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public void setCust_phone(String cust_phone) {
        this.cust_phone = cust_phone;
    }

    public String getCust_address() {
        return cust_address;
    }

    public void setCust_address(String cust_address) {
        this.cust_address = cust_address;
    }

    public String getCust_city() {
        return cust_city;
    }

    public void setCust_city(String cust_city) {
        this.cust_city = cust_city;
    }

    public String getCust_occupation() {
        return cust_occupation;
    }

    public void setCust_occupation(String cust_occupation) {
        this.cust_occupation = cust_occupation;
    }

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }

    public String getCust_fax() {
        return cust_fax;
    }

    public void setCust_fax(String cust_fax) {
        this.cust_fax = cust_fax;
    }

}
