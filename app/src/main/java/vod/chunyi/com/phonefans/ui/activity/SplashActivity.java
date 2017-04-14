package vod.chunyi.com.phonefans.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.service.DBOperationService;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.FileUtils;
import vod.chunyi.com.phonefans.utils.StatusBarUtils;

/**
 * Created by knight on 2017/4/11.
 */

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews() {
        StatusBarUtils.setImage(this);
    }

    @Override
    public void initVaribles(Intent intent) {

        // 从 assets 文件夹中复制 db 文件到本地文件夹
        copyDB(getResources().getString(R.string.db_name));

        // 检验数据库版本 如果不一致进行更换
        DBOperationService.CheckDBVersion(getApplicationContext());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.startActivity(SplashActivity.this);
                finish();
            }
        }, 2000);
    }


    /**
     * 从 assets 文件夹里面复制 DB 文件
     *
     * @param dbName
     */
    private void copyDB(String dbName) {

        File destFile = new File(getFilesDir(), dbName);
        if (!destFile.exists()) {
            Log.e("copyDB","copyDB");
            try {
                InputStream in = getAssets().open(dbName);

                FileOutputStream out = new FileOutputStream(destFile);
                FileUtils.copyFile(in, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("copyDB","not_copyDB");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
