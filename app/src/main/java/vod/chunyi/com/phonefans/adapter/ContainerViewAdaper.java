package vod.chunyi.com.phonefans.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vod.chunyi.com.phonefans.ui.fragment.GameFragment;
import vod.chunyi.com.phonefans.ui.fragment.MyPageFragment;
import vod.chunyi.com.phonefans.ui.fragment.SongFragment;

/**
 * Created by knight on 2017/4/5.
 */

public class ContainerViewAdaper extends FragmentPagerAdapter {

    private Context mContext;
    private String[] mTitles;

    public ContainerViewAdaper(Context context, FragmentManager fm, String[] titles) {
        super(fm);
        this.mContext = context;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SongFragment();
            case 1:
                return new GameFragment();
            case 2:
                return new MyPageFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
