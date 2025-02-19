package com.example.foodplaner.models;

import com.google.gson.annotations.SerializedName;

public class Area implements Displayable{

    private static final String URL_ICON="https://www.themealdb.com/images/icons/flags/big/64/";
    @SerializedName("strArea")
    private String name;

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public String getImg(){
        return URL_ICON+CountryParser.getCode(name)+".png";
    }

    @Override
    public String getObjectType() {
        return "Area";
    }

    public String getId(){
        return name;
    }
}
