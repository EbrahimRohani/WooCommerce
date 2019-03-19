package com.example.ebrah.woocommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("name")
    private String mName;

    @SerializedName("price")
    private String mPrice;

    @SerializedName("images")
    private List<Image> mImages;

    public Product(String name, String price, List<Image> images) {
        mName = name;
        mPrice = price;
        mImages = images;
    }

    public String getName() {
        return mName;
    }

    public String getPrice() {
        return mPrice;
    }

    public List<Image> getImages() {
        return mImages;
    }
}
