package com.chunyi.vod.dao;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vod.chunyi.com.phonefans.bean.User;
import vod.chunyi.com.phonefans.db.VodDao;
import vod.chunyi.com.phonefans.db.VodStaticNum;

public class MyAsyncTask extends AsyncTask {

    /**
     * 发送给服务器的请求指令
     */
    private int post = 0;

    /**
     * 没有连接到服务器
     */
    public static final int NoNetToServer = 0X200;
    /**
     * 请求服务器的工具类
     */
    private HttpJSONClient jsonClient = new HttpJSONClient();
    /**
     * 关联的dao
     */
    private VodDao dao;
    /**
     * 请求服务器后返回的json
     */
    private JSONObject backLogMessage;
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
     * TODO
     */
    public static final String address = "http://119.29.84.88/fans/";
    /**
     * 执行任务之前时间
     */
    private long preTime;
    /**
     * 执行任务之后时间
     */
    private long lTime;

    /**
     * 执行之前
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preTime = new Date().getTime();
    }

    @Override
    protected Object doInBackground(Object... params) {
        // 判断是否连接到服务器
        dao.setNetToServer(isConnByHttp());
        if (dao.isNetToServer()) {
            switch (post) {
                case VodStaticNum.POST_COMPETITION_LIST:
                    // 请求服务器参数信息列表
                    if (params != null && params.length == 2) {
                        int page = (Integer) params[0];
                        int rows = (Integer) params[1];
                        postCompetionList(page, rows);
                    }
                    break;
                case VodStaticNum.POST_COMPETITION_LIST_DETAIL:
                    // 请求服务器参数信息列表
                    if (params != null && params.length == 3) {
                        int page = (Integer) params[0];
                        int rows = (Integer) params[1];
                        int matchNo = (Integer) params[2];
                        postCompetionListDetail(page, rows, matchNo);
                    }
                    break;

                case VodStaticNum.POST_LOGIN:
                    if (params != null && params.length == 2) {
                        String userName = (String) params[0];
                        String passWord = (String) params[1];

                        postLogin(userName, passWord);
                    }
                    break;
                case VodStaticNum.POST_REGISTER:

                    if (params != null && params.length == 2) {
                        String phoneNo = (String) params[0];
                        String loginPsw = (String) params[1];

                        postRegister(phoneNo, loginPsw);
                    }
                    break;
                case VodStaticNum.POST_GETSMSCODE:
                    if (params != null && params.length == 1) {
                        String phoneNo = (String) params[0];

                        postGetSmsCode(phoneNo);
                    }
                    break;

                case VodStaticNum.POST_CHANGPASSWORD:
                    if (params != null && params.length == 1) {
                        String password = (String) params[0];

                        postchangePassword(password);
                    }
                    break;
                case VodStaticNum.POST_QUERY_USERBYNUM:
                    if (params != null && params.length == 1) {
                        String phoneNum = (String) params[0];

                        postqueryUserByNum(phoneNum);
                    }
                    break;
            }

        } else {
            Message message = new Message();
            message.what = VodStaticNum.NO_CONNECT_SERVER;
            handler.sendMessage(message);
        }
        return null;
    }

    /**
     * 执行结果
     */
    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        Message message = new Message();
        if (!dao.isNetToServer()) {
            // 如果没有连接到服务器发送消息给更新界面
            post = NoNetToServer;
        }
        message.what = post;
        handler.sendMessage(message);

