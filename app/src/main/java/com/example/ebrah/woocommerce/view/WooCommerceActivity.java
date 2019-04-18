package com.example.ebrah.woocommerce.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class WooCommerceActivity extends SingleFragmentActivity{

    @Override
    public Fragment createFragment() {
        return HomeFragment.newInstance();
    }
}
