package com.example.ebrah.woocommerce.viewmodel;

import android.content.Context;
import android.widget.ImageView;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.model.Image;
import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.repository.ProductRepository;
import com.example.ebrah.woocommerce.view.ProductDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "noob";
    private MutableLiveData<List<Image>> mImageListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mProductMutableLiveData = new MutableLiveData<>();
    //private int mImagePosition = 0;

//    public void setImagePosition(int imagePosition) {
//        mImagePosition = imagePosition;
//    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        if (imageUrl != null)
            Picasso.get().load(imageUrl).into(imageView);
        else
            Picasso.get().load(R.drawable.product_place_holder).into(imageView);
    }

    public String getImagePath() {
//        return mImageListMutableLiveData.getValue().get(mImagePosition).getPathUrl();

        for (Image image : mImageListMutableLiveData.getValue()) {
            if (image != null ){
                return image.getPathUrl();

            }
        }
        return null;
    }

    public void setProduct(Product product) {
        mProductMutableLiveData.setValue(product);
        mImageListMutableLiveData.setValue(product.getImages());
    }

    public String getProductTitle() {
        if (mProductMutableLiveData.getValue().getName() != null)
            return mProductMutableLiveData.getValue().getName();
        else
            return "Sorry! product title not found...";
    }

    public String getProductPrice() {
        if (mProductMutableLiveData.getValue().getPrice() != null)
            return mProductMutableLiveData.getValue().getPrice()+ " " + "تومان";
        else
            return "Sorry! product price not found...";
    }

    public int getProductId(){
        return mProductMutableLiveData.getValue().getId();
    }

    public MutableLiveData<List<Product>> getProductListByDateLiveData(Context context) {
        return ProductRepository.getInstance(context).getProductListByDateMutableLiveData();
    }

    public MutableLiveData<List<Product>> getProductListByPopularityLiveData(Context context){
        return ProductRepository.getInstance(context).getProductListByPopularityMutableLiveData();
    }

    public MutableLiveData<List<Product>> getProductListByRatingLiveData(Context context){
        return ProductRepository.getInstance(context).getProductListByRatingMutableLiveData();
    }

    public MutableLiveData<Product> findProductById(Context context, int id){
        return ProductRepository.getInstance(context).findProductById(id);
    }

    public void onClick(){

        ProductDetailFragment.newInstance(getProductId());
    }
}
