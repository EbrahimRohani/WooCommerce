package com.example.ebrah.woocommerce.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.databinding.FragmentImagePagerBinding;
import com.example.ebrah.woocommerce.model.Image;
import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.repository.ProductRepository;
import com.example.ebrah.woocommerce.viewmodel.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.EventListener;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagePagerFragment extends Fragment {

    private static final String PRODUCT_ID = "product_id";
    private static final String IMAGE_POSITION = "image_position";
    public static final String TAG = "noob";

    private FragmentImagePagerBinding mFragmentImagePagerBinding;

    private int mProductId;
    private int mImagePosition;
    private Product mProduct;
    private HomeViewModel mHomeViewModel;

    public static ImagePagerFragment newInstance(int productId, int imagePosition) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        args.putInt(IMAGE_POSITION, imagePosition);
        ImagePagerFragment fragment = new ImagePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ImagePagerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mProductId = getArguments().getInt(PRODUCT_ID);
        mImagePosition = getArguments().getInt(IMAGE_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentImagePagerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_pager, container, false);
        mFragmentImagePagerBinding.setLifecycleOwner(this);
        observeViewModel(mHomeViewModel, mProductId);
        return mFragmentImagePagerBinding.getRoot();
    }

    public void observeViewModel(HomeViewModel homeViewModel, int productId){
        homeViewModel.findProductById(getActivity(),productId).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                //TODO: need better product searching for better performance of image searching.
                //TODO: this can be handle better on MVVM architecture, by giving image list and position to HomeViewModel and then set image by data binding in layouts.

                Log.i(TAG, "onChanged: Pager Called ");
                mProduct = product;
                List<Image> imageList = mProduct.getImages();

                if(imageList != null && imageList.size()!=0)
                    Picasso.get().load(imageList.get(mImagePosition).getPathUrl()).into(mFragmentImagePagerBinding.imageView);
                else
                    Picasso.get().load(R.drawable.product_place_holder).into(mFragmentImagePagerBinding.imageView);
            }
        });
    }

}
