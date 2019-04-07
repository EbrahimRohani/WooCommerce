package com.example.ebrah.woocommerce.view;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
    private ProductsAdapter mNewestProductsAdapter;
    private ProductsAdapter mMostPopularProductsAdapter;
    private ProductsAdapter mMostRatedProductAdapter;


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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mFragmentHomeBinding.toolbar);

        mFragmentHomeBinding.setLifecycleOwner(this);
        observeViewModel(mHomeViewModel);
        return mFragmentHomeBinding.getRoot();


    }

    private void observeViewModel(HomeViewModel homeViewModel) {
        homeViewModel.getProductListByDateLiveData(getActivity()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mNewestProductsAdapter = new ProductsAdapter(products);
                mNewestProductsAdapter.setProductList(products);
                mFragmentHomeBinding.recyclerViewNewestProducts.setAdapter(mNewestProductsAdapter);
            }
        });

        homeViewModel.getProductListByPopularityLiveData(getActivity()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mMostPopularProductsAdapter = new ProductsAdapter(products);
                mMostPopularProductsAdapter.setProductList(products);
                mFragmentHomeBinding.recyclerViewMostPopularProducts.setAdapter(mMostPopularProductsAdapter);
            }
        });

        homeViewModel.getProductListByRatingLiveData(getActivity()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mMostRatedProductAdapter = new ProductsAdapter(products);
                mMostRatedProductAdapter.setProductList(products);
                mFragmentHomeBinding.recyclerViewMostRatedProducts.setAdapter(mMostRatedProductAdapter);
            }
        });
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private ProductListItemBinding mProductListItemBinding;
        private Product mProduct;

        public ProductsViewHolder(@NonNull ProductListItemBinding productListItemBinding) {
            super(productListItemBinding.getRoot());
            mProductListItemBinding = productListItemBinding;

            productListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: must fix ImageView unclickable issue.
                    getFragmentManager().beginTransaction().replace(R.id.single_fragment_container, ProductDetailFragment.newInstance(mProduct.getId())).addToBackStack("hello").commit();
                }
            });
        }

        public void bind(Product product) {
            //TODO: must fix initial item bindings which binds the third item of every list for all the list, and gets fixed by swiping and refreshing...
            //TODO: must consider recycler view pagination
            mProduct = product;
            mProductListItemBinding.setHomeviewmodel(mHomeViewModel);
            mHomeViewModel.setProduct(product);
            mFragmentHomeBinding.executePendingBindings();
        }

    }

    public class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {

        List<Product> mProductList;

        public ProductsAdapter(List<Product> productList) {
            mProductList = productList;
        }

        public void setProductList(List<Product> productList) {
            mProductList = productList;
        }

        @NonNull
        @Override
        public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ProductListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.product_list_item, parent, false);
            return new ProductsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
            holder.bind(mProductList.get(position));
        }

        @Override
        public int getItemCount() {
            if (mProductList != null)
                return mProductList.size();
            else
                return 0;
        }
    }

//    public class NewestItemsAdapter extends ParentAdapter<Product, ProductListItemBinding, HomeViewModel>{
//
//        public NewestItemsAdapter(List<Product> products) {
//            super(products);
//        }
//
//        @Override
//        public int layoutId() {
//            return R.layout.product_list_item;
//        }
//
//        @Override
//        public HomeViewModel viewModel() {
//            return null;
//        }
//
//        @Override
//        public int variableId() {
//            return BR.homeviewmodel;
//        }
//
//        @Override
//        public void setModel(Product product) {
//            viewModel().setProduct(product);
//        }
//
//
//    }

}
