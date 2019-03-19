package com.example.ebrah.woocommerce.view;

import androidx.fragment.app.Fragment;

public class WooCommerceActivity extends SingleFragmentActivity{

    @Override
    public Fragment createFragment() {
        return HomeFragment.newInstance();
    }
}
