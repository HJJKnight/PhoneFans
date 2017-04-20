package vod.chunyi.com.phonefans.modle;

import android.content.Context;

import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.Song;
import vod.chunyi.com.phonefans.bean.UserBean;
import vod.chunyi.com.phonefans.net.BaseCallback;
import vod.chunyi.com.phonefans.net.OkHttpHelper;

/**
 * Created by knight on 2017/4/14.
 */

public class NetApiIml implements NetApi {

    private static NetApiIml mInstance;
    private static Context mContext;

    private static String address = "";

    public static NetApiIml getInstance(Context context) {

        if (mInstance == null) {
            mContext = context;
            mInstance = new NetApiIml();
            address = context.getResources().getString(R.string.server_url);
        }
        return mInstance;
    }

    @Override
    public void postQueryUserByNumber(String phoneNum, BaseCallback callback) {

        params.clear();
        params.put(NetCode.PHONE_NUMBER, phoneNum);

        String url = address + NetCode.VOD_FINDUSER_BY_PHONE;

        OkHttpHelper.getInstance().post(url, params, callback);

    }

    @Override
    public void postChangePassword(int userId, String password, BaseCallback callback) {

        params.clear();
       /* int userId = -1;
        UserBean.UserPo userInfo = SharedPreferencesUtils.getObject(mContext, NetCode.USER_INFO, UserBean.class).getUserPo();
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }*/

        params.put(NetCode.USER_ID, userId);
        params.put(NetCode.LOGIN_PSW, password);

        String url = address + NetCode.VOD_MODIFY_USER;

        OkHttpHelper.getInstance().post(url, params, callback);

    }

    @Override
    public void getSmsCode(String phoneNo, BaseCallback callback) {
        params.clear();
        params.put(NetCode.PHONE_NUMBER, phoneNo);
        String url = address + NetCode.VOD_GET_SMS_CODE;

        OkHttpHelper.getInstance().post(url, params, callback);

    }

    @Override
    public void postRegister(String phoneNum, String password, BaseCallback callback) {
        params.clear();
        params.put(NetCode.PHONE_NUMBER, phoneNum);
        params.put(NetCode.LOGIN_PSW, password);

        String url = address + NetCode.VOD_ADD_USER;
        OkHttpHelper.getInstance().post(url, params, callback);

    }

    @Override
    public void postLogin(String userName, String passWord, BaseCallback callback) {
        params.clear();
        params.put(NetCode.ACCOUNT, userName);
        params.put(NetCode.PASSWORD, passWord);

        String url = address + NetCode.VOD_LOGIN;

        OkHttpHelper.getInstance().post(url, params, callback);


    }


    @Override
    public void postUserInfo(UserBean userBean, BaseCallback callback) {
        params.clear();
        params.put(NetCode.USER_ID, userBean.getUser().getUserId());
        params.put(NetCode.USER_NAME, userBean.getUser().getUserName());
        params.put(NetCode.USER_PHONE, userBean.getUser().getPhoneNo());

        String url = address + NetCode.VOD_MODIFY_USER;

        OkHttpHelper.getInstance().post(url, params, callback);
    }

    @Override
    public void postUserHeadImg(int userId, String base64Str, BaseCallback callback) {
        params.clear();
        params.put(NetCode.USER_ID, userId);
        params.put(NetCode.USER_IMG, base64Str);
        String url = address + NetCode.VOD_UPLOAD_HEAD_IMG;

        OkHttpHelper.getInstance().post(url, params, callback);
    }

    @Override
    public void postCompetionDetailList(int page, int rows, int matchNo, BaseCallback callback) {
        params.clear();
        params.put(NetCode.PAGE, page);
        params.put(NetCode.ROWS, rows);
        params.put(NetCode.MATCH_NO, matchNo);

        String url = address + NetCode.VOD_MATCHVIDEO_LIST;

        OkHttpHelper.getInstance().post(url, params, callback);

    }

    @Override
    public void postCompetionList(int page, int rows, BaseCallback callback) {

        params.clear();
        params.put(NetCode.PAGE, page);
        params.put(NetCode.ROWS, rows);

        String url = address + NetCode.VOD_MATCH_LIST;
        OkHttpHelper.getInstance().post(url, params, callback);

    }

    @Override
    public void postAddSongToSelectList(Song song, BaseCallback handler) {

    }
}
