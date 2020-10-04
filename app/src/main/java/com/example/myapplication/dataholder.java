package com.example.myapplication;

public class dataholder {
    String name,quantity,price,category,pimage;

    dataholder(){

    }

    public dataholder(String name, String quantity, String price, String category, String pimage) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.pimage = pimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
