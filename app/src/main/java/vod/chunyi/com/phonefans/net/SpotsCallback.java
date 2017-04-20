package vod.chunyi.com.phonefans.net;

import android.content.Context;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;
import vod.chunyi.com.phonefans.R;

/**
 * Created by knight on 2017/4/20.
 */

public abstract class SpotsCallback<T> extends SimpleCallback<T> {

    private SpotsDialog mDialog;

    private Context mContext;

    public SpotsCallback(Context context) {
        this.mContext = context;
        initSpotsDialog();
    }

    private void initSpotsDialog() {
        mDialog = new SpotsDialog(mContext, "加载中...");

    }

    public void showDialog() {
        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

    public void setLoadingMessage(String msg) {
        mDialog.setMessage(msg);
    }

    public void setLoadingMessage(int resId) {
        mDialog.setMessage(mContext.getString(resId));
    }

    @Override
    public void onBeforeRequest(Request request) {
        showDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }
}
