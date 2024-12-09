package com.example.lucere;

public class Product {
    private String name;
    private String url;
    private String type;
    private String ingredients;

    public Product(String name, String url, String type, String ingredients) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getIngredients() {
        return ingredients;
    }
}
