package vod.chunyi.com.phonefans;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by knight on 2017/4/7.
 */

public class DBOperationService extends IntentService {


    public static final String CHECK_DB_VERSION = "check_db_version";

    private static final String CHECK_DB_URL = "check_db_url";

    private OkHttpClient client = new OkHttpClient.Builder().build();


    public DBOperationService() {
        super("DBOperationService");
    }

    public static void CheckDBVersion(Context context) {
        Intent intent = new Intent(context, DBOperationService.class);
        intent.setAction(CHECK_DB_VERSION);
        intent.putExtra(CHECK_DB_URL, context.getResources().getString(
                R.string.fans_data_url));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (CHECK_DB_VERSION.equals(action)) {
                doCheckVersion(intent.getStringExtra(CHECK_DB_URL));
            }

        }
    }

    private void doCheckVersion(String verisonUrl) {

        try {
            Request request = new Request.Builder().url(verisonUrl).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                Log.e("doCheckVersion",result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

/*
    *//**
     * 功能：连接云端 返回json 判断数据库版本
     *//*
    public void checkDbVersion() {

        new AsyncTask<String, Void, Void>() {

            Message message = new Message();

            @Override
            protected Void doInBackground(String... params) {

                String url = params[0];

                HttpJSONClient jsonClient = new HttpJSONClient();
                JSONObject backLogMessage = jsonClient.post(url);

                // 没有返回信息 未连接上
                if (backLogMessage == null) {
                    message.what = CONNECT_ERROR;
                } else {
                    Log.d(TAG, "數據庫版本信息json=====" + backLogMessage.toString());
                }

                // 1获取储存的数据库版本
                String mVersionNo = sp.getString("mVersionNo", "");

                // 2获取服务器的数据库版本
                if (backLogMessage != null && backLogMessage.has("dbVersion")
                        && backLogMessage.has("dbVersionName")) {
                    try {
                        String versionNo = backLogMessage
                                .getString("dbVersion");

                        // 3 如果服务器版本和本地数据库版本不一样
                        if (!versionNo.equals(mVersionNo)) {

                            message.what = NEW_DB;
                            // 4 下载新的数据库
                            String versionFileName = backLogMessage
                                    .getString("dbVersionName");
                            doDownLoadWork(versionFileName);

                            // 5 将新的版本号储存到本地
                            SharedPreferences.Editor editor = getSharedPreferences(
                                    "data", MODE_PRIVATE).edit();
                            editor.putString("mVersionNo", versionNo);
                            editor.commit();

                        } else {
                            // 发送数据库版本
                            Log.i(TAG, "SQ is already exits!!");
                            message.what = SAME_DB;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                        message.what = JSON_ERROR;

                    } finally {
                        handler.sendMessage(message);
                    }
                }
                return null;
            }

        }.execute(fans_data_url);
    }*/
}
