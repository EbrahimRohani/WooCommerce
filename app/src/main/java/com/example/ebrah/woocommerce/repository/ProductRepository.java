package com.example.ebrah.woocommerce.repository;

import android.content.Context;
import android.widget.Toast;

import com.example.ebrah.woocommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private static final String ORDER_BY_DATE = "date";
    private static final String ORDER_BY_POPULARITY = "popularity";
    private static final String ORDER_BY_RATING = "rating";

    private MutableLiveData<List<Product>> mProductListByDateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListByPopularityMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListByRatingMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mAllProductsListMutableLiveData = new MutableLiveData<>();

    private static ProductRepository instance;
    private Context mContext;
    private Api mApi;


    private ProductRepository(Context context) {
        mContext = context.getApplicationContext();
        mApi = RetrofitClientInstance.getInstance().create(Api.class);
    }

    public static ProductRepository getInstance(Context context) {
        if (instance == null)
            instance = new ProductRepository(context);

        return instance;
    }

    //Order by Date
    public MutableLiveData<List<Product>> getProductListByDateMutableLiveData() {
        mApi.getProducts(ORDER_BY_DATE).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    mProductListByDateMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(mContext, "No products found...", Toast.LENGTH_LONG).show();
            }
        });

        return mProductListByDateMutableLiveData;
    }

    //Order by Popularity
    public MutableLiveData<List<Product>> getProductListByPopularityMutableLiveData(){
        mApi.getProducts(ORDER_BY_POPULARITY).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                    mProductListByPopularityMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(mContext, "No products found...", Toast.LENGTH_LONG).show();
            }
        });

        return mProductListByPopularityMutableLiveData;
    }

    //Order by Rating
    public MutableLiveData<List<Product>> getProductListByRatingMutableLiveData(){
        mApi.getProducts(ORDER_BY_RATING).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                    mProductListByRatingMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(mContext, "No products found...", Toast.LENGTH_LONG).show();
            }
        });
        return mProductListByRatingMutableLiveData;
    }

    public MutableLiveData<Product> findProductById(final int id){
        final MutableLiveData<Product> mutableLiveDataProduct = new MutableLiveData<>();
        mApi.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                    mAllProductsListMutableLiveData.setValue(response.body());

                for(Product product : mAllProductsListMutableLiveData.getValue()){
                    if(product.getId() == id)
                        mutableLiveDataProduct.setValue(product);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(mContext, "No products found...", Toast.LENGTH_LONG).show();
            }
        });



        return mutableLiveDataProduct;
    }



//    public MutableLiveData<Product> findProductById(int id){
//        for(Product product : getMutableListOfAllProducts(mContext).getValue()){
//            if(product.getId() == id) {
//                mSearchedProduct.setValue(product);
//                return mSearchedProduct;
//            }
//        }
//
//        return null;
//    }
}
