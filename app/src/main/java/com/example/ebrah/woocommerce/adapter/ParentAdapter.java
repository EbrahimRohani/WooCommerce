package com.example.ebrah.woocommerce.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ParentAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter<ParentAdapter.ParentViewHolder<B>> {

    List<M> mList;

    @LayoutRes
    public abstract int layoutId();

    public abstract ViewModel viewModel();

    public abstract int variableId();

    public ParentAdapter(List<M> mList) {
        this.mList = mList;
    }

    public void setList(List<M> mList) {
        this.mList = mList;
        notifyItemRangeChanged(0, mList.size());
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId(), parent, false);
        return new ParentViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        holder.getBinding().setVariable(variableId(), viewModel());
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return 0;
    }



    public static class ParentViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private V v;

        public ParentViewHolder(V v) {
            super(v.getRoot());
        }

        private V getBinding() {
            return v;
        }
    }
}
