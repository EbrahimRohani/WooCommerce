package com.example.ebrah.woocommerce.model;

import com.google.gson.annotations.SerializedName;

public class CategoryImage {

    @SerializedName("src")
    private String mPathUrl;

    private int id;

    public String getPathUrl() {
        return mPathUrl;
    }

    public int getId() {
        return id;
    }

    public CategoryImage(String pathUrl, int id) {
        mPathUrl = pathUrl;
        this.id = id;
    }
}
