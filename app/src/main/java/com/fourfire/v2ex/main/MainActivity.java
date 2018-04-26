package com.fourfire.v2ex.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.fourfire.v2ex.R;
import com.fourfire.v2ex.main.page.PageFragment;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;

import static com.fourfire.v2ex.util.ID.PAGE;

/**
 * Created by 45089 on 2018/4/17.
 */

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Connector.getDatabase();
        init();
    }

    private void init() {
        tabLayout = findViewById(R.id.main_toptabs);
        viewPager = findViewById(R.id.main_viewpager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            fragments.add(new PageFragment());
            Bundle bundle = new Bundle();
            bundle.putInt(PAGE, i);
            fragments.get(i).setArguments(bundle);
        }
        MainViewpagerAdapter mainViewpagerAdapter = new MainViewpagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mainViewpagerAdapter);
        viewPager.setOffscreenPageLimit(11);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(" 最新 ");
        tabLayout.getTabAt(1).setText(" 最热 ");
        tabLayout.getTabAt(2).setText(" 技术 ");
        tabLayout.getTabAt(3).setText(" 创意 ");
        tabLayout.getTabAt(4).setText(" 好玩 ");
        tabLayout.getTabAt(5).setText(" Apple ");
        tabLayout.getTabAt(6).setText(" 酷工作 ");
        tabLayout.getTabAt(7).setText(" 交易 ");
        tabLayout.getTabAt(8).setText(" 城市 ");
        tabLayout.getTabAt(9).setText(" 问与答 ");
        tabLayout.getTabAt(10).setText(" R2 ");
    }
}
