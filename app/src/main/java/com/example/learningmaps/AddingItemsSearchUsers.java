package com.example.learningmaps;

public class AddingItemsSearchUsers {
    private String search_user_name;
    private String search_user_imgID;
    private String search_user_bloodtype;
    private String search_user_email;

    public AddingItemsSearchUsers(String search_user_name, String search_user_imgID, String search_user_bloodtype,String search_user_email) {
        this.search_user_name = search_user_name;
        this.search_user_imgID = search_user_imgID;
        this.search_user_bloodtype = search_user_bloodtype;
        this.search_user_email= search_user_email;
    }

    public String getSearch_user_email() {
        return search_user_email;
    }

    public String getSearch_user_name() {
        return search_user_name;
    }

    public String getSearch_user_imgID() {
        return search_user_imgID;
    }

    public String getSearch_user_bloodtype() {
        return search_user_bloodtype;
    }

    public void setSearch_user_name(String search_user_name) {
        this.search_user_name = search_user_name;
    }

    public void setSearch_user_email(String search_user_email) {
        this.search_user_email = search_user_email;
    }

    public void setSearch_user_imgID(String search_user_imgID) {
        this.search_user_imgID = search_user_imgID;
    }

    public void setSearch_user_bloodtype(String search_user_bloodtype) {
        this.search_user_bloodtype = search_user_bloodtype;
    }
}
