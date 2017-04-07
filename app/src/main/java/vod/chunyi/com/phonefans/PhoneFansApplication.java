package vod.chunyi.com.phonefans;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import vod.chunyi.com.phonefans.bean.Song;
import vod.chunyi.com.phonefans.net.DownLoaderTask;
import vod.chunyi.com.phonefans.net.ZipExtractorTask;
import vod.chunyi.com.phonefans.utils.FileUtils;

/**
 * Created by knight on 2017/4/6.
 */

public class PhoneFansApplication extends Application {

    /**
     * 关联的dao
     */
    private VodDao dao;

    /**
     * 数据库对象
     */
    private SQLiteDatabase db;

    // 歌迷系统的数据库路径
    private static final String path = "/data/data/com.chunyi.vod/files/fans.db";
    // 存放下载歌迷系统压缩数据库的路径
    private static final String dbZipPath = "/data/data/com.chunyi.vod/files/";
    // 对比数据库版本的url
    private String fans_data_url = "";
    // 下载数据的地址前缀
    private String fans_data_down = "";
    /**
     * 返回给主线程 下载并解压完成
     */
    public static final int DOWN_UNZIP_FINISH = 0X255;

    /**
     * 和服务器数据库版本一样
     */
    public static final int SAME_DB = 0X10;

    /**
     * 有新的数据库版本
     */
    public static final int NEW_DB = 0X12;

    /**
     * json解析错误
     */
    public static final int JSON_ERROR = 0X11;

    /**
     * 连接服务器错误
     */
    public static final int CONNECT_ERROR = 0X13;
    /**
     * 储存该项目的sp
     */
    private SharedPreferences sp;

    private String TAG = "VodApplication";

    /**
     * 用于存储全局的已选歌曲
     */
    public List<Song> selectSongs = new ArrayList<Song>();
    /**
     * 用于记录底部点击的位置
     */
    public int mainButtomPosition;
    /**
     * 用于加载所有的activity
     */
    public List<Activity> activities = new ArrayList<Activity>();

    /**
     * 用于记录中间碎片的按钮
     */
    public int mainCenterPosition;
    /**
     * 手机绑定的tv端机械码
     */
    public String bindMachineCode;

    public String phoneMachineCode;
    // 用于显示的tv端机械码
    public String bindMachineCodeShow;
    // 连接后台socket的服务
    //public SocketService socketService;
    // 判断是否连接到tv端
    public boolean connectTv = true;
    /**
     * tv端的ip地址
     */
    public String tvIP = "";
    // 监听网络变化
    //private ConnectionChangeReceiver myreceiver;
    // 返回值 -1：没有网络 1：WIFI网络2：wap网络3：net网络
    public int NetType;

    private boolean connectAgain = false;
    // 用于判断是否被注册的标识
    private int registerService = 0;

    private static PhoneFansApplication instance;

    public static PhoneFansApplication getIns() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        fans_data_url = getApplicationContext().getResources().getString(
                R.string.fans_data_url);
        fans_data_down = getApplicationContext().getResources().getString(
                R.string.fans_data_down);
        sp = getSharedPreferences("data", MODE_PRIVATE);
        // 1从资源文件当中考取数据库
        copyDB("fans.db");

        // 2 将数据库取出 注入到dao里面 这里先注入 是防止进入后进行查询
        db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);
        dao = new VodDao(this);
        dao.setDb(db);

        // 3 检验数据库版本 如果不一致进行更换
        //checkDbVersion();
        DBOperationService.CheckDBVersion(getApplicationContext());

        sp = getSharedPreferences("data", MODE_PRIVATE);
        // TODO
        bindMachineCode = sp.getString("bindMachineCode", "");
        // bindMachineCode = "ffffffff-aba5-39ab-7c69-87b70033c587";
        bindMachineCodeShow = sp.getString("bindMachineCodeShow", "");
        tvIP = sp.getString("bindMachineIP", "");

        phoneMachineCode = getMachineCode();
        NetType = GetNetype(getApplicationContext());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWN_UNZIP_FINISH:
                    // 下载并解压新的数据库完成
                    Log.d(TAG, "SQ is down finish!!");
                    // 获取新的数据库并注入到dao中
                    db = SQLiteDatabase.openDatabase(path, null,
                            SQLiteDatabase.OPEN_READONLY);
                    dao.setDb(db);
                    // Toast.makeText(getApplicationContext(), "数据库更新完成", 0).show();
                    break;
                case SAME_DB:
                    Log.d(TAG, "SQ is same with url");
                    break;
                case JSON_ERROR:
                    Log.d(TAG, "JSON_ERROR");
                    break;
                case CONNECT_ERROR:
                    Log.d(TAG, "CONNECT_ERROR");
                    break;
                case NEW_DB:
                    Log.d(TAG, "SQ has new");
                    // Toast.makeText(getApplicationContext(), "后台正更新数据库",
                    // 0).show();
                    break;
            }

        }
    };


    /**
     * 从 assets 文件夹里面复制 DB 文件
     *
     * @param dbName
     */
    private void copyDB(String dbName) {

        File destFile = new File(getFilesDir(), dbName);

        File srcFile = new File("file:///android_asset/" + dbName);

        FileUtils.copyFile(srcFile, destFile);

    }

    /**
     * 功能：从云端下载数据库ZIP文件
     *
     * @param dbName 数据库压缩包名字
     */
    private void doDownLoadWork(String dbName) {

        new DownLoaderTask(fans_data_down + dbName, dbZipPath, this).execute();
    }

    /**
     * 功能：解压从云端下载数据库ZIP文件
     */
    public void doZipExtractorWork(String dbName) {

        new ZipExtractorTask(dbZipPath + dbName, dbZipPath, this, handler)
                .execute();
    }

    public VodDao getDao() {
        return dao;
    }

    public void setDao(VodDao dao) {
        this.dao = dao;
    }

    /**
     * 获取设备机械码 并对其做加密处理
     *
     * @return 机械码
     */
    public String getMachineCode() {
        TelephonyManager tm = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = tm.getDeviceId() + "";
        String tmSerial = tm.getSimSerialNumber() + "";
        String androidId = android.provider.Settings.Secure.getString(
                getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }








    // 返回值 -1：没有网络 1：WIFI网络2：wap网络3：net网络
    public static int GetNetype(Context context) {

        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = 3;
            } else {
                netType = 2;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }
}
