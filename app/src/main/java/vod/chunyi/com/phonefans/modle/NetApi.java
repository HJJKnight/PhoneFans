package vod.chunyi.com.phonefans.modle;

import android.content.Context;
import android.os.Handler;

/**
 * 网络业务类
 * Created by knight on 2017/4/11.
 */

public class NetApi {

    private static NetApi mInstance;

    private static NetTask mTask;

    private Context mContext;

    public static NetApi getInstance(Context context) {
        if (mInstance == null) {
            return new NetApi(context);
        }
        return mInstance;
    }

    private NetApi(Context context) {
        mContext = context;
        //mTask = new NetTask(mContext);
    }

    /**
     * 根据用户电话查询用户信息
     *
     * @param phoneNum
     * @param handler
     */
    public void postQueryUserByNumber(String phoneNum, Handler handler) {

        mTask.setUIPost(NetCode.POST_QUERY_USERBYNUM, handler);
        mTask.execute(phoneNum);
    }

    /**
     * 更改登陆密码
     *
     * @param passWord
     * @param handler
     */
    public void postChangePassword(String passWord, Handler handler) {

        mTask.setUIPost(NetCode.POST_CHANGPASSWORD, handler);
        mTask.execute(passWord);
    }

    /**
     * 获取验证码
     *
     * @param phoneNo 电话号码
     * @param handler
     */
    public void getSmsCode(String phoneNo, Handler handler) {
       /* smsCode = "";
        GetsmsCode = 0;*/

        mTask.setUIPost(NetCode.POST_GETSMSCODE, handler);
        mTask.execute(phoneNo);
    }

    /**
     * 用户注册
     *
     * @param phoneNum
     * @param passWord
     * @param handler
     */
    public void register(String phoneNum, String passWord, Handler handler) {

        mTask.setUIPost(NetCode.POST_REGISTER, handler);
        mTask.execute(phoneNum, passWord);

    }

    /**
     * 登录
     *
     * @param userName 用户名
     * @param passWord 密码
     * @param handler
     */
    public void login(String userName, String passWord, Handler handler) {

        mTask.setUIPost(NetCode.POST_LOGIN, handler);
        mTask.execute(userName, passWord);
    }

    /**
     * 请求服务器参数详情
     *
     * @param page    页码数
     * @param rows    每页显示的数量
     * @param matchNo 赛区编号
     * @param handler 传入的hander以便返回数据后更新ui
     */
    public void postCompetionDetailList(int page, int rows, int matchNo, Handler handler) {
        mTask.setUIPost(NetCode.POST_COMPETITION_LIST_DETAIL, handler);
        mTask.execute(page, rows, matchNo);
    }

    /**
     * 请求服务器参赛列表
     *
     * @param page    页码数
     * @param rows    每页显示的数量
     * @param handler 传入的hander以便返回数据后更新ui
     */
    public void postCompetionList(int page, int rows, Handler handler) {
        mTask = new NetTask(mContext);
        mTask.setUIPost(NetCode.POST_COMPETITION_LIST, handler);
        mTask.execute(page, rows);
    }


    /**
     * 功能：将歌曲加入已选列表
     *
     * @param searchResult 歌曲对象

    public void postAddSongToSelectList(Song song, Handler handler) {

    SocketMessage message = new SocketMessage();
    if (application.socketService != null && song != null) {
    List<Song> Songs = new ArrayList<Song>();

    message.setMethod("addSong");
    message.setMsg(song.getSong_id() + "");
    Songs.add(song);
    message.setSongs(Songs);
    application.socketService.sendTvMessage(message, handler);

    }

    }
     */
}
