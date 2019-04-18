package com.example.ebrah.woocommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.databinding.CategoryItemListBinding;
import com.example.ebrah.woocommerce.model.Category;
import com.example.ebrah.woocommerce.viewmodel.CategoryViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryHolder> {
    private List<Category> mCategoryList;
    private Context mContext;

    public CategoryListAdapter(Context context, List<Category> categoryList) {
        mContext = context;
        mCategoryList = categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.category_item_list, parent, false);
        return new CategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.bind(mCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mCategoryList != null)
            return mCategoryList.size();
        else
            return 0;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{
        private CategoryItemListBinding mCategoryItemListBinding;
        private Category mCategory;

        public CategoryHolder(CategoryItemListBinding categoryItemListBinding) {
            super(categoryItemListBinding.getRoot());
            mCategoryItemListBinding = categoryItemListBinding;
            mCategoryItemListBinding.setCategoryviewmodel(new CategoryViewModel());
        }

        public void bind(Category category){
            mCategory = category;
            mCategoryItemListBinding.getCategoryviewmodel().setCategory(category);
            mCategoryItemListBinding.executePendingBindings();
        }
    }
}

