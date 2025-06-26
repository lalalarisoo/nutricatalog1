package com.example.nutricatalog.model;

public class FoodItem {
    private String name;
    private String category;
    private String type;
    private String nutrition;

    private String ingredients;
    private int imageResId; // drawable image

    public FoodItem(String name, String category, String type, String nutrition, String ingredients, int imageResId) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.nutrition = nutrition;
        this.imageResId = imageResId;
    }

    public FoodItem(String name, String category, String type, String nutrition, int imageResId) {
        this(name, category, type, nutrition, "-", imageResId);
    }

    // Getter
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getNutrition() { return nutrition; }

    public String getIngredients() {
        return ingredients;
    }

    public int getImageResId() { return imageResId; }
}
