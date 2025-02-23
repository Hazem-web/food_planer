package com.example.foodplaner.models;
import com.google.gson.*;
import java.lang.reflect.Type;


public class MealDeserializer implements JsonDeserializer<Meal> {
    @Override
    public Meal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Meal meal = new Meal();

        // Set basic fields
        meal.setId(jsonObject.has("idMeal") && !jsonObject.get("idMeal").isJsonNull() ? jsonObject.get("idMeal").getAsString() : "");
        meal.setName(jsonObject.has("strMeal") && !jsonObject.get("strMeal").isJsonNull() ? jsonObject.get("strMeal").getAsString() : "");
        meal.setCategory(jsonObject.has("strCategory") && !jsonObject.get("strCategory").isJsonNull() ? jsonObject.get("strCategory").getAsString() : "");
        meal.setArea(jsonObject.has("strArea") && !jsonObject.get("strArea").isJsonNull() ? jsonObject.get("strArea").getAsString() : "");
        meal.setInstructions(jsonObject.has("strInstructions") && !jsonObject.get("strInstructions").isJsonNull() ? jsonObject.get("strInstructions").getAsString() : "");
        meal.setImg(jsonObject.has("strMealThumb") && !jsonObject.get("strMealThumb").isJsonNull() ? jsonObject.get("strMealThumb").getAsString() : "");
        meal.setTags(jsonObject.has("strTags") && !jsonObject.get("strTags").isJsonNull() ? jsonObject.get("strTags").getAsString() : "");
        meal.setVideo(jsonObject.has("strYoutube") && !jsonObject.get("strYoutube").isJsonNull() ? jsonObject.get("strYoutube").getAsString() : "");
        meal.setSource(jsonObject.has("strSource") && !jsonObject.get("strSource").isJsonNull() ? jsonObject.get("strSource").getAsString() : "");

        String ingredients="",measures="";

        for (int i = 1; i <= 20; i++) {
            String ingredientKey = "strIngredient" + i;
            String measureKey = "strMeasure" + i;

            if (jsonObject.has(ingredientKey) && !jsonObject.get(ingredientKey).isJsonNull()) {
                String ingredient = jsonObject.get(ingredientKey).getAsString().trim();
                if (!ingredient.isEmpty()) {
                    ingredients+=ingredient+",";
                }
            }

            if (jsonObject.has(measureKey) && !jsonObject.get(measureKey).isJsonNull()) {
                String measure = jsonObject.get(measureKey).getAsString().trim();
                if (!measure.isEmpty()) {
                    measures+=measure+",";
                }
            }
        }
        if (ingredients.length()>0) {
            meal.setIngredients(ingredients.substring(0, ingredients.length() - 1));
            meal.setMeasures(measures.substring(0, measures.length() - 1));
        }
        return meal;
    }
}
