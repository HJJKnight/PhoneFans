package vod.chunyi.com.phonefans.bean;

/**
 * Created by knight on 2017/4/10.
 */

public class DBInfo {
   /* {
        "resultCode":1, "dbVersionName":"db_version_1.48.zip", "fileVersion":"1.24", "fileVersionName":"file_version_1.24.zip", "dbVersion":"1.48"
    }*/

    private int resultCode;
    //eg:db_version_1.48.zip
    private String dbVersionName;
    //eg:1.24
    private String fileVersion;
    //eg:file_version_1.24.zip
    private String fileVersionName;
    //eg:1.48
    private String dbVersion;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getDbVersionName() {
        return dbVersionName;
    }

    public void setDbVersionName(String dbVersionName) {
        this.dbVersionName = dbVersionName;
    }

    public String getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getFileVersionName() {
        return fileVersionName;
    }

    public void setFileVersionName(String fileVersionName) {
        this.fileVersionName = fileVersionName;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    @Override
    public String toString() {
        return "DBInfo{" +
                "resultCode=" + resultCode +
                ", dbVersionName='" + dbVersionName + '\'' +
                ", fileVersion='" + fileVersion + '\'' +
                ", fileVersionName='" + fileVersionName + '\'' +
                ", dbVersion='" + dbVersion + '\'' +
                '}';
    }
}
