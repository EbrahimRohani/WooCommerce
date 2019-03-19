package com.example.ebrah.woocommerce.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.databinding.FragmentHomeBinding;
import com.example.ebrah.woocommerce.databinding.ProductListItemBinding;
import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.viewmodel.HomeViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "noob";
    private FragmentHomeBinding mFragmentHomeBinding;
    private HomeViewModel mHomeViewModel;
    private NewestItemsAdapter mNewestItemsAdapter;


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mFragmentHomeBinding.setLifecycleOwner(this);
        List<Product> productsList = mHomeViewModel.getProductList(getActivity());
//        Log.i(TAG, "onCreateView: " + productsList.size());
        if(productsList!= null) {
            mNewestItemsAdapter = new NewestItemsAdapter(productsList);
            mFragmentHomeBinding.recyclerViewNewestProducts.setAdapter(mNewestItemsAdapter);
        }
        observeViewModel(mHomeViewModel);
        Log.i(TAG, "ta inja ");
        return mFragmentHomeBinding.getRoot();

    }

    private void observeViewModel(HomeViewModel homeViewModel) {
        homeViewModel.getProductListLiveData(getActivity()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if(products != null) {
                    Log.i(TAG, "onChanged: Called");
                    mNewestItemsAdapter = new NewestItemsAdapter(products);
                    mNewestItemsAdapter.setProductList(products);
                    mFragmentHomeBinding.recyclerViewNewestProducts.setAdapter(mNewestItemsAdapter);
                    Log.i(TAG, "onChanged: LIST = " + products.size());
                }
            }
        });
    }

    public class NewestViewHolder extends RecyclerView.ViewHolder{
        private ProductListItemBinding mProductListItemBinding;
        private Product mProduct;

        public NewestViewHolder(@NonNull ProductListItemBinding productListItemBinding) {
            super(productListItemBinding.getRoot());
            mProductListItemBinding = productListItemBinding;
        }

        public void bind(Product product){
            mProduct = product;
            mProductListItemBinding.setHomeviewmodel(mHomeViewModel);
            mHomeViewModel.setProduct(product);


        }

    }

    public class NewestItemsAdapter extends RecyclerView.Adapter<NewestViewHolder>{

        List<Product> mProductList;

        public NewestItemsAdapter(List<Product> productList) {
            mProductList = productList;
        }

        public void setProductList(List<Product> productList) {
            mProductList = productList;
        }

        @NonNull
        @Override
        public NewestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ProductListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),R.layout.product_list_item, parent, false);
            return new NewestViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull NewestViewHolder holder, int position) {
            holder.bind(mProductList.get(position));
        }

        @Override
        public int getItemCount() {
            if(mProductList != null)
                return mProductList.size();
            else
                return 0;
        }
    }

//    public class NewestItemsAdapter extends ParentAdapter<Product, FragmentHomeBinding> {
//
//
//        public NewestItemsAdapter(List<Product> mList) {
//            super(mList);
//        }
//
//        @Override
//        public int layoutId() {
//            return R.layout.product_list_item;
//        }
//
//        @Override
//        public ViewModel viewModel() {
//            return new HomeViewModel();
//        }
//
//        @Override
//        public int variableId() {
//            return BR.homeviewmodel;
//        }
//    }
}
