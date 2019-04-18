package com.example.ebrah.woocommerce.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.model.Category;
import com.example.ebrah.woocommerce.model.CategoryImage;
import com.example.ebrah.woocommerce.repository.CategoryRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryViewModel extends ViewModel {
    public static final String TAG = "noob";
    private MutableLiveData<Category> mCategoryMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CategoryImage> mCategoryImageMutableLiveData = new MutableLiveData<>();

    @BindingAdapter("android:categoryImageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl){
        if(imageUrl != null)
            Picasso.get().load(imageUrl).into(imageView);
        else
           // Picasso.get().load(R.drawable.product_place_holder).into(imageView);
            imageView.setImageResource(R.drawable.product_place_holder);
    }

    public void setCategory(Category category){
        mCategoryMutableLiveData.setValue(category);
        if(category.getCategoryImage() != null)
            mCategoryImageMutableLiveData.setValue(category.getCategoryImage());
        else
            mCategoryImageMutableLiveData.setValue(null);

    }

    public MutableLiveData<List<Category>> getCategoryListMutableLiveData(Context context){
        return CategoryRepository.getInstance(context).getCategoryListMutableLiveData();
    }

    public String getCategoryTitle(){
        String title = mCategoryMutableLiveData.getValue().getTitle();

        if(title != null)
            return title;
        else
            return "category title is not found!";
    }

    public String pathUrl(){
        if(mCategoryImageMutableLiveData.getValue() != null)
            return mCategoryImageMutableLiveData.getValue().getPathUrl();
            

        else
            return null;


    }
}
