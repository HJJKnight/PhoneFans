package vod.chunyi.com.phonefans.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.ui.activity.SettingActivity;
import vod.chunyi.com.phonefans.ui.fragment.base.BaseFragment;

/**
 * Created by knight on 2017/4/5.
 */

public class MyPageFragment extends BaseFragment {

    @BindView(R.id.my_head_img)
    ImageView mHeadImg;
    @BindView(R.id.my_qr_scan)
    LinearLayout mQrScan;
    @BindView(R.id.my_setting)
    LinearLayout mSetting;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mypage;
    }

    @Override
    public void initData() {
        //TODO 获取已登录用户信息
    }

    @Override
    public void initViews(View view) {

    }


    @OnClick({R.id.my_head_img, R.id.my_qr_scan, R.id.my_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_head_img:
                break;
            case R.id.my_qr_scan:
                break;
            case R.id.my_setting:
                SettingActivity.startActivity(getHolderActivity());
                break;
        }
    }
}
