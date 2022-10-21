package com.smartsimjgraphics.bccihub.forum;

public class Forum_Model {

    //This is the Model Class

    private int forumID;
    private String forum_category, forum_desc,
            forum_date, forum_time, cust_email, cust_phone;

    public Forum_Model(){

    }

    public Forum_Model(int forumID, String forum_category, String forum_desc,
                       String forum_date, String forum_time, String cust_email,
                       String cust_phone) {

        this.forumID = forumID;
        this.forum_category = forum_category;
        this.forum_desc = forum_desc;
        this.forum_date = forum_date;
        this.forum_time = forum_time;
        this.cust_email = cust_email;
        this.cust_phone = cust_phone;

    }


    public int getForumID() {
        return forumID;
    }

    public void setForumID(int forumID) {
        this.forumID = forumID;
    }

    public String getForum_category() {
        return forum_category;
    }

    public void setForum_category(String forum_category) {
        this.forum_category = forum_category;
    }

    public String getForum_desc() {
        return forum_desc;
    }

    public void setForum_desc(String forum_desc) {
        this.forum_desc = forum_desc;
    }

    public String getForum_date() {
        return forum_date;
    }

    public void setForum_date(String forum_date) {
        this.forum_date = forum_date;
    }

    public String getForum_time() {
        return forum_time;
    }

    public void setForum_time(String forum_time) {
        this.forum_time = forum_time;
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
