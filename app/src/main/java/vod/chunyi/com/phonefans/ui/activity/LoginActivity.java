package vod.chunyi.com.phonefans.ui.activity;

import android.content.Context;
import android.content.Intent;

import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;

/**
 * Created by knight on 2017/4/7.
 */

public class LoginActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initVaribles(Intent intent) {

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
