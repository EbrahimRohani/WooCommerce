package com.example.ebrah.woocommerce.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ebrah.woocommerce.model.Product;

import java.util.List;

import javax.security.auth.login.LoginException;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private static final String ORDER_BY_DATE = "date";
    private MutableLiveData<List<Product>> mProductListMutableLiveData = new MutableLiveData<>();
    private static ProductRepository instance;


    private ProductRepository(final Context context) {
        Api api = RetrofitClientInstance.getInstance().create(Api.class);
        api.getProductsByDate(ORDER_BY_DATE).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    mProductListMutableLiveData.setValue(response.body());
                    Log.i("noob", "onResponse: " + mProductListMutableLiveData.getValue().size());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(context, "No products found", Toast.LENGTH_SHORT).show();
                Log.i("noob", "onFailure: ");
            }
        });
    }


    public static ProductRepository getInstance(final Context context) {
        if (instance == null)
            instance = new ProductRepository(context);

        return instance;
    }

    public MutableLiveData<List<Product>> getProductListMutableLiveData() {
        return mProductListMutableLiveData;
    }
}
