package com.example.ebrah.woocommerce.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.databinding.FragmentProductDetailBinding;
import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.repository.ProductRepository;
import com.example.ebrah.woocommerce.viewmodel.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    private static final String PRODUCT_ID = "product_id";
    public static final String TAG = "noob";

    private FragmentProductDetailBinding mFragmentProductDetailBinding;
    private int mProductId;
    private Product mProduct;
    private HomeViewModel mHomeViewModel;

    public static ProductDetailFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, id);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ProductDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mProductId = getArguments().getInt(PRODUCT_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentProductDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        observeViewModel(mHomeViewModel);
        return mFragmentProductDetailBinding.getRoot();
    }

    public void observeViewModel(HomeViewModel homeViewModel){
        homeViewModel.findProductById(getActivity(),mProductId).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mProduct = product;
                mFragmentProductDetailBinding.imageGalleryViewPager.setAdapter(new ImagePagerAdapter(getChildFragmentManager()));
            }
        });

    }

    public class ImagePagerAdapter extends FragmentStatePagerAdapter {
        //TODO: can handle image gallery by PagerAdapter. This might have better performance loading images...

        public ImagePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return ImagePagerFragment.newInstance(mProductId, position);
        }

        @Override
        public int getCount() {
            if(mProduct!= null){
                int imageListSize = mProduct.getImages().size();
                if(imageListSize != 0) {
                    return imageListSize;
                }
                else {
                    return 1;
                }
            }

            else return 1;
        }
    }

}
