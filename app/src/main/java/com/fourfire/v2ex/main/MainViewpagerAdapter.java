package com.fourfire.v2ex.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 45089 on 2018/4/17.
 */

public class MainViewpagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;

    public MainViewpagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " 最新 ";
        switch (position)
        {
            case 0:
                title = " 最新 ";
                break;
            case 1:
                title = " 最热 ";
                break;
            case 2:
                title = " 技术 ";
                break;
            case 3:
                title = " 创意 ";
                break;
            case 4:
                title = " 好玩 ";
                break;
            case 5:
                title = " Apple ";
                break;
            case 6:
                title = " 酷工作 ";
                break;
            case 7:
                title = " 交易 ";
                break;
            case 8:
                title = " 城市 ";
                break;
            case 9:
                title = " 问与答 ";
                break;
            case 10:
                title = " R2 ";
                break;
        }
        return title;
    }
}
