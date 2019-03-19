package com.example.ebrah.woocommerce.model;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("src")
    private String mPathUrl;

    public String getPathUrl() {
        return mPathUrl;
    }

    public Image(String pathUrl) {
        mPathUrl = pathUrl;
    }
}
