package com.example.ebrah.woocommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.databinding.ProductListItemBinding;
import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.viewmodel.ProductViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductItemHolder> {
    private List<Product> mProductList;
    private Context mContext;

    public ProductListAdapter(Context context, List<Product> productList) {
        mContext = context;
        mProductList = productList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    @NonNull
    @Override
    public ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductListItemBinding productListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.product_list_item, parent, false);
        return new ProductItemHolder(productListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemHolder holder, int position) {
        holder.bind(mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mProductList != null)
            return mProductList.size();
        else
            return 0;
    }



    public class ProductItemHolder extends RecyclerView.ViewHolder{
        private ProductListItemBinding mProductListItemBinding;
        private Product mProduct;

        public ProductItemHolder(ProductListItemBinding productListItemBinding) {
            super(productListItemBinding.getRoot());
            mProductListItemBinding = productListItemBinding;
            mProductListItemBinding.setProductviewmodel(new ProductViewModel());

        }

        public void bind(Product product){
            //TODO: the first 7 products are shown correctly, but the rest will be repeating of the first 3 ones again...
            //TODO: must consider recycler view pagination
            mProduct = product;
            mProductListItemBinding.getProductviewmodel().setProduct(product);
            mProductListItemBinding.executePendingBindings();
        }
    }

}




