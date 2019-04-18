package com.example.ebrah.woocommerce.repository;

import android.content.Context;
import android.widget.Toast;

import com.example.ebrah.woocommerce.model.Category;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private static final CategoryRepository ourInstance = new CategoryRepository();
    private MutableLiveData<List<Category>> mCategoryListMutableLiveData = new MutableLiveData<>();

    //TODO: check whether getting context in static form is correct or not...
    private static Context mContext;
    private Api mApi;
    
    public static CategoryRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        return ourInstance;
    }

    private CategoryRepository() {
        mApi = RetrofitClientInstance.getInstance().create(Api.class);    
    }

    public MutableLiveData<List<Category>> getCategoryListMutableLiveData(){
        mApi.getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful())
                    mCategoryListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
            }
        });

        return mCategoryListMutableLiveData;
    }
}
