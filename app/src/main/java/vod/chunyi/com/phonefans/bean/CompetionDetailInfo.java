package vod.chunyi.com.phonefans.bean;

/**
 * 具体比赛信息
 */
public class CompetionDetailInfo {

    private int page;

    private int rows;

    private String sort;

    private String order;

    private String matchNo;

    private int recordId;

    private String recordCode;

    private String songName;

    private String userName;

    private String coverImgPath;

    private String songVideoFile;

    private String songVideoPath;

    private String songVideoTime;

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

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public String getMatchNo() {
        return this.matchNo;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getRecordId() {
        return this.recordId;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getRecordCode() {
        return this.recordCode;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setCoverImgPath(String coverImgPath) {
        this.coverImgPath = coverImgPath;
    }

    public String getCoverImgPath() {
        return this.coverImgPath;
    }

    public void setSongVideoFile(String songVideoFile) {
        this.songVideoFile = songVideoFile;
    }

    public String getSongVideoFile() {
        return this.songVideoFile;
    }

    public void setSongVideoPath(String songVideoPath) {
        this.songVideoPath = songVideoPath;
    }

    public String getSongVideoPath() {
        return this.songVideoPath;
    }

    public void setSongVideoTime(String songVideoTime) {
        this.songVideoTime = songVideoTime;
    }

    public String getSongVideoTime() {
        return this.songVideoTime;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getIsUsed() {
        return this.isUsed;
    }
}

