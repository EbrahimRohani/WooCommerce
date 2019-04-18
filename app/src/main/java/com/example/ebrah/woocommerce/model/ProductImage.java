package com.example.ebrah.woocommerce.model;

import com.google.gson.annotations.SerializedName;

public class ProductImage {

    @SerializedName("src")
    private String mPathUrl;

    public String getPathUrl() {
        return mPathUrl;
    }

    public ProductImage(String pathUrl) {
        mPathUrl = pathUrl;
    }
}
