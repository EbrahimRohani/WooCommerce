package com.example.ebrah.woocommerce.repository;

import com.example.ebrah.woocommerce.model.Category;
import com.example.ebrah.woocommerce.model.Product;

import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("products/?consumer_key=ck_06ec2d6ff800054efbe42aecfb1df9bfc1022bd1&consumer_secret=cs_be6b76294fa9f74b3b7ee810f616db5ee76ea526")
    Call<List<Product>> getProductsByOrder(@Query("orderby") String orderBy, @Query("page") int pageNumber);

    @GET("products/?consumer_key=ck_06ec2d6ff800054efbe42aecfb1df9bfc1022bd1&consumer_secret=cs_be6b76294fa9f74b3b7ee810f616db5ee76ea526")
    Call<List<Product>> getAllProducts();

    @GET("products/categories?consumer_key=ck_06ec2d6ff800054efbe42aecfb1df9bfc1022bd1&consumer_secret=cs_be6b76294fa9f74b3b7ee810f616db5ee76ea526")
    Call<List<Category>> getAllCategories();
}
