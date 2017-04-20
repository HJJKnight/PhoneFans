package vod.chunyi.com.phonefans.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.UserBean;
import vod.chunyi.com.phonefans.modle.NetApi;
import vod.chunyi.com.phonefans.modle.NetApiIml;
import vod.chunyi.com.phonefans.modle.NetCode;
import vod.chunyi.com.phonefans.net.SpotsCallback;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.Constants;
import vod.chunyi.com.phonefans.utils.SharedPreferencesUtils;
import vod.chunyi.com.phonefans.utils.StatusBarUtils;
import vod.chunyi.com.phonefans.utils.ToastUtil;

/**
 * Created by knight on 2017/4/7.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_text_My)
    TextView mTvMy;
    @BindView(R.id.login_text_tel)
    EditText mEtTel;
    @BindView(R.id.login_text_psw)
    EditText mEtPsw;
    @BindView(R.id.login_btn_login)
    ImageButton mBtnLogin;
    @BindView(R.id.login_btn_bind)
    ImageButton mBtnBind;
    @BindView(R.id.login_text_register)
    TextView mTvRegister;
    @BindView(R.id.login_text_forget)
    TextView mTvForget;
    @BindView(R.id.login_img_logo)
    ImageView loginImgLogo;


    private NetApi api;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        StatusBarUtils.setImage(this);

        mEtTel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO: 2017/4/20 logo 的收缩
            }
        });
    }

    @Override
    public void initVaribles(Intent intent) {
        String phone = SharedPreferencesUtils.get(LoginActivity.this, Constants.USER_PHONE, "").toString();
        if (!TextUtils.isEmpty(phone)) {
            mEtTel.setText(phone);
            mEtTel.setSelection(phone.length());
        }

        api = NetApiIml.getInstance(LoginActivity.this);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.login_text_My, R.id.login_text_tel, R.id.login_text_psw, R.id.login_btn_login, R.id.login_btn_bind, R.id.login_text_register, R.id.login_text_forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_text_My:
                break;
            case R.id.login_text_tel:

                break;
            case R.id.login_text_psw:
                break;
            case R.id.login_btn_login:
                doLogin();
                break;
            case R.id.login_btn_bind:
                break;
            case R.id.login_text_register:
                break;
            case R.id.login_text_forget:
                break;
        }
    }

    /**
     * 进行登录操作
     */
    private void doLogin() {
        String account = mEtTel.getText().toString().trim();
        String psw = mEtPsw.getText().toString().trim();

        api.postLogin(account, psw, new SpotsCallback<UserBean>(LoginActivity.this) {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, UserBean userBean) {
                if (response.isSuccessful()) {
                    Log.e("resultCode", userBean.getResultCode() + "");
                    if (userBean.getResultCode() == NetCode.SUCCESS_CODE) {
                        try {
                            SharedPreferencesUtils.putObj(LoginActivity.this, Constants.USER_INFO, userBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        HomeActivity.startActivity(LoginActivity.this);
                    } else {
                        ToastUtil.showShort(LoginActivity.this, "账号或密码错误");
                    }
                }

            }
        });
    }


}
