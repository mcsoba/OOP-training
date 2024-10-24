package com.oop.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Food {

    private String name;
    private BigDecimal calorie;
    private String description;
    private BigDecimal price;

    private Category category;

    public Food(){

    }

    public Food(String name, BigDecimal calorie, String description, BigDecimal price, Category category) {
        this.name = name;
        this.calorie = calorie;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCalorie(BigDecimal calorie) {
        this.calorie = calorie;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCalorie() {
        return calorie;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food food = (Food) o;
        return Objects.equals(name, food.name) && Objects.equals(calorie, food.calorie) && Objects.equals(description, food.description) && category == food.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, calorie, description, price, category);
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", calorie=" + calorie +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
