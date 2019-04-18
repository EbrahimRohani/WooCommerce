package com.example.ebrah.woocommerce.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.model.ProductImage;
import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.model.enums.ListOrder;
import com.example.ebrah.woocommerce.repository.ProductRepository;
import com.example.ebrah.woocommerce.view.ProductDetailFragment;
import com.example.ebrah.woocommerce.view.ProductListFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewModel extends ViewModel {
    private static final String TAG = "noob";
    private MutableLiveData<List<ProductImage>> mProductImageListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mProductMutableLiveData = new MutableLiveData<>();

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        if (imageUrl != null)
            Picasso.get().load(imageUrl).into(imageView);
        else
            Picasso.get().load(R.drawable.product_place_holder).into(imageView);
    }

    public String getImagePath() {

        for (ProductImage productImage : mProductImageListMutableLiveData.getValue()) {
            if (productImage != null ){
                return productImage.getPathUrl();

            }
        }
        return null;
    }

    public void setProduct(Product product) {
        Log.i(TAG, "setProduct: " + product.getId());
        mProductMutableLiveData.setValue(product);
        mProductImageListMutableLiveData.setValue(product.getProductImages());
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

    public MutableLiveData<List<Product>> getProductListByOrderLiveData(Context context, ListOrder listOrder, int pageNumber) {
        switch (listOrder){
            case ORDER_BY_DATE:
                return ProductRepository.getInstance(context).getProductListByDateMutableLiveData(pageNumber);

            case ORDER_BY_POPULARITY:
                return ProductRepository.getInstance(context).getProductListByPopularityMutableLiveData(pageNumber);

            case ORDER_BY_RATING:
                return ProductRepository.getInstance(context).getProductListByRatingMutableLiveData(pageNumber);
        }
        return null;
    }

    public MutableLiveData<Product> findProductById(Context context, int id){
        return ProductRepository.getInstance(context).findProductById(id);
    }

    public void onClick(View view){
        Log.i(TAG, "onClick: called " + getProductId());
        ((AppCompatActivity)view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.single_fragment_container, ProductDetailFragment.newInstance(getProductId())).addToBackStack(null).commit();
    }
}
