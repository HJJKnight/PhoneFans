package vod.chunyi.com.phonefans.net;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by knight on 2017/4/10.
 */

public abstract class DownloadCallback<T> extends BaseCallback<T> {

    @Override
    public void onBeforeRequest(Request request) {

    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onError(Response response, int code, Exception e) {

    }
}
