package com.example.ebrah.woocommerce.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ebrah.woocommerce.model.Product;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private static final String ORDER_BY_DATE = "date";
    private static final String ORDER_BY_POPULARITY = "popularity";
    private static final String ORDER_BY_RATING = "rating";

    private MutableLiveData<List<Product>> mProductListByDateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListByPopularityMutabaleLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListByRatingMutableLiveData = new MutableLiveData<>();
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

    public MutableLiveData<List<Product>> getProductListByPopularityMutabaleLiveData(){
        mApi.getProducts(ORDER_BY_POPULARITY).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                    mProductListByPopularityMutabaleLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(mContext, "No products found...", Toast.LENGTH_LONG).show();
            }
        });

        return mProductListByPopularityMutabaleLiveData;
    }

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
}
