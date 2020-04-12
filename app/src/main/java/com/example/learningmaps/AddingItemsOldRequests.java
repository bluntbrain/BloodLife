package com.example.learningmaps;

public class AddingItemsOldRequests {

    private String name;
    private String type;
    private String location;
    private String mobile;
    private String status;
    private String units;

    public AddingItemsOldRequests(String name, String type, String location, String mobile, String status, String units) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.mobile = mobile;
        this.status = status;
        this.units = units;
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
