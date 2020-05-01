package com.example.learningmaps;

public class AddingItemsOldRequests {
    private String gender;
    private String request_id;
    private String name;
    private String type;
    private String location;
    private String mobile;
    private String status;
    private String units;

    public AddingItemsOldRequests(String gender,String request_id,String name, String type, String location, String mobile, String status, String units) {
        this.gender=gender;
        this.request_id=request_id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.mobile = mobile;
        this.status = status;
        this.units = units;
    }

    public String getGender() {
        return gender;
    }

    public String getRequest_id() {
        return request_id;
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getMobile() {
        return mobile;
    }

    public String getStatus() {
        return status;
    }

    public String getUnits() {
        return units;
    }
}
