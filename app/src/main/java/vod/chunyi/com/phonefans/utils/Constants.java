package vod.chunyi.com.phonefans.utils;

/**
 * 基础常量类
 * Created by knight on 2017/4/5.
 */

public class Constants {
    /* 底部菜单栏相关常量*/
    //控制栏底部"点赞"
    public static final int BOTTOM_CONTROLLER_SUPPORT = 0X01;
    //控制栏底部"鲜花"
    public static final int BOTTOM_CONTROLLER_FLOWER = 0X02;
    //控制栏底部"加分"
    public static final int BOTTOM_CONTROLLER_SCORE = 0X03;
    //控制栏底部"暂停"
    public static final int BOTTOM_CONTROLLER_PAUSE = 0X04;
    //控制栏底部"列表"
    public static final int BOTTOM_CONTROLLER_LIST = 0X05;

    /* 点歌 界面相关常量 具体数字 */
    //"点歌" 界面的栏目数
    public static final int SELECT_SONG_ITEMS = 3;
    //各个栏目的列数
    public static final int SELECT_SONG_ITEMS_ROWS = 3;
    //"推荐" 下面的行数
    public static final int SELECT_SONG_RECOMMEND = 2;
    //"歌手" 下面的行数
    public static final int SELECT_SONG_SINGER = 1;
    //"歌单" 下面的行数
    public static final int SELECT_SONG_SONGLIST = 2;


    //返回给主线程 下载并解压完成
    public static final int DOWN_UNZIP_FINISH = 0X11;
    //和服务器数据库版本一样
    public static final int SAME_DB = 0X12;
    //有新的数据库版本
    public static final int NEW_DB = 0X13;
    // json解析错误
    public static final int JSON_ERROR = 0X14;
    // 连接服务器错误
    public static final int CONNECT_SERVER_ERROR = 0X15;

    /* SP 保存相关的数据*/
    public static final String DB_VERSION = "dbVersion";

    public static final String DB_VERSIONNAME = "dbVersionName";

    //没有网络连接
    public static final int NETWORN_NONE = -1;
    //wifi连接
    public static final int NETWORN_WIFI = 0X16;
    //手机网络数据连接类型
    public static final int NETWORN_MOBILE = 0X17;



}
