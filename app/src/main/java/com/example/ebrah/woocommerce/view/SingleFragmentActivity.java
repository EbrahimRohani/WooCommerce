package com.example.ebrah.woocommerce.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.ebrah.woocommerce.R;
import com.example.ebrah.woocommerce.databinding.ActivitySingleFragmentActivityBinding;
import com.example.ebrah.woocommerce.databinding.FragmentHomeBinding;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private ActivitySingleFragmentActivityBinding mActivitySingleFragmentActivityBinding;

    public abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySingleFragmentActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_single_fragment_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentById(R.id.single_fragment_container)== null)
            fragmentManager.beginTransaction().add(R.id.single_fragment_container, createFragment()).commit();
    }
}
