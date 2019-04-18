package com.example.ebrah.woocommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("price")
    private String mPrice;

    @SerializedName("images")
    private List<ProductImage> mProductImages;

    public Product(int id, String name, String price, List<ProductImage> productImages) {
        mId = id;
        mName = name;
        mPrice = price;
        mProductImages = productImages;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPrice() {
        return mPrice;
    }

    public List<ProductImage> getProductImages() {
        return mProductImages;
    }
}
