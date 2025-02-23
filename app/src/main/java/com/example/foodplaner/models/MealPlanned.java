package com.example.foodplaner.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "PLANNED_MEALS")
public class MealPlanned {
    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    private String category;

    private String area;

    private String instructions;

    private String img;

    private String tags;

    private String video;

    private String source;

    private String ingredients;

    private String measures;

    private Date date;

    public MealPlanned() {
    }

    public MealPlanned(Meal meal){
        id=meal.getId();
        name=meal.getName();
        category=meal.getCategory();
        area=meal.getArea();
        instructions=meal.getInstructions();
        img=meal.getImg();
        tags=meal.getTags();
        video=meal.getVideo();
        source=meal.getSource();
        ingredients=meal.getIngredients();
        measures=meal.getMeasures();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }
}
