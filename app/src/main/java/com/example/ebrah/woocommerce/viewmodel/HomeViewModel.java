package com.example.ebrah.woocommerce.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.model.Image;
import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.repository.ProductRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "noob";

    private MutableLiveData<List<Image>> mImageListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mProductLiveData = new MutableLiveData<>();


    public String getImagePath() {
        for(Image image : mImageListMutableLiveData.getValue()){
            if (image != null)
                return image.getPathUrl();
        }
        return null;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        if (imageUrl != null)
            Picasso.get().load(imageUrl).into(imageView);
        else
            Picasso.get().load(R.drawable.product_place_holder).into(imageView);
    }

    public void setProduct(Product product) {
        mProductLiveData.setValue(product);
        mImageListMutableLiveData.setValue(product.getImages());
    }

    public LiveData<Product> getProductLiveData() {
        return mProductLiveData;
    }

    public String getProductTitle() {
        if (mProductLiveData.getValue().getName() != null)
            return mProductLiveData.getValue().getName();
        else
            return "Sorry! product title not found...";
    }

    public String getProductPrice() {
        if (mProductLiveData.getValue().getPrice() != null)
            return mProductLiveData.getValue().getPrice();
        else
            return "Sorry! product price not found...";
    }

    public LiveData<List<Product>> getProductListLiveData(Context context) {
        return ProductRepository.getInstance(context).getProductListMutableLiveData();
    }

    public List<Product> getProductList(Context context) {
        return ProductRepository.getInstance(context).getProductListMutableLiveData().getValue();
    }


}
