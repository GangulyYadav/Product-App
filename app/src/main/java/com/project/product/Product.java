package com.project.product;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Product implements Serializable {
    private int id;
    private String title;
    private String description;
    private double price;
    private double discountPercentage;
    private double rating;
    private int stock;
    private String brand;
    private String category;
    private String thumbnail;
    private List<String> images;



    public Product(int id, String title, String description, double price, double discountPercentage, double rating, String brand, int stock , String category, String thumbnail, List<String> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.brand = brand;
        this.category = category;
        this.stock = stock;
        this.thumbnail = thumbnail;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public double getRating() {
        return rating;
    }

    public String getBrand() {
        return brand;
    }

    public int getStock() { return stock; }

    public String getCategory() { return category; }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<String> getImages() {
        return images;
    }
}
