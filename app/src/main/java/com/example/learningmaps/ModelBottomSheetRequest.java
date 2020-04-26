package com.example.learningmaps;

public class ModelBottomSheetRequest {
    private String name;
    private String units;
    private String bloodtype;
    private String status;
    private String gender;
    private String place;
    private String phone;

    public ModelBottomSheetRequest(String name, String units, String bloodtype, String status, String gender, String place, String phone) {
        this.name = name;
        this.units = units;
        this.bloodtype = bloodtype;
        this.status = status;
        this.gender = gender;
        this.place = place;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getUnits() {
        return units;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public String getStatus() {
        return status;
    }

    public String getGender() {
        return gender;
    }

    public String getPlace() {
        return place;
    }

    public String getPhone() {
        return phone;
    }
}
