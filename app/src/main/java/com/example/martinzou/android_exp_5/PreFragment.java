package com.example.martinzou.android_exp_5;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * Created by 10630 on 2018/5/25.
 */

public class PreFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从xml文件中加载选项
        addPreferencesFromResource(R.xml.preference);
    }

}
