package com.example.ebrah.woocommerce.view;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.adapter.CategoryListAdapter;
import com.example.ebrah.woocommerce.databinding.FragmentCategoryListBinding;
import com.example.ebrah.woocommerce.model.Category;
import com.example.ebrah.woocommerce.viewmodel.CategoryViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment {

    private FragmentCategoryListBinding mFragmentCategoryListBinding;
    private CategoryViewModel mCategoryViewModel;
    private CategoryListAdapter mCategoryListAdapter;

    public static CategoryListFragment newInstance() {

        Bundle args = new Bundle();

        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public CategoryListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentCategoryListBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_category_list, container, false);
        observerViewModel(mCategoryViewModel);
        return mFragmentCategoryListBinding.getRoot();
    }

    public void observerViewModel(CategoryViewModel categoryViewModel){
        categoryViewModel.getCategoryListMutableLiveData(getActivity()).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                updateUI(categories);
            }
        });
    }

    public void updateUI(List<Category> categoryList){
        mCategoryListAdapter = new CategoryListAdapter(getActivity(), categoryList);
        mFragmentCategoryListBinding.recyclerViewCategoryList.setAdapter(mCategoryListAdapter);
    }

}
