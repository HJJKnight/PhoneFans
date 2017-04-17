package vod.chunyi.com.phonefans.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.view.HomeBottomController;
import vod.chunyi.com.phonefans.view.UploadImgPopupWindow;

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
    }

    @Override
    public void initVaribles(Intent intent) {
// TODO: 2017/4/17 获取 当前登录用户的相关信息

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.setting_head, R.id.setting_nick_name, R.id.setting_tel, R.id.setting_change_psw, R.id.setting_quit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_head:
                UploadImgPopupWindow popwindow = new UploadImgPopupWindow(this, new View.OnClickListener() {
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
                popwindow.showAtLocation(mSettingLayout, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.setting_nick_name:
                //TODO 弹出自定义 popupwindow
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
}
