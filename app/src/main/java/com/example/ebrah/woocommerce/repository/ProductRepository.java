package com.example.ebrah.woocommerce.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.model.enums.ListOrder;

import java.util.List;
import java.util.Objects;

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

    //Initializing max number of pages until server responses the exact number of max pages
    private int mMaxNumberOfPages = 1;


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
    public MutableLiveData<List<Product>> getProductListByDateMutableLiveData(int pageNumber) {

        //checking that maximum page is reached or not
        Log.i("toop", "getProductListByDateMutableLiveData: "+ getMaxNumberOfPages(ListOrder.ORDER_BY_DATE, pageNumber));
        if(pageNumber <= getMaxNumberOfPages(ListOrder.ORDER_BY_DATE, pageNumber)){
            Log.i("toop", "getProductListByDateMutableLiveData: Reached" );
            mApi.getProductsByOrder(ORDER_BY_DATE, pageNumber).enqueue(new Callback<List<Product>>() {
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


        }
        return mProductListByDateMutableLiveData;
    }

    //Order by Popularity
    public MutableLiveData<List<Product>> getProductListByPopularityMutableLiveData(int pageNumber){

        //checking that maximum page is reached or not
        if(pageNumber <= getMaxNumberOfPages(ListOrder.ORDER_BY_POPULARITY, pageNumber)){
            mApi.getProductsByOrder(ORDER_BY_POPULARITY, pageNumber).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if(response.isSuccessful() && response.code()!= 400)
                        mProductListByPopularityMutableLiveData.setValue(response.body());
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Toast.makeText(mContext, "No products found...", Toast.LENGTH_LONG).show();
                }
            });
        }


        return mProductListByPopularityMutableLiveData;
    }

    //Order by Rating
    public MutableLiveData<List<Product>> getProductListByRatingMutableLiveData(int pageNumber){

        //checking that maximum page is reached or not
        if(pageNumber <= getMaxNumberOfPages(ListOrder.ORDER_BY_RATING, pageNumber)){
            mApi.getProductsByOrder(ORDER_BY_RATING, pageNumber).enqueue(new Callback<List<Product>>() {
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
        }

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

    public int getMaxNumberOfPages(ListOrder listOrder, int pageNumber){
        mApi.getProductsByOrder(listOrder.getOrderBy(), pageNumber).enqueue(new Callback<List<Product>>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                    mMaxNumberOfPages = Integer.parseInt(Objects.requireNonNull(response.headers().get("X-WP-TotalPages")));
                Log.i("toop", "onResponse: " + mMaxNumberOfPages);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        return mMaxNumberOfPages;
    }

}
