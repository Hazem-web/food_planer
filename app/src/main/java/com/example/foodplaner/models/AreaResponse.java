package com.example.foodplaner.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AreaResponse {
    @SerializedName("meals")
    private List<Area> areas;

    // Getter
    public List<Area> getAreas() {
        return areas;
    }

    // Setter
    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
