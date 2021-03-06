package vod.chunyi.com.phonefans.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.OnClick;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.UserBean;
import vod.chunyi.com.phonefans.ui.activity.LoginActivity;
import vod.chunyi.com.phonefans.ui.activity.SettingActivity;
import vod.chunyi.com.phonefans.ui.fragment.base.BaseFragment;
import vod.chunyi.com.phonefans.utils.Constants;
import vod.chunyi.com.phonefans.utils.SharedPreferencesUtils;
import vod.chunyi.com.phonefans.utils.ToastUtil;

/**
 * Created by knight on 2017/4/5.
 */

public class MyPageFragment extends BaseFragment {

    @BindView(R.id.my_head_img)
    ImageView mHeadImg;
    @BindView(R.id.my_login_status)
    TextView mStatus;
    @BindView(R.id.my_head_layout)
    LinearLayout mHeadLayout;
    @BindView(R.id.my_qr_scan)
    LinearLayout mQrScan;
    @BindView(R.id.my_setting)
    LinearLayout mSetting;

    private UserBean mUserBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mypage;
    }

    @Override
    public void initData() {
        //TODO 获取已登录用户信息
        mUserBean = SharedPreferencesUtils.getObject(getHolderActivity(), Constants.USER_INFO, UserBean.class);
    }

    @Override
    public void initViews(View view) {
        if (mUserBean != null) {
            mStatus.setText("已登录");
            Glide.with(getHolderActivity())
                    .load(mUserBean.getUser().getHeadImagUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.mipmap.my_nologin)
                    .into(mHeadImg);
        }

    }

    @OnClick({R.id.my_head_layout, R.id.my_qr_scan, R.id.my_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_head_layout:
                doLogin();
                break;
            case R.id.my_qr_scan:
                // TODO: 2017/4/21 二维码扫描
                break;
            case R.id.my_setting:
                if (mUserBean == null) {
                    ToastUtil.showShort(getHolderActivity(), "请先登录");
                    LoginActivity.startActivity(getHolderActivity());
                } else {
                    SettingActivity.startActivity(getHolderActivity());
                }
                break;
        }
    }

    private void doLogin() {
        // TODO: 2017/4/18 检查当前用户登录信息
        if (mUserBean == null) {
            LoginActivity.startActivity(getHolderActivity());
        }

    }
}