        lTime = new Date().getTime();
        dao.setLoadTime(lTime - preTime);
    }

    private void postqueryUserByNum(String phoneNum) {
        params.clear();

        params.put("phoneNo", phoneNum + "");
        String url = address + "vod_findUserByPhone.json";
        backLogMessage = jsonClient.post(url, params);
        // 返回上传结果
        if (backLogMessage != null && backLogMessage.has("resultCode")) {
            try {
                dao.postQueryByNo = backLogMessage.getInt("resultCode");

                if (dao.postQueryByNo == 1) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<User>() {
                    }.getType();
                    User groupList = new User();
                    groupList = gson.fromJson(backLogMessage.get("user")
                            .toString(), listType);
                    // 存入缓存当中
                    dao.sofrtUser = groupList;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修改密码
     *
     * @param password
     */
    private void postchangePassword(String password) {

        params.clear();
        Integer userId = null;
        if (dao.sofrtUser != null) {
            userId = dao.sofrtUser.getUserId();
        }

        params.put("userId", userId + "");
        params.put("loginPsw", password + "");
        String url = address + "vod_modifyUser.json";
        backLogMessage = jsonClient.post(url, params);

        // 返回上传结果
        if (backLogMessage != null && backLogMessage.has("resultCode")) {
            try {
                dao.changePassword = backLogMessage.getInt("resultCode");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取验证码
     *
     * @param phoneNo
     */
    private void postGetSmsCode(String phoneNo) {

        params.clear();
        params.put("phoneNo", phoneNo + "");
        String url = address + "vod_getSmsCode.json";
        backLogMessage = jsonClient.post(url, params);

        if (backLogMessage != null && backLogMessage.has("resultCode")) {

            try {
                if (backLogMessage.getString("resultCode").equals("0")) {
                    dao.GetsmsCode = 0;
                } else if (backLogMessage.getString("resultCode").equals("1")) {
                    dao.GetsmsCode = 1;
                    if (backLogMessage.has("smsCode")) {
                        dao.smsCode = backLogMessage.getString("smsCode");
                    }

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 注册
     *
     * @param phoneNo
     * @param loginPsw
     */
    private void postRegister(String phoneNo, String loginPsw) {

        params.clear();
        params.put("phoneNo", phoneNo + "");
        params.put("loginPsw", loginPsw + "");
        String url = address + "vod_addUser.json";
        backLogMessage = jsonClient.post(url, params);
        if (backLogMessage != null && backLogMessage.has("resultCode")) {
            try {
                if (backLogMessage.getString("resultCode").equals("0")) {
                    dao.register = 0;
                } else if (backLogMessage.getString("resultCode").equals("1")) {
                    dao.register = 1;
                    Gson gson = new Gson();
                    Type listType = new TypeToken<User>() {
                    }.getType();
                    User groupList = new User();
                    groupList = gson.fromJson(backLogMessage.get("userPo")
                            .toString(), listType);
                    dao.user = groupList;
                } else if (backLogMessage.getString("resultCode").equals("2")) {
                    dao.register = 2;

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 登陆
     *
     * @param userName 用户名
     * @param passWord 密码
     */
    private void postLogin(String userName, String passWord) {
        params.clear();
        params.put("account", userName + "");
        params.put("password", passWord + "");
        String url = address + "vod_login.json";
        backLogMessage = jsonClient.post(url, params);

        if (backLogMessage != null && backLogMessage.has("result")) {
            try {
                if (backLogMessage.get("result").equals("0")) {
                    dao.login = 0;
                } else if (backLogMessage.get("result").equals("1")) {
                    dao.login = 1;
                }
                if (dao.login == 1) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<User>() {
                    }.getType();
                    User groupList = new User();
                    try {
                        groupList = gson.fromJson(backLogMessage.get("user")
                                .toString(), listType);

                        dao.user = groupList;

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 请求服务器参数信息列表 详细信息
     *
     * @param page    页码数
     * @param rows    每页显示的数量
     * @param matchNo 赛区编号
     */
    private void postCompetionListDetail(int page, int rows, int matchNo) {

        params.clear();
        params.put("page", page + "");
        params.put("rows", rows + "");
        params.put("matchNo", matchNo + "");

        String url = address + "vod_matchVideo_list.json";
        backLogMessage = jsonClient.post(url, params);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CompetionDetail>>() {
        }.getType();
        List<CompetionDetail> groupList = new ArrayList<CompetionDetail>();

        if (backLogMessage != null) {

            System.out.println("postCompetionListDetail ====="
                    + backLogMessage.toString());
            try {
                groupList = gson.fromJson(
                        backLogMessage.get("rows").toString(), listType);

                dao.getCompetionDetails().clear();
                dao.setCompetionDetails(groupList);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 请求服务器参数信息列表
     *
     * @param page 页码数
     * @param row  每页显示的数量
     */
    private void postCompetionList(int page, int row) {

        params.clear();
        params.put("page", page + "");
        params.put("rows", row + "");

        String url = address + "vod_match_list.json";
        backLogMessage = jsonClient.post(url, params);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Competion>>() {
        }.getType();
        List<Competion> groupList = new ArrayList<Competion>();
        if (backLogMessage != null) {

            System.out.println("postCompetionList ====="
                    + backLogMessage.toString());
            try {
                groupList = gson.fromJson(
                        backLogMessage.get("rows").toString(), listType);
                dao.getCompetions().clear();
                dao.setCompetions(groupList);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 发送给服务器的请求指令
     *
     * @param post
     */
    public void setUIPost(int post, Handler handler, VodDao dao) {
        this.post = post;
        this.handler = handler;
        this.dao = dao;
    }

    /**
     * 判断是否连接到服务器
     *
     * @return
     */
    public boolean isConnByHttp() {
        boolean isConn = false;
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000 * 8);
            if (conn.getResponseCode() == 200) {
                isConn = true;
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {
            conn.disconnect();
        }
        return isConn;
    }

}
