package com.example.ebrah.woocommerce.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.adapter.ProductListAdapter;
import com.example.ebrah.woocommerce.databinding.FragmentProductListBinding;
import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.model.enums.ListOrder;
import com.example.ebrah.woocommerce.viewmodel.ProductViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    public static final String LIST_ORDER = "list_order";
    private ListOrder mListOrder;
    private ProductListAdapter mProductListAdapter;
    private FragmentProductListBinding mFragmentProductListBinding;
    private ProductViewModel mProductViewModel;
    private int mPageNumber = 1;


    public static ProductListFragment newInstance(ListOrder listOrder) {

        Bundle args = new Bundle();
        args.putSerializable(LIST_ORDER, listOrder);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public ProductListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListOrder = (ListOrder) getArguments().getSerializable(LIST_ORDER);
        mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentProductListBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_product_list, container, false);
        observeProductListByOrder(mProductViewModel, mListOrder);
        return mFragmentProductListBinding.getRoot();
    }

    public void observeProductListByOrder(ProductViewModel productViewModel, ListOrder listOrder){
        productViewModel.getProductListByOrderLiveData(getActivity(), listOrder, mPageNumber).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                updateUI(products);
            }
        });
    }

    public void updateUI(List<Product> products){
        mProductListAdapter = new ProductListAdapter(getActivity(), products);
        mProductListAdapter.setProductList(products);
        mFragmentProductListBinding.singleProductListRecyclerView.setAdapter(mProductListAdapter);
    }

}
