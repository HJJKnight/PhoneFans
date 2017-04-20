package vod.chunyi.com.phonefans.modle;

/**
 * 网络业务常量类
 * Created by knight on 2017/4/12.
 */

public class NetCode {

    public static final int SUCCESS_CODE = 1;
    public static final int FAILE_CODE = 0;


    public static final int POST_LOGIN = 0X101;// 登陆
    public static final int NO_CONNECT_SERVER = 0X102;// 没有连接到服务器
    public static final int POST_REGISTER = 0X103;// 注册
    public static final int POST_GETSMSCODE = 0X104;// 获取验证码
    public static final int POST_CHANGPASSWORD = 0X105;// 更改密码
    public static final int POST_QUERY_USERBYNUM = 0X106;// 根据手机号码查询用户信息
    public static final int POST_QUERY_SELECT_SONG = 0X107;// 根据手机号码查询已选歌曲
    public static final int POST_COMPETITION_LIST = 0X108;// 请求服务器赛事列表
    public static final int POST_COMPETITION_LIST_DETAIL = 0X109;// 请求服务器赛事列表详情信息
    public static final int ADD_SONG = 0X110;// 加歌曲
    public static final int ADD_SONG_FAILE = 0X111;// 加歌曲失败
    public static final int DETELE_SONG = 0X112;// 切歌
    public static final int DETELE_SONG_FAILE = 0X113;// 切歌失败
    public static final int PLAY_SUCESS = 0X114;// 播放成功
    public static final int PLAY_FAILE = 0X115;
    public static final int PASUE_SUCESS = 0X116;
    public static final int PASUE_FAILE = 0X117;
    public static final int SCOKET_QUERY_SELECT_SONG = 0X118;

    public static final String USER_INFO = "userBean";

    //网络业务接口
    public static final String VOD_FINDUSER_BY_PHONE = "vod_findUserByPhone.json";
    public static final String VOD_MODIFY_USER = "vod_modifyUser.json";
    public static final String VOD_ADD_USER = "vod_addUser.json";
    public static final String VOD_LOGIN = "vod_login.json";
    public static final String VOD_MATCHVIDEO_LIST = "vod_matchVideo_list.json";
    public static final String VOD_MATCH_LIST = "vod_match_list.json";
    public static final String VOD_GET_SMS_CODE = "vod_getSmsCode.json";
    public static final String VOD_UPLOAD_HEAD_IMG = "vod_upload_headImage.json";


    //网路业务参数
    public static final String PHONE_NUMBER = "phoneNo";
    public static final String USER_ID = "userId";
    public static final String LOGIN_PSW = "loginPsw";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String PAGE = "page";
    public static final String ROWS = "rows";
    public static final String MATCH_NO = "matchNo";


    public static final String USER_IMG = "fileImage";
    public static final String USER_NAME = "userName";
    public static final String USER_PHONE = "phoneNo";
    public static final String USER_PSW = "loginPsw";

}
