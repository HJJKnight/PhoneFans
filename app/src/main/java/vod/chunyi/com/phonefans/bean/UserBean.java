package vod.chunyi.com.phonefans.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户实体类
 */
public class UserBean implements Serializable {

    //用户注册状态
    @SerializedName("result")
    private int resultCode;
    //用户注册详情
    private User user;

    public static class User implements Serializable{
        private int page;

        private int rows;

        private String sort;

        private String order;

        //用户id
        private int userId;
        //用户名称
        private String userName;
        //用户手机号码
        private String phoneNo;
        //用户密码
        private String loginPsw;
        //用户注册时间
        private String createTime;
        //用户头像
        private String headImagUrl;
        //用户性别
        private String sex;
        //用户年龄
        private String age;
        //用户区域
        private String area;
        //用户最后登陆时间
        private String lastLoginTime;

        private String isUsed;

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage() {
            return this.page;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getRows() {
            return this.rows;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getSort() {
            return this.sort;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getOrder() {
            return this.order;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getUserId() {
            return this.userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return this.userName;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getPhoneNo() {
            return this.phoneNo;
        }

        public void setLoginPsw(String loginPsw) {
            this.loginPsw = loginPsw;
        }

        public String getLoginPsw() {
            return this.loginPsw;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setHeadImagUrl(String headImagUrl) {
            this.headImagUrl = headImagUrl;
        }

        public String getHeadImagUrl() {
            return this.headImagUrl;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSex() {
            return this.sex;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAge() {
            return this.age;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getArea() {
            return this.area;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getLastLoginTime() {
            return this.lastLoginTime;
        }

        public void setIsUsed(String isUsed) {
            this.isUsed = isUsed;
        }

        public String getIsUsed() {
            return this.isUsed;
        }

    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

}
