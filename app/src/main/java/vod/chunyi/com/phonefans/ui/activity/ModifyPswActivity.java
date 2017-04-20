package vod.chunyi.com.phonefans.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.UserBean;
import vod.chunyi.com.phonefans.modle.NetApi;
import vod.chunyi.com.phonefans.modle.NetApiIml;
import vod.chunyi.com.phonefans.modle.NetCode;
import vod.chunyi.com.phonefans.net.SimpleCallback;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.RegexUtils;
import vod.chunyi.com.phonefans.utils.StatusBarUtils;
import vod.chunyi.com.phonefans.utils.ToastUtil;

/**
 * Created by knight on 2017/4/19.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ModifyPswActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.modify_psw_pwd)
    EditText mPsw;
    @BindView(R.id.modify_psw_new_pwd)
    EditText mNewPwd;
    @BindView(R.id.modify_psw_iv_confirm)
    ImageView mConfirm;

    private int userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_psw;
    }

    @Override
    public void initViews() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.seek_bar_pj));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mPsw.setError(getResources().getString(R.string.psw_empty_desc));
                }

                if (!RegexUtils.isLegalPsw(s.toString())) {
                    mPsw.setError(getResources().getString(R.string.psw_illegal_desc));
                }
                if ((s.length() < 6 && s.length() > 1) || s.length() > 12) {
                    mPsw.setError(getResources().getString(R.string.psw_illegal_length_desc));
                }
            }
        });

        mNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mNewPwd.setError(getResources().getString(R.string.psw_empty_desc));
                }

                if (!RegexUtils.isLegalPsw(s.toString())) {
                    mNewPwd.setError(getResources().getString(R.string.psw_illegal_desc));
                }
                if ((s.length() < 6 && s.length() > 1) || s.length() > 12) {
                    mNewPwd.setError(getResources().getString(R.string.psw_illegal_length_desc));
                }

            }
        });
    }

    @Override
    public void initVaribles(Intent intent) {

        if (intent != null) {
            userId = intent.getIntExtra(NetCode.USER_ID, -1);
        }
    }


    @OnClick(R.id.modify_psw_iv_confirm)
    public void onClick() {
        // TODO: 2017/4/19 进行修改密码提交
        String pwd = mPsw.getText().toString().trim();
        String surePwd = mNewPwd.getText().toString().trim();
        if (pwd.equals(surePwd)) {

            NetApi api = NetApiIml.getInstance(ModifyPswActivity.this);
            api.postChangePassword(userId, surePwd, new SimpleCallback<UserBean>() {
                @Override
                public void onFailure(Request request, Exception e) {

                }

                @Override
                public void onSuccess(Response response, UserBean userBean) {
                    Log.e("postChangePassword", userBean.toString());
                    if (response.isSuccessful()) {
                        Snackbar.make(mConfirm, "密码更新成功", Snackbar.LENGTH_LONG)
                                .setAction("知道了", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                }).show();
                    }

                }
            });
        } else {
            ToastUtil.showShort(ModifyPswActivity.this, "两次输入不一致");
        }

    }

    public static void startActivity(Context context, int userId) {
        Intent intent = new Intent(context, ModifyPswActivity.class);
        intent.putExtra(NetCode.USER_ID, userId);
        context.startActivity(intent);
    }


}
