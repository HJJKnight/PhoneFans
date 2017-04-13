package vod.chunyi.com.phonefans.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.adapter.ContainerViewAdaper;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.StatusBarUtils;
import vod.chunyi.com.phonefans.view.HomeBottomController;

/**
 * Created by knight on 2017/4/5.
 */

public class HomeActivity extends BaseActivity {


    @BindView(R.id.tab_title)
    TabLayout mTabTitle;
    @BindView(R.id.fragment_container)
    ViewPager mContainer;
    @BindView(R.id.home_bottom_controller)
    HomeBottomController mController;

    private String[] mTiltes;

    private long oldOutTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initViews() {

        StatusBarUtils.setColor(this, getResources().getColor(R.color.seek_bar_pj));

        mContainer.setAdapter(new ContainerViewAdaper(HomeActivity.this, getSupportFragmentManager(), mTiltes));
        mTabTitle.setupWithViewPager(mContainer);
    }

    @Override
    public void initVaribles() {

        mTiltes = this.getResources().getStringArray(R.array.home_top_title);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - oldOutTime > 1200l) {
                oldOutTime = System.currentTimeMillis();
                Toast.makeText(HomeActivity.this, "再次点击退出应用", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
