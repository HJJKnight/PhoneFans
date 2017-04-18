package vod.chunyi.com.phonefans.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.Request;
import okhttp3.Response;
import vod.chunyi.com.phonefans.PhoneFansApplication;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.User;
import vod.chunyi.com.phonefans.net.OkHttpHelper;
import vod.chunyi.com.phonefans.net.SimpleCallback;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.Constants;
import vod.chunyi.com.phonefans.utils.PicUtils;
import vod.chunyi.com.phonefans.utils.ToastUtil;
import vod.chunyi.com.phonefans.view.HomeBottomController;
import vod.chunyi.com.phonefans.view.UploadImgPopupWindow;
import vod.chunyi.com.phonefans.view.dialog.ModifyUserInfoDialog;

/**
 * Created by knight on 2017/4/17.
 */

public class SettingActivity extends BaseActivity {

    private static final int PHOTO_REQUEST_GALLERY = 1;
    private static final int PHOTO_REQUEST_CAREMA = 2;

    @BindView(R.id.setting_layout)
    LinearLayout mSettingLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.setting_head)
    LinearLayout mHead;
    @BindView(R.id.setting_img_head)
    ImageView mImgHead;
    @BindView(R.id.setting_nick_name)
    LinearLayout mNickName;
    @BindView(R.id.setting_tel)
    LinearLayout mTel;
    @BindView(R.id.setting_change_psw)
    LinearLayout mChangePsw;
    @BindView(R.id.setting_quit)
    LinearLayout mQuit;
    @BindView(R.id.home_bottom_controller)
    HomeBottomController homeBottomController;

    private User mUser;
    private String mUploadUrl;
    //是否是上传图片
    private boolean isUploadImg;

    private ModifyUserInfoDialog mDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews() {

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
                                if (mUser != null) {

                                }
                                break;
                        }
                    }
                });
    }

    @Override
    public void initVaribles(Intent intent) {
        //mUploadUrl = getResources().getString(R.string.upload_headImage_url);
        // TODO: 2017/4/17 获取 当前登录用户的相关信息
        PhoneFansApplication application = PhoneFansApplication.getInstance();
        mUser = application.getUser();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.setting_head, R.id.setting_nick_name, R.id.setting_tel, R.id.setting_change_psw, R.id.setting_quit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_head:
               /* UploadImgPopupWindow popwindow = new UploadImgPopupWindow(this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.pop_btn_take_photo:
                                TakePhoto();
                                break;
                            case R.id.pop_btn_pick_photo:
                                PickPhoto();
                                break;
                        }
                    }
                });
                popwindow.showAtLocation(mSettingLayout, Gravity.BOTTOM, 0, 0);*/
                isUploadImg = true;

                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);

                break;
            case R.id.setting_nick_name:
                //TODO 弹出自定义 popupwindow
                mDialog.setTitle("修改用户昵称").show();

                break;
            case R.id.setting_tel:
                // TODO: 2017/4/17  弹出自定义 popupwindow
                break;
            case R.id.setting_change_psw:

                break;
            case R.id.setting_quit:
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
                        .error(R.mipmap.my_pic)
                        .into(mImgHead);
                Log.e("settingActivity", "photo:" + photos.get(0));
                // TODO: 2017/4/18 图片上传至服务器
                Log.e("base64", PicUtils.ImageToBase64Str(photos.get(0)));

                PicUtils.Base64ToImage(PicUtils.ImageToBase64Str(photos.get(0)), Environment.getExternalStorageDirectory().getPath());
            }
        }
    }

    /**
     * 调用系统拍照
     */
    private void TakePhoto() {
        // 调用系统相机
        Uri imageUri = null;
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);

        imageUri = Uri.fromFile(new File(getApplicationContext().getExternalFilesDir(null), "headImage.jpg"));

        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, PHOTO_REQUEST_CAREMA);
    }

    /**
     * 图片选择
     */
    private void PickPhoto() {
        // 调用本地相册选取照片
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra("crop", true);
        photoPickerIntent.putExtra("scale", true);
        startActivityForResult(photoPickerIntent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 上传操作
     *
     * @param info
     */
    private void UploadHeadImg(String info) {

        Map<String, Object> params = new HashMap<>();

        if (mUser == null) {
            LoginActivity.startActivity(SettingActivity.this);
            ToastUtil.showShort(this, "请先登录");
        }
        params.put(Constants.USER_ID, mUser.getUserPo().getUserId());
        if (isUploadImg) {
            mUploadUrl = getResources().getString(R.string.upload_headImage_url);
            params.put(Constants.USER_IMG, info);
            isUploadImg = !isUploadImg;
        } else {
            mUploadUrl = getResources().getString(R.string.upload_vod_modifyUser);

            /*params.put(Constants.USER_NAME, userName);
            params.put(Constants.USER_PHONE, userPhone);
            params.put(Constants.USER_PSW, userPsw);*/
        }

        OkHttpHelper.getInstance().post(mUploadUrl, params, new SimpleCallback<String>() {
            @Override
            public void onFailure(Request request, Exception e) {
                ToastUtil.showShort(SettingActivity.this, "用户信息修改失败");
            }

            @Override
            public void onSuccess(Response response, String s) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jobj = new JSONObject(s);
                        if (jobj.has("resultCode")) {
                            int resultCode = jobj.getInt("resultCode");
                            if (resultCode == 0) {
                                ToastUtil.showShort(SettingActivity.this, "用户信息修改失败");
                            } else if (resultCode == 1) {

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}
