package vod.chunyi.com.phonefans.db;

/**
 * 用于储存请求参数码
 *
 * @author lj
 */
public class VodCode {
    // 查询数据库完成
    public static final int QUERY_SQL_FINISH = 0X1001;

    // 连接成功
    public static final String SCOKET_CONNECT_SUCESS = "com.chunyi.vod.mmediaPlayerService.connect.sucess";
    // 连接失败
    public static final String SCOKET_CONNECT_FAILE = "com.chunyi.vod.mmediaPlayerService.connect.faile";
    // 连接错误
    public static final String SCOKET_CONNECT_WRONG = "com.chunyi.vod.mmediaPlayerService.connect.wrong";
    // 发送后台有新的播放列表的广播
    public static final String SCOKET_NEW_SELECT_SONG = "com.chunyi.vod.mmediaPlayerService.newSelectSong";
    public static final String SCOKET_NOT_FIND_SERVICE = "com.chunyi.vod.mmediaPlayerService.notFindService";
    public static final String SCOKET_PLAYSTATE = "com.chunyi.vod.mmediaPlayerService.playstate";
    public static final String SCOKET_PLAYPASUEERROR = "com.chunyi.vod.mmediaPlayerService.playpauseerror";
    public static final String SCOKET_CONNECT_TV = "com.chunyi.vod.mmediaPlayerService.connecttv";

}
