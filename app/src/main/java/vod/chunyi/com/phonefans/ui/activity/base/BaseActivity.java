package vod.chunyi.com.phonefans.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import butterknife.ButterKnife;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.ui.activity.HomeActivity;

/**
 * Created by knight on 2017/4/5.
 */

public abstract class BaseActivity extends AppCompatActivity {


    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initVaribles();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        initVaribles();
        ButterKnife.bind(this);
        initToolBar();
        initViews();
    }

    private void initToolBar() {
        if(this instanceof HomeActivity){
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
    }

}
