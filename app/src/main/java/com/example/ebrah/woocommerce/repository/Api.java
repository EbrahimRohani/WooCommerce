package com.example.ebrah.woocommerce.repository;

import com.example.ebrah.woocommerce.model.Product;

import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("products/?consumer_key=ck_06ec2d6ff800054efbe42aecfb1df9bfc1022bd1&consumer_secret=cs_be6b76294fa9f74b3b7ee810f616db5ee76ea526")
    Call<List<Product>> getProducts(@Query("orderby") String orderBy);

    @GET("products/?consumer_key=ck_06ec2d6ff800054efbe42aecfb1df9bfc1022bd1&consumer_secret=cs_be6b76294fa9f74b3b7ee810f616db5ee76ea526")
    Call<List<Product>> getAllProducts();

//    @GET("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/products?consumer_key=ck_06ec2d6ff800054efbe42aecfb1df9bfc1022bd1&consumer_secret=cs_be6b76294fa9f74b3b7ee810f616db5ee76ea526&orderby=rating&per_page=5&page=1")
//    Call<List<Product>> test();
}
