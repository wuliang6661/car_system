package com.hlbw.car_system.ui.main.none;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hlbw.car_system.R;
import com.hlbw.car_system.ui.main.home.HomeFragment;

import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by wuliang on 2018/12/29.
 * <p>
 * 容器Fragment
 */

public class NoneFragment1 extends SupportFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_load, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(HomeFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, new HomeFragment());
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }
}
