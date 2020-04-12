package com.example.learningmaps;

public class AddingItemsSearchDonors {

    private String searchname;
    private String searchimgURL;
    private String searchid;

    public AddingItemsSearchDonors(String searchname, String searchimgURL, String searchid) {
        this.searchname = searchname;
        this.searchimgURL = searchimgURL;
        this.searchid = searchid;
    }


    public String getSearchname() {
        return searchname;
    }

    public String getSearchimgURL() {
        return searchimgURL;
    }

    public String getSearchid() {
        return searchid;
    }
}
