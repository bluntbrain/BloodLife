package com.example.learningmaps;

public class ModelBottomSheetCamp {

    private String campName;
    private String campOrganisation;
    private String campDate;
    private String campPlace;
    private String campTime;
    private String campContact;

    public ModelBottomSheetCamp(String campName, String campOrganisation, String campDate, String campPlace, String campTime, String campContact) {
        this.campName = campName;
        this.campOrganisation = campOrganisation;
        this.campDate = campDate;
        this.campPlace = campPlace;
        this.campTime = campTime;
        this.campContact = campContact;
    }

    public String getCampName() {
        return campName;
    }

    public String getCampOrganisation() {
        return campOrganisation;
    }

    public String getCampDate() {
        return campDate;
    }

    public String getCampPlace() {
        return campPlace;
    }

    public String getCampTime() {
        return campTime;
    }

    public String getCampContact() {
        return campContact;
    }
}
