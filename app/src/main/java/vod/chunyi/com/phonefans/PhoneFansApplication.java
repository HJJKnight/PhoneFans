package vod.chunyi.com.phonefans;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import vod.chunyi.com.phonefans.bean.Song;
import vod.chunyi.com.phonefans.db.VodDao;

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


    private String TAG = "PhoneFansApplication";

    /**
     * 用于存储全局的已选歌曲
     */
    public List<Song> selectSongs = new ArrayList<Song>();
    /**
     * 用于记录底部点击的位置
     */
    public int mainButtomPosition;

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

    private static PhoneFansApplication mInstance;

    public static PhoneFansApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // 2 将数据库取出 注入到dao里面 这里先注入 是防止进入后进行查询
        /*db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);
        dao = new VodDao(this);
        dao.setDb(db);*/

        // TODO
       /* bindMachineCode = sp.getString("bindMachineCode", "");
        // bindMachineCode = "ffffffff-aba5-39ab-7c69-87b70033c587";
        bindMachineCodeShow = sp.getString("bindMachineCodeShow", "");
        tvIP = sp.getString("bindMachineIP", "");*/

        //phoneMachineCode = getMachineCode();
        //NetType = GetNetype(getApplicationContext());
    }


    public void setSelectedSong(List<Song> songs) {
        this.selectSongs = songs;
    }

    public List<Song> getSelectSongs() {
        return selectSongs;
    }

    public VodDao getDao() {
        return dao;
    }

    public void setDao(VodDao dao) {
        this.dao = dao;
    }



}
