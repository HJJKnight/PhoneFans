package vod.chunyi.com.phonefans.modle;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.CompetionDetailInfo;
import vod.chunyi.com.phonefans.bean.CompetionInfo;
import vod.chunyi.com.phonefans.bean.User;
import vod.chunyi.com.phonefans.net.OkHttpHelper;
import vod.chunyi.com.phonefans.net.SimpleCallback;
import vod.chunyi.com.phonefans.utils.Constants;
import vod.chunyi.com.phonefans.utils.PhoneUtils;
import vod.chunyi.com.phonefans.utils.SharedPreferencesUtils;

/**
 * Created by knight on 2017/4/12.
 */

public class NetTask extends AsyncTask {

    private Context mContext;
    /**
     * 发送给服务器的请求指令
     */
    private int post = 0;

    /**
     * 方便发送message给相应的界面更新ui
     */
    private Handler handler;
    /**
     * 发送给服务器的键值对
     */
    private Map<String, Object> params = new HashMap<String, Object>();

    /**
     * 访问服务器的地址
     */
    public String address;
    /**
     * 执行任务之前时间
     */
    private long preTime;
    /**
     * 执行任务之后时间
     */
    private long lTime;

    public NetTask(Context context) {
        this.mContext = context;
    }

    /**
     * 执行之前
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //TODO
        preTime = new Date().getTime();
        address = mContext.getResources().getString(R.string.server_url);
    }

    @Override
    protected Object doInBackground(Object... params) {

        //判断是否链接上互联网
        if (PhoneUtils.getNetType(mContext) != Constants.NETWORN_NONE) {
            boolean isConnectServer = PhoneUtils.isConnectServer(address);
            // 判断是否连接到服务器
            if (isConnectServer) {
                switch (post) {
                    case NetCode.POST_COMPETITION_LIST:
                        // 请求服务器参数信息列表
                        if (params != null && params.length == 2) {
                            int page = (Integer) params[0];
                            int rows = (Integer) params[1];
                            postCompetionList(page, rows);
                        }
                        break;
                    case NetCode.POST_COMPETITION_LIST_DETAIL:
                        // 请求服务器参数信息列表
                        if (params != null && params.length == 3) {
                            int page = (Integer) params[0];
                            int rows = (Integer) params[1];
                            int matchNo = (Integer) params[2];
                            postCompetionDetailList(page, rows, matchNo);
                        }
                        break;

                    case NetCode.POST_LOGIN:
                        if (params != null && params.length == 2) {
                            String userName = (String) params[0];
                            String passWord = (String) params[1];

                            postLogin(userName, passWord);
                        }
                        break;
                    case NetCode.POST_REGISTER:

                        if (params != null && params.length == 2) {
                            String phoneNo = (String) params[0];
                            String loginPsw = (String) params[1];

                            postRegister(phoneNo, loginPsw);
                        }
                        break;
                    case NetCode.POST_GETSMSCODE:
                        if (params != null && params.length == 1) {
                            String phoneNo = (String) params[0];

                            postGetSmsCode(phoneNo);
                        }
                        break;

                    case NetCode.POST_CHANGPASSWORD:
                        if (params != null && params.length == 1) {
                            String password = (String) params[0];

                            postChangePassword(password);
                        }
                        break;
                    case NetCode.POST_QUERY_USERBYNUM:
                        if (params != null && params.length == 1) {
                            String phoneNum = (String) params[0];

                            postQueryUserByNum(phoneNum);
                        }
                        break;
                }

            } else {
                return Constants.CONNECT_SERVER_ERROR;
            }
        } else {
            return Constants.NETWORN_NONE;
        }

        return null;
    }

    /**
     * 执行结果
     */
    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if (result != null) {
            Message msg = handler.obtainMessage();
            msg.arg1 = (int) result;
            handler.sendMessage(msg);
        }

