package com.example.foodplaner.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CategoryFilterResponse {
    @SerializedName("meals")
    private List<Category> categories;

    // Getter
    public List<Category> getCategories() {
        return categories;
    }

    // Setter
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
