package com.example.ebrah.woocommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("name")
    private String mTitle;

    @SerializedName("image")
    private CategoryImage mCategoryImage;

    public String getTitle() {
        return mTitle;
    }

    public CategoryImage getCategoryImage() {
        return mCategoryImage;
    }

    public Category(String title, CategoryImage categoryImage) {
        mTitle = title;
        mCategoryImage = categoryImage;
    }

}

