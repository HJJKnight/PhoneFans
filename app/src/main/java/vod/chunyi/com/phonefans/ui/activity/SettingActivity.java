package vod.chunyi.com.phonefans.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.Request;
import okhttp3.Response;
import vod.chunyi.com.phonefans.PhoneFansApplication;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.UserBean;
import vod.chunyi.com.phonefans.modle.NetApi;
import vod.chunyi.com.phonefans.modle.NetApiIml;
import vod.chunyi.com.phonefans.net.SimpleCallback;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.Constants;
import vod.chunyi.com.phonefans.utils.PicUtils;
import vod.chunyi.com.phonefans.utils.SharedPreferencesUtils;
import vod.chunyi.com.phonefans.utils.StatusBarUtils;
import vod.chunyi.com.phonefans.utils.ToastUtil;
import vod.chunyi.com.phonefans.view.CircleImageView;
import vod.chunyi.com.phonefans.view.HomeBottomController;
import vod.chunyi.com.phonefans.view.dialog.ModifyUserInfoDialog;

/**
 * Created by knight on 2017/4/17.
 */

public class SettingActivity extends BaseActivity {

    private static final String SETTING_NICK_NAME = "setting_nick_name";
    private static final String SETTING_PHONE = "setting_phone";

    @BindView(R.id.setting_layout)
    LinearLayout mSettingLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.setting_head)
    LinearLayout mHead;
    @BindView(R.id.setting_img_head1)
    ImageView mImageHead1;
    @BindView(R.id.setting_img_head)
    CircleImageView mImgHead;
    @BindView(R.id.setting_nick_name)
    LinearLayout mNickName;
    @BindView(R.id.setting_text_nick_name)
    TextView mTvNickName;
    @BindView(R.id.setting_tel)
    LinearLayout mPhone;
    @BindView(R.id.setting_text_phone)
    TextView mTvPhone;
    @BindView(R.id.setting_change_psw)
    LinearLayout mChangePsw;
    @BindView(R.id.setting_quit)
    LinearLayout mQuit;
    @BindView(R.id.home_bottom_controller)
    HomeBottomController homeBottomController;

    private UserBean mUserBean;

    private NetApi api;

    private ModifyUserInfoDialog mDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews() {

        StatusBarUtils.setColor(this, getResources().getColor(R.color.seek_bar_pj));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDialog = new ModifyUserInfoDialog(this)
                .setOnButtonClickListener(new ModifyUserInfoDialog.OnButtonClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.view_modify_user_info_cancle:
                                mDialog.dismiss();
                                break;
                            case R.id.view_modify_user_info_confirm:
                                // TODO: 2017/4/19 需要对当前的userInfo 进行验证
                                if (mUserBean != null) {
                                    ToastUtil.showShort(SettingActivity.this, mDialog.getInputText());

                                    if (SETTING_NICK_NAME.equals(mDialog.getTag())) {
                                        mTvNickName.setText(mDialog.getInputText());
                                        mUserBean.getUser().setUserName(mDialog.getInputText());

                                    } else {
                                        mTvPhone.setText(mDialog.getInputText());
                                        mUserBean.getUser().setPhoneNo(mDialog.getInputText());
                                    }
                                }
                                upLoadUserInfo();
                                mDialog.dismiss();
                                break;
                        }
                    }
                });

        if (mUserBean != null) {
            Log.e("userName", mUserBean.getUser().getUserName());
            Log.e("img", mUserBean.getUser().getHeadImagUrl());

            Glide.with(SettingActivity.this)
                    .load(mUserBean.getUser().getHeadImagUrl())
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(mImgHead);
            Glide.with(SettingActivity.this)
                    .load(mUserBean.getUser().getHeadImagUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(mImageHead1);

            mTvNickName.setText(mUserBean.getUser().getUserName());
            mTvPhone.setText(mUserBean.getUser().getPhoneNo());
        }
    }

    @Override
    public void initVaribles(Intent intent) {
        // TODO: 2017/4/17 获取 当前登录用户的相关信息

        mUserBean = SharedPreferencesUtils.getObject(SettingActivity.this, Constants.USER_INFO, UserBean.class);
        api = NetApiIml.getInstance(SettingActivity.this);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.setting_head, R.id.setting_nick_name, R.id.setting_tel, R.id.setting_change_psw, R.id.setting_quit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_head:

                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);

                break;
            case R.id.setting_nick_name:
                mDialog.setTag(SETTING_NICK_NAME);
                mDialog.setTitle("修改用户昵称").setInputText(mTvNickName.getText().toString()).show();

                break;
            case R.id.setting_tel:
                mDialog.setTag(SETTING_PHONE);
                mDialog.setTitle("修改手机号码").setInputText(mTvPhone.getText().toString()).show();
                break;
            case R.id.setting_change_psw:
                ModifyPswActivity.startActivity(SettingActivity.this, mUserBean.getUser().getUserId());
                break;
            case R.id.setting_quit:
                // TODO: 2017/4/21 需要弹出退出确认框
                SharedPreferencesUtils.put(SettingActivity.this, Constants.USER_PHONE, mUserBean.getUser().getPhoneNo());
                SharedPreferencesUtils.remove(SettingActivity.this, Constants.USER_INFO);
                LoginActivity.startActivity(SettingActivity.this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                // TODO: 2017/4/18 图片先进行压缩，再设置到ImageView
                Glide.with(this)
                        .load(photos.get(0))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.mipmap.my_pic)
                        .into(mImgHead);


                Log.e("settingActivity", "photo:" + photos.get(0));
                // TODO: 2017/4/18 图片上传至服务器
                Log.e("base64", PicUtils.ImageToBase64Str(photos.get(0)));
                UploadHeadImg(PicUtils.ImageToBase64Str(photos.get(0)));
                //PicUtils.Base64ToImage(PicUtils.ImageToBase64Str(photos.get(0)), Environment.getExternalStorageDirectory().getPath());
            }
        }
    }

    /**
     * 上传用户头像操作
     *
     * @param base64Str
     */
    private void UploadHeadImg(String base64Str) {

        if (mUserBean == null) {
            LoginActivity.startActivity(SettingActivity.this);
            ToastUtil.showShort(this, "请先登录");
        }

        api.postUserHeadImg(mUserBean.getUser().getUserId(), base64Str, new SimpleCallback<String>() {
            @Override
            public void onFailure(Request request, Exception e) {
                ToastUtil.showShort(SettingActivity.this, "用户头像修改失败");
            }

            @Override
            public void onSuccess(Response response, String s) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jobj = new JSONObject(s);
                        if (jobj.has("resultCode")) {
                            int resultCode = jobj.getInt("resultCode");
                            if (resultCode == 0) {
                                ToastUtil.showShort(SettingActivity.this, "用户头像修改失败");
                            } else if (resultCode == 1) {
                                refreshLocalUserInfo();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 上传用户信息
     */
    private void upLoadUserInfo() {
        if (mUserBean == null) {
            LoginActivity.startActivity(SettingActivity.this);
            ToastUtil.showShort(this, "请先登录");
        }

        api.postUserInfo(mUserBean, new SimpleCallback<UserBean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, UserBean userBean) {
                if (response.isSuccessful()) {
                    ToastUtil.showShort(SettingActivity.this, "用户信息更新成功");
                    // TODO: 2017/4/21 刷新了本地数据 应该也反应到界面上
                    refreshLocalUserInfo();
                }
            }
        });
    }

    /**
     * 刷新本地用户信息
     */
    private void refreshLocalUserInfo() {
        api.postQueryUserByNumber(mUserBean.getUser().getPhoneNo(), new SimpleCallback<UserBean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, UserBean user) {
                if (response.isSuccessful()) {
                    try {
                        SharedPreferencesUtils.remove(SettingActivity.this, Constants.USER_INFO);
                        SharedPreferencesUtils.putObj(SettingActivity.this, Constants.USER_INFO, user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
