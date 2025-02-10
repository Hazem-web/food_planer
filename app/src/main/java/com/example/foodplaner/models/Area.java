package com.example.foodplaner.models;

import com.google.gson.annotations.SerializedName;

public class Area {
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
}
