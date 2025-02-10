package com.example.foodplaner.models;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("idCategory")
    private String id;

    @SerializedName("strCategory")
    private String name;

    @SerializedName("strCategoryThumb")
    private String img;

    @SerializedName("strCategoryDescription")
    private String desc;

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getDesc() {
        return desc;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}