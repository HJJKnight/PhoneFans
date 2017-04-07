package vod.chunyi.com.phonefans.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import vod.chunyi.com.phonefans.PhoneFansApplication;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.net.HttpJSONClient;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.StatusBarUtils;
import vod.chunyi.com.phonefans.utils.ToastUtil;

public class SplashActivity extends BaseActivity {

    protected static final int ENTER_HOME = 0;
    protected static final int SHOW_UPDATE_DIALOG = 1;
    protected static final int URL_ERROR = 2;
    protected static final int NET_ERROR = 3;
    protected static final int JSON_ERROR = 4;
    protected static final String TAG = SplashActivity.class.getSimpleName();
    private SharedPreferences sp;
    private String description;// 新版本詳細信息
    private TextView tv_splash_update;
    private String version;
    private PhoneFansApplication application;
    private String apkName = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews() {
        StatusBarUtils.setImage(this);
    }

    @Override
    public void initVaribles() {

        application = (PhoneFansApplication) getApplication();
        application.activities.add(this);
        tv_splash_update = (TextView) findViewById(R.id.tv_splash_update);
        sp = getSharedPreferences("config", MODE_PRIVATE);

        application.registerConnectReceiver();
        checkVersion();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ENTER_HOME:
                    enterHome();
                    break;
                case SHOW_UPDATE_DIALOG:
                    showUpdateDailog();
                    break;
                case URL_ERROR:
                    ToastUtil.showShort(SplashActivity.this,"URL异常");
                    enterHome();
                    break;
                case NET_ERROR:
                    ToastUtil.showShort(SplashActivity.this,"网络异常");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUtil.showShort(SplashActivity.this,"网络异常");
                    enterHome();
                    break;
            }
        }
    };

    /**
     * 校检apk版本
     */
    private void checkVersion() {

        new Thread() {

            Message message = Message.obtain();
            long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                super.run();

                try {

                    HttpJSONClient jsonClient = new HttpJSONClient();
                    JSONObject object = jsonClient
                            .post(getString(R.string.check_apk_url));
                    if (object == null) {
                        message.what = URL_ERROR;
                    } else {
                        Log.d(TAG, "Json====" + object.toString());
                        version = object.getString("phoneVersion");
                        description = object.getString("phoneVersionDesc");
                        apkName = object.getString("phoneVersionName");

                        if (getVersionName().equals(version)) {

                            message.what = ENTER_HOME;
                        } else {

                            message.what = SHOW_UPDATE_DIALOG;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what = JSON_ERROR;
                }

                finally {
                    long endTime = System.currentTimeMillis();
                    long dTime = endTime - startTime;
                    if (dTime < 2000) {
                        SystemClock.sleep(2000 - dTime);
                    }
                    handler.sendMessage(message);
                }

            }
        }.start();

    }

    private String getVersionName() {

        PackageManager pm = getPackageManager();

        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void showUpdateDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("检测到有新版本哦:");
        builder.setMessage(description);
        // builder.setCancelable(false);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                enterHome();
            }
        });

        builder.setNegativeButton("取消升级",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        enterHome();

                    }
                });

        builder.setPositiveButton("马上升级",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 1、下载apk
                        // 2、替换安装
                        if (Environment.getExternalStorageState().equals(
                                Environment.MEDIA_MOUNTED)) {

                            FinalHttp http = new FinalHttp();
                            String download = getString(R.string.down_apk_url)
                                    + apkName;
                            Log.d(TAG, download);
                            http.download(download,
                                    Environment.getExternalStorageDirectory()
                                            + "/FansVod" + version + ".apk",
                                    new AjaxCallBack<File>() {
                                        @Override
                                        public void onFailure(Throwable t,
                                                              int errorNo, String strMsg) {
                                            super.onFailure(t, errorNo, strMsg);
                                            t.printStackTrace();

                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "下载失败", 0).show();
                                            Log.d(TAG, errorNo + "------" + t
                                                    + "---------" + strMsg);
                                            enterHome();
                                        }

                                        @Override
                                        public void onLoading(long count,
                                                              long current) {
                                            super.onLoading(count, current);
                                            tv_splash_update
                                                    .setVisibility(View.VISIBLE);
                                            int Progress = (int) (current * 100 / count);

                                            tv_splash_update.setText("下载进度："
                                                    + Progress + "%");
                                        }

                                        @Override
                                        public void onSuccess(File t) {
                                            // TODO Auto-generated method stub
                                            super.onSuccess(t);
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "下载成功", 0).show();
                                            // 安装apk
                                            installApk(t);
                                        }

                                        private void installApk(File t) {
                                            Intent intent = new Intent();
                                            intent.setAction("android.intent.action.VIEW");
                                            intent.setDataAndType(
                                                    Uri.fromFile(t),
                                                    "application/vnd.android.package-archive");

                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }

                                    });

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "sdcard不可用", 0).show();
                        }
                    }
                });
        builder.show();
    }

    protected void enterHome() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }


}
