package vod.chunyi.com.phonefans.modle;

import java.util.HashMap;
import java.util.Map;

import vod.chunyi.com.phonefans.bean.Song;
import vod.chunyi.com.phonefans.net.BaseCallback;

/**
 * Created by knight on 2017/4/14.
 */

public interface NetApi {


    Map<String, Object> params = new HashMap<>();


    /**
     * 根据用户电话查询用户信息
     *
     * @param phoneNum
     * @param callback
     */
    void postQueryUserByNumber(String phoneNum, BaseCallback callback);

    /**
     * 更改登陆密码
     *
     * @param userId
     * @param passWord
     * @param callback
     */
    void postChangePassword(int userId, String passWord, BaseCallback callback);

    /**
     * 获取验证码
     *
     * @param phoneNo  电话号码
     * @param callback
     */
    void getSmsCode(String phoneNo, BaseCallback callback);

    /**
     * 用户注册
     * resultCode:判断注册是否成功 0失败 ,1成功 ,2已经注册
     *
     * @param phoneNum
     * @param passWord
     * @param callback
     */
    void postRegister(String phoneNum, String passWord, BaseCallback callback);

    /**
     * 登录
     *
     * @param userName 用户名
     * @param passWord 密码
     * @param callback
     */
    void postLogin(String userName, String passWord, BaseCallback callback);

    /**
     * 请求服务器参数详情
     *
     * @param page     页码数
     * @param rows     每页显示的数量
     * @param matchNo  赛区编号
     * @param callback 传入的callback以便返回数据后更新ui
     */
    void postCompetionDetailList(int page, int rows, int matchNo, BaseCallback callback);

    /**
     * 请求服务器参赛列表
     *
     * @param page     页码数
     * @param rows     每页显示的数量
     * @param callback 传入的hander以便返回数据后更新ui
     */
    void postCompetionList(int page, int rows, BaseCallback callback);

    /**
     * 功能：将歌曲加入已选列表
     *
     * @param song
     * @param callback
     */
    void postAddSongToSelectList(Song song, BaseCallback callback);


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
