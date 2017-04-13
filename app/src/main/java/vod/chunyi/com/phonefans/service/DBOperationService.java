package vod.chunyi.com.phonefans.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;

import okhttp3.Request;
import okhttp3.Response;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.DBInfo;
import vod.chunyi.com.phonefans.net.DownloadCallback;
import vod.chunyi.com.phonefans.net.OkHttpHelper;
import vod.chunyi.com.phonefans.net.SimpleCallback;
import vod.chunyi.com.phonefans.utils.Constants;
import vod.chunyi.com.phonefans.utils.FileUtils;
import vod.chunyi.com.phonefans.utils.SharedPreferencesUtils;

/**
 * Created by knight on 2017/4/7.
 */

public class DBOperationService extends IntentService {


    public static final String CHECK_DB_VERSION = "check_db_version";

    private static final String CHECK_DB_URL = "check_db_url";

    private static final String DOWN_DB = "down_db";

    private static final String DOWN_DB_URL = "down_db_url";

    private static final String UNZIP_DB = "unzip_db";

    private static final String UNZIP_DB_DIR = "unzip_db_dir";

    public DBOperationService() {
        super("DBOperationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("DBOperationService", "onCreate()");
    }

    public static void CheckDBVersion(Context context) {
        Intent intent = new Intent(context, DBOperationService.class);
        intent.setAction(CHECK_DB_VERSION);
        intent.putExtra(CHECK_DB_URL, context.getResources().getString(
                R.string.fans_data_url));
        context.startService(intent);
    }

    public static void DownDBFile(Context context, String name) {
        Intent intent = new Intent(context, DBOperationService.class);
        intent.setAction(DOWN_DB);
        String db_url = context.getResources().getString(R.string.fans_data_down) + name;
        intent.putExtra(DOWN_DB_URL, db_url);
        context.startService(intent);
    }

    public static void unZipDBFile(Context context, String dbZipFilePath) {
        Intent intent = new Intent(context, DBOperationService.class);
        intent.setAction(UNZIP_DB);
        intent.putExtra(UNZIP_DB_DIR, dbZipFilePath);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            String action = intent.getAction();
            if (CHECK_DB_VERSION.equals(action)) {
                Log.e("DBOperationService", "onHandleIntent");
                doCheckVersion(intent.getStringExtra(CHECK_DB_URL));
            } else if (DOWN_DB.equals(action)) {
                doDownloadDB(intent.getStringExtra(DOWN_DB_URL));
            } else if (UNZIP_DB.equals(action)) {
                doUnZipDBFile(intent.getStringExtra(UNZIP_DB_DIR));
            }
        }
    }

    /**
     * 下载 db 数据库压缩 zip文件
     *
     * @param dbFileUrl
     */
    private void doDownloadDB(String dbFileUrl) {

        String destFileDir = getFilesDir().getAbsolutePath();

        OkHttpHelper.getInstance().downFile(dbFileUrl, destFileDir, new DownloadCallback<File>() {
            @Override
            public void onFailure(Request request, Exception e) {
                SharedPreferencesUtils.remove(getApplicationContext(), Constants.DB_VERSION);
            }

            @Override
            public void onProgress(double total, double current) {
                Log.e("onProgress", "total:" + total + ",current:" + current);
            }

            @Override
            public void onSuccess(Response response, File file) {
                Log.e("db_file_dir:", file.getAbsolutePath());

                DBOperationService.unZipDBFile(getApplicationContext(), file.getAbsolutePath());
            }
        });
    }

    /**
     * 进行 db 数据库版本检验
     *
     * @param versionUrl
     */
    private void doCheckVersion(String versionUrl) {

        OkHttpHelper.getInstance().post(versionUrl, null, new SimpleCallback<DBInfo>() {

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, DBInfo dbInfo) {
                if (response.isSuccessful()) {

                    Log.e("onResponse", "result-->" + dbInfo.toString());
                    String sp_db_version = (String) SharedPreferencesUtils.get(getApplicationContext(), Constants.DB_VERSION, "");
                    if (!sp_db_version.equals(dbInfo.getDbVersion())) {
                        DBOperationService.DownDBFile(getApplicationContext(), dbInfo.getDbVersionName());
                        SharedPreferencesUtils.put(getApplicationContext(), Constants.DB_VERSION, dbInfo.getDbVersion());
                    }
                }
            }
        });
    }

    /**
     * 解压 zip 数据库文件
     *
     * @param dbZipDir
     */
    private void doUnZipDBFile(String dbZipDir) {
        String destDBDir = getFilesDir().getAbsolutePath();
        //String destDBDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e("doUnZipDBFile->dir:", destDBDir);
        try {
            FileUtils.UnZipFolder(dbZipDir, destDBDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
