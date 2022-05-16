package com.hlbw.car_system.ui.main.none;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlbw.car_system.R;
import com.hlbw.car_system.ui.main.setting.SettingFragment;

import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by dell on 2018/12/29.
 */

public class NoneFragment2 extends SupportFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fra_load, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(SettingFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, new SettingFragment());
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }
}
