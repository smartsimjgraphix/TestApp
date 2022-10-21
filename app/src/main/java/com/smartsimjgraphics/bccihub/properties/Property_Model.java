package com.smartsimjgraphics.bccihub.properties;

public class Property_Model {

    private int propID;
    private String propTitle, propDistrict, propLocation, propSize,
            propNumOfBedRooms, propYear, propPrice, propType, propStatus,
            propPayStatus, propPayMonth, propDescription, cust_email, cust_phone;

    public Property_Model() {

    }

    public Property_Model(int propID, String propTitle, String propDistrict,
                          String propLocation, String propSize, String propNumOfBedRooms,
                          String propYear, String propPrice, String propType,
                          String propStatus, String propPayStatus, String propPayMonth,
                          String propDescription, String cust_email, String cust_phone) {

        this.propID = propID;
        this.propTitle = propTitle;
        this.propDistrict = propDistrict;
        this.propLocation = propLocation;
        this.propSize = propSize;
        this.propNumOfBedRooms = propNumOfBedRooms;
        this.propYear = propYear;
        this.propPrice = propPrice;
        this.propType = propType;
        this.propStatus = propStatus;
        this.propPayStatus = propPayStatus;
        this.propPayMonth = propPayMonth;
        this.propDescription = propDescription;
        this.cust_email = cust_email;
        this.cust_phone = cust_phone;

    }


    public int getPropID() {
        return propID;
    }

    public void setPropID(int propID) {
        this.propID = propID;
    }

    public String getPropTitle() {
        return propTitle;
    }

    public void setPropTitle(String propTitle) {
        this.propTitle = propTitle;
    }

    public String getPropDistrict() {
        return propDistrict;
    }

    public void setPropDistrict(String propDistrict) {
        this.propDistrict = propDistrict;
    }

    public String getPropLocation() {
        return propLocation;
    }

    public void setPropLocation(String propLocation) {
        this.propLocation = propLocation;
    }

    public String getPropSize() {
        return propSize;
    }

    public void setPropSize(String propSize) {
        this.propSize = propSize;
    }

    public String getPropNumOfBedRooms() {
        return propNumOfBedRooms;
    }

    public void setPropNumOfBedRooms(String propNumOfBedRooms) {
        this.propNumOfBedRooms = propNumOfBedRooms;
    }

    public String getPropYear() {
        return propYear;
    }

    public void setPropYear(String propYear) {
        this.propYear = propYear;
    }

    public String getPropPrice() {
        return propPrice;
    }

    public void setPropPrice(String propPrice) {
        this.propPrice = propPrice;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getPropStatus() {
        return propStatus;
    }

    public void setPropStatus(String propStatus) {
        this.propStatus = propStatus;
    }

    public String getPropPayStatus() {
        return propPayStatus;
    }

    public void setPropPayStatus(String propPayStatus) {
        this.propPayStatus = propPayStatus;
    }

    public String getPropPayMonth() {
        return propPayMonth;
    }

    public void setPropPayMonth(String propPayMonth) {
        this.propPayMonth = propPayMonth;
    }

    public String getPropDescription() {
        return propDescription;
    }

    public void setPropDescription(String propDescription) {
        this.propDescription = propDescription;
    }

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public void setCust_phone(String cust_phone) {
        this.cust_phone = cust_phone;
    }


}
