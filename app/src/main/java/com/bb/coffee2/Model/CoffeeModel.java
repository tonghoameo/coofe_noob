package com.bb.coffee2.Model;

import com.google.firebase.firestore.DocumentId;

public class CoffeeModel {
    @DocumentId
    String coffeeId;
    String coffeeName,description,imageUrl;
    int price, quantity;
    public CoffeeModel(){

    }
    public CoffeeModel(String coffeeId, String coffeeName, String description, String imageUrl, int price, int quantity){
        this.coffeeId = coffeeId;
        this.coffeeName = coffeeName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public void setCoffeeId(String coffeeId) {
        this.coffeeId = coffeeId;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public String getDescription() {
        return description;
    }

    public String getCoffeeId() {
        return coffeeId;
    }

    public Integer getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "CoffeeModel{" +
                "coffeeName='" + coffeeName + '\'' +
                ", coffeeId='" + coffeeId + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
