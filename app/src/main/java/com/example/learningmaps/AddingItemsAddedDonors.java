package com.example.learningmaps;

public class AddingItemsAddedDonors {

    private String addedname;
    private String addedimgURL;
    private String addedid;

    public AddingItemsAddedDonors(String searchname, String searchimgURL, String searchid) {
        this.addedname = searchname;
        this.addedimgURL = searchimgURL;
        this.addedid = searchid;
    }

    public String getAddedname() {
        return addedname;
    }

    public String getAddedimgURL() {
        return addedimgURL;
    }

    public String getAddedid() {
        return addedid;
    }
}
