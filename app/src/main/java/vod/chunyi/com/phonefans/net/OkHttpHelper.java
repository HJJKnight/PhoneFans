package vod.chunyi.com.phonefans.net;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringDef;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vod.chunyi.com.phonefans.utils.FileUtils;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by knight on 2017/4/10.
 */

public class OkHttpHelper {


    private static OkHttpHelper mInstance;
    private OkHttpClient mHttpClient;

    private Gson mGson;

    private Handler mHandler;

    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper() {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());

    }

    public static OkHttpHelper getInstance() {
        return mInstance;
    }

    /**
     * Post 请求数据
     *
     * @param url
     * @param params
     * @param callback
     */
    public void post(String url, Map<String, Object> params, BaseCallback callback) {
        Request request = buildPostRequset(url, params);
        doRequest(request, callback);
    }

    /**
     * Get 请求数据，不带参数
     *
     * @param url
     * @param callback
     */
    public void get(String url, BaseCallback callback) {
        get(url, null, callback);
    }

    /**
     * Get 请求数据，带参数
     *
     * @param url
     * @param params
     * @param callback
     */
    public void get(String url, Map<String, Object> params, BaseCallback callback) {
        Request request = buildGetRequset(url, params);
        doRequest(request, callback);
    }

    /**
     * 执行下载操作
     *
     * @param url
     * @param destFileDir
     * @param callback
     */
    public void downFile(final String url, final String destFileDir, final BaseCallback callback) {
        final Request request = new Request.Builder().url(url).build();

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    double current = 0;
                    double total = response.body().contentLength();
                    File file = new File(destFileDir, FileUtils.getFileName(url));

                    if (total == file.length()) {
                        callBackProgress(total, file.length(), callback);
                        callBackSuccess(response, file, callback);
                        return;
                    }

                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);

                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        callBackProgress(total, current, callback);
                    }
                    fos.flush();

                    callBackSuccess(response, file, callback);

                } catch (IOException e) {
                    callBackFailure(request, e, callback);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    /**
     * 执行请求方法
     *
     * @param request
     * @param callback
     */
    public void doRequest(final Request request, final BaseCallback callback) {

        callback.onBeforeRequest(request);
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackFailure(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBackResponse(response, callback);

                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    if (callback.mType == String.class) {
                        callBackSuccess(response, resultStr, callback);
                    } else {
                        try {
                            Object obj = mGson.fromJson(resultStr, callback.mType);
                            callBackSuccess(response, obj, callback);
                        } catch (com.google.gson.JsonParseException e) { // Json解析的错误
                            callBackError(response, e, callback);
                        }
                    }
                } else {
                    callBackError(response, null, callback);
                }
            }
        });

    }

    private void callBackFailure(final Request request, final IOException e, final BaseCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }

    private void callBackError(final Response response, final Exception e, final BaseCallback callback) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }

    private void callBackSuccess(final Response response, final Object obj, final BaseCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }

    private void callBackProgress(final double total, final double current, final BaseCallback callback) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onProgress(total, current);
            }
        });
    }

    private void callBackResponse(final Response response, final BaseCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 构建 Get 请求Request 对象
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildGetRequset(String url, Map<String, Object> params) {
        return buildRequest(url, GET, params);
    }

    /**
     * 构建 Post 请求Request 对象
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildPostRequset(String url, Map<String, Object> params) {
        return buildRequest(url, POST, params);
    }

    /**
     * 构建 请求
     *
     * @param url
     * @param methodType
     * @param params
     * @return
     */
    private Request buildRequest(String url, @HttpMethod String methodType, Map<String, Object> params) {

        Request.Builder builder = new Request.Builder().url(url);

        if (methodType == POST) {
            RequestBody requestBody = buildFormData(params);
            builder.post(requestBody);

        } else if (methodType == GET) {
            url = buildeUrlParams(url, params);
            builder.url(url);
            builder.get();
        }
        return builder.build();
    }

    /**
     * 构建 get 请求带参数的 url地址
     *
     * @param url
     * @param params
     * @return
     */
    private String buildeUrlParams(String url, Map<String, Object> params) {
        if (params != null) {

            StringBuilder urlBuilder = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                urlBuilder.append(entry.getKey() + "=" + (entry.getValue() == null ? "" : entry.getValue().toString()));
                urlBuilder.append("&");
            }

            String s = urlBuilder.toString();
            if (s.endsWith("&")) {
                s = s.substring(0, s.length() - 1);
            }

            if (url.indexOf("?") > 0) {
                url = url + "&" + s;
            } else {
                url = url + "?" + s;
            }

        }

        return url;
    }

    /**
     * 构建一个 Post 表单请求体
     *
     * @param params
     * @return
     */
    private RequestBody buildFormData(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {

                builder.add(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
            }
        }
        return builder.build();
    }

    public static final String POST = "post";
    public static final String GET = "get";

    @Retention(SOURCE)
    @StringDef({POST, GET})
    public @interface HttpMethod {
    }
}
