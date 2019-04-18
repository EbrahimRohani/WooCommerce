package com.example.ebrah.woocommerce.view;


import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.NumberPicker;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.adapter.ProductListAdapter;
import com.example.ebrah.woocommerce.databinding.FragmentHomeBinding;
import com.example.ebrah.woocommerce.model.Product;
import com.example.ebrah.woocommerce.model.enums.ListOrder;
import com.example.ebrah.woocommerce.repository.ProductRepository;
import com.example.ebrah.woocommerce.viewmodel.ProductViewModel;
import com.google.android.material.navigation.NavigationView;

import java.time.DateTimeException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "noob";
    private FragmentHomeBinding mFragmentHomeBinding;
    private ProductViewModel mProductViewModel;
    private ProductListAdapter mProductListAdapter;
    private DrawerLayout mDrawerLayout;
    private LinearLayoutManager mLayoutManager;


    //RecyclerView Pagination
    private OnScrollListener mOnScrollListener;
    private int mPageNumber = 1;
    private int itemCount = 0;
    private boolean mIsLoading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previousTotal = 0;
    private int viewThreshold = 10;


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
        setHasOptionsMenu(true);
        mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);

        Toolbar toolbar = mFragmentHomeBinding.toolbar;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mDrawerLayout = mFragmentHomeBinding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mFragmentHomeBinding.navigationView.setNavigationItemSelectedListener(this);

        mFragmentHomeBinding.setLifecycleOwner(this);
        observeViewModel(mProductViewModel);


        //pagination:
        // mFragmentHomeBinding.recyclerViewNewestProducts.setLayoutManager(mLayoutManager);
        //mFragmentHomeBinding.recyclerViewNewestProducts.addOnScrollListener(getOnScrollListener(ProductListFragment.ListOrder.ORDER_BY_DATE));

        //mFragmentHomeBinding.recyclerViewMostRatedProducts.setLayoutManager(mLayoutManager);
        //mFragmentHomeBinding.recyclerViewMostRatedProducts.addOnScrollListener(getOnScrollListener(ProductListFragment.ListOrder.ORDER_BY_RATING));

        /* testing */
        mFragmentHomeBinding.recyclerViewMostPopularProducts.setLayoutManager(mLayoutManager);
        mFragmentHomeBinding.recyclerViewMostPopularProducts.addOnScrollListener(getOnScrollListener(ListOrder.ORDER_BY_POPULARITY));


        //each recycler view must get a new layoutManager for itself... so the current code for them will throw an exception...


        return mFragmentHomeBinding.getRoot();


    }

    private void observeViewModel(ProductViewModel productViewModel) {
        updateUI(productViewModel, ListOrder.ORDER_BY_DATE);
        updateUI(productViewModel, ListOrder.ORDER_BY_POPULARITY);
        updateUI(productViewModel, ListOrder.ORDER_BY_RATING);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //TODO: must be converted to MVVM architecture...

        switch (menuItem.getItemId()) {
            case R.id.newest_products_nav_item:
                replaceFragment(ProductListFragment.newInstance(ListOrder.ORDER_BY_DATE));
                break;

            case R.id.most_popular_products_nav_item:
                replaceFragment(ProductListFragment.newInstance(ListOrder.ORDER_BY_POPULARITY));
                break;

            case R.id.most_rated_products_nav_item:
                replaceFragment(ProductListFragment.newInstance(ListOrder.ORDER_BY_RATING));
                break;

            case R.id.categories_nav_item:
                replaceFragment(CategoryListFragment.newInstance());
        }
        return false;

    }

    //for pagination
    public OnScrollListener getOnScrollListener(final ListOrder listOrder) {
        return new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (mIsLoading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            mPageNumber++;
                            updateUIByOrder(mProductViewModel, listOrder);
                            if(mPageNumber == ProductRepository.getInstance(getActivity()).getMaxNumberOfPages(listOrder, mPageNumber))
                                mIsLoading = true;
                        }
                    }
                }
            }
        };
    }


    private void replaceFragment(Fragment fragment) {
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.single_fragment_container, fragment).addToBackStack(null).commit();
    }

    private void updateUI(final ProductViewModel productViewModel, final ListOrder listOrder) {
        productViewModel.getProductListByOrderLiveData(getActivity(), listOrder, mPageNumber).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mProductListAdapter = new ProductListAdapter(getContext(), products);
                mProductListAdapter.setProductList(products);
                switch (listOrder) {
                    case ORDER_BY_DATE:
                        mFragmentHomeBinding.recyclerViewNewestProducts.setAdapter(mProductListAdapter);
                        break;

                    case ORDER_BY_POPULARITY:
                        mFragmentHomeBinding.recyclerViewMostPopularProducts.setAdapter(mProductListAdapter);
                        break;

                    case ORDER_BY_RATING:
                        mFragmentHomeBinding.recyclerViewMostRatedProducts.setAdapter(mProductListAdapter);
                        break;
                }

            }
        });
    }

    //used in pagination method
    private void updateUIByOrder(ProductViewModel productViewModel, ListOrder listOrder) {
        switch (listOrder) {
            case ORDER_BY_DATE:
                updateUI(productViewModel, listOrder);
                break;

            case ORDER_BY_POPULARITY:
                updateUI(productViewModel, listOrder);
                break;

            case ORDER_BY_RATING:
                updateUI(productViewModel, listOrder);
                break;
        }

    }


    //    public class NewestItemsAdapter extends ParentAdapter<Product, ProductListItemBinding, ProductViewModel>{
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
//        public ProductViewModel viewModel() {
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