        //TODO
        lTime = new Date().getTime();

    }


    /**
     * 发送给服务器的请求指令
     *
     * @param post
     */
    public void setUIPost(int post, Handler handler) {
        this.post = post;
        this.handler = handler;
    }

    /**
     * 通过电话号码查询用户
     *
     * @param phoneNum
     */
    private void postQueryUserByNum(String phoneNum) {
        params.clear();

        params.put(NetCode.PHONE_NUMBER, phoneNum);
        String url = address + NetCode.VOD_FINDUSER_BY_PHONE;

        OkHttpHelper.getInstance().post(url, params, new SimpleCallback<User>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, User user) {
                Log.e("postQueryUserByNum", user.getResultCode() + "");

                try {
                    // 存入SP
                    SharedPreferencesUtils.putObj(mContext, NetCode.USER_INFO, user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    /**
     * 修改密码
     *
     * @param password
     */
    private void postChangePassword(String password) {

        params.clear();
        int userId = -1;
        User.UserPo userInfo = SharedPreferencesUtils.getObject(mContext, NetCode.USER_INFO, User.class).getUserPo();
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }

        params.put(NetCode.USER_ID, userId);
        params.put(NetCode.LOGIN_PSW, password);
        String url = address + NetCode.VOD_MODIFY_USER;

        OkHttpHelper.getInstance().post(url, params, new SimpleCallback<User>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, User user) {
                Log.e("postChangePassword", user.getResultCode() + "");

                try {
                    //返回的 resultCode 为 1表示成功，0失败
                    SharedPreferencesUtils.putObj(mContext, NetCode.USER_INFO, user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * 获取验证码
     *
     * @param phoneNo
     */
    private void postGetSmsCode(String phoneNo) {

        params.clear();
        params.put(NetCode.PHONE_NUMBER, phoneNo);
        String url = address + NetCode.VOD_GET_SMS_CODE;

        OkHttpHelper.getInstance().post(url, params, new SimpleCallback<String>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, String s) {
                Log.e("postGetSmsCode", s);
            }

        });

    }

    /**
     * 注册
     *
     * @param phoneNo
     * @param loginPsw
     */
    private void postRegister(String phoneNo, String loginPsw) {

        params.clear();
        params.put(NetCode.PHONE_NUMBER, phoneNo);
        params.put(NetCode.LOGIN_PSW, loginPsw);
        String url = address + NetCode.VOD_ADD_USER;
        OkHttpHelper.getInstance().post(url, params, new SimpleCallback<User>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, User user) {
                //判断注册是否成功 0 失败 1 成功 2 已经注册
                int resultCode = user.getResultCode();
                Log.e("postRegister", "resultCode:" + resultCode);
            }
        });
    }

    /**
     * 登陆
     * TODO
     *
     * @param userName 用户名
     * @param passWord 密码
     */
    private void postLogin(String userName, String passWord) {
        params.clear();
        params.put(NetCode.ACCOUNT, userName);
        params.put(NetCode.PASSWORD, passWord);
        String url = address + NetCode.VOD_LOGIN;

        OkHttpHelper.getInstance().post(url, params, new SimpleCallback<User>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, User user) {
                //判断注册是否成功 0 失败 1 成功 2 已经注册
                int resultCode = user.getResultCode();
                Log.e("postLogin", "resultCode:" + resultCode);
            }
        });

    }

    /**
     * 请求服务器参数信息列表 详细信息
     *
     * @param page    页码数
     * @param rows    每页显示的数量
     * @param matchNo 赛区编号
     */
    private void postCompetionDetailList(int page, int rows, int matchNo) {

        params.clear();
        params.put(NetCode.PAGE, page);
        params.put(NetCode.ROWS, rows);
        params.put(NetCode.MATCH_NO, matchNo);

        String url = address + NetCode.VOD_MATCHVIDEO_LIST;

        OkHttpHelper.getInstance().post(url, params, new SimpleCallback<String>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, String s) {
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<CompetionDetailInfo>>() {
                    }.getType();
                    JSONObject jsonObj = new JSONObject(s);

                    gson.fromJson(jsonObj.get(NetCode.ROWS).toString(), listType);

                    Log.e("postCompetionListDetail", s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 请求服务器参数信息列表
     *
     * @param page 页码数
     * @param row  每页显示的数量
     */
    private void postCompetionList(int page, int row) {

        params.clear();
        params.put(NetCode.PAGE, page);
        params.put(NetCode.ROWS, row);

        String url = address + NetCode.VOD_MATCH_LIST;

        OkHttpHelper.getInstance().post(url, params, new SimpleCallback<String>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, String s) {
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<CompetionInfo>>() {
                    }.getType();
                    JSONObject jsonObj = new JSONObject(s);

                    List<CompetionInfo> datas = gson.fromJson(jsonObj.get(NetCode.ROWS).toString(), listType);

                    Message msg = handler.obtainMessage();
                    msg.obj = datas;
                    msg.what = NetCode.POST_COMPETITION_LIST;
                    handler.sendMessage(msg);

                    Log.e("postCompetionList", s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

}
