package com.smartsimjgraphics.bccihub.payments;

public class Payments_Model {

    int payID;
    String pay_source, pay_date_time, cust_username, cust_email, cust_phone;

    public Payments_Model(){

    }

    public Payments_Model(int payID, String pay_source,
                          String pay_date_time, String cust_username, String cust_email,
                          String cust_phone) {

        this.payID = payID;
        this.pay_source = pay_source;
        this.pay_date_time = pay_date_time;
        this.cust_username = cust_username;
        this.cust_email = cust_email;
        this.cust_phone = cust_phone;

    }

    public int getPayID() {
        return payID;
    }

    public void setPayID(int payID) {
        this.payID = payID;
    }

    public String getPay_source() {
        return pay_source;
    }

    public void setPay_source(String pay_source) {
        this.pay_source = pay_source;
    }

    public String getPay_date_time() {
        return pay_date_time;
    }

    public void setPay_date_time(String pay_date_time) {
        this.pay_date_time = pay_date_time;
    }

    public String getCust_username() {
        return cust_username;
    }

    public void setCust_username(String cust_username) {
        this.cust_username = cust_username;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public void setCust_phone(String cust_phone) {
        this.cust_phone = cust_phone;
    }

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }


}
