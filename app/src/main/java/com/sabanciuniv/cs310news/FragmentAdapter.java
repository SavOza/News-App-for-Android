package com.sabanciuniv.cs310news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {


    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        Bundle bundle = new Bundle();
        Fragment fragment = new NewsCategoryFragment();

        switch (position){
            case 0: //Econ, id 1
                bundle.putInt("id",1);
                fragment.setArguments(bundle);
                return fragment;
            case 1: //Sport, id 2
                bundle.putInt("id",2);
                fragment.setArguments(bundle);
                return fragment;
            case 2: //Pol, id 3
                bundle.putInt("id",3);
                fragment.setArguments(bundle);
                return fragment;
            default:
                bundle.putInt("id",1);
                fragment.setArguments(bundle);
                return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
