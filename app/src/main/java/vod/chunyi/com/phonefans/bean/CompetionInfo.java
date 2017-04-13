package vod.chunyi.com.phonefans.bean;

/**
 * 比赛信息
 */
public class CompetionInfo {

    private int page;

    private int rows;

    private String sort;

    private String order;

    private int matchNo;

    private String matchName;

    private String coverPicName;

    private String coverPicPath;

    private String isUsed;

    private String operUserId;

    private String operTime;

    private String recordId;

    private int recordNum;

    private String keyWord;

    private String selectVideos;

    private String recordIds;

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

    public void setMatchNo(int matchNo) {
        this.matchNo = matchNo;
    }

    public int getMatchNo() {
        return this.matchNo;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchName() {
        return this.matchName;
    }

    public void setCoverPicName(String coverPicName) {
        this.coverPicName = coverPicName;
    }

    public String getCoverPicName() {
        return this.coverPicName;
    }

    public void setCoverPicPath(String coverPicPath) {
        this.coverPicPath = coverPicPath;
    }

    public String getCoverPicPath() {
        return this.coverPicPath;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getIsUsed() {
        return this.isUsed;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }

    public String getOperUserId() {
        return this.operUserId;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public String getOperTime() {
        return this.operTime;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public int getRecordNum() {
        return this.recordNum;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public void setSelectVideos(String selectVideos) {
        this.selectVideos = selectVideos;
    }

    public String getSelectVideos() {
        return this.selectVideos;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds;
    }

    public String getRecordIds() {
        return this.recordIds;
    }

    @Override
    public String toString() {
        return "CompetionInfo{" +
                "page=" + page +
                ", rows=" + rows +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", matchNo=" + matchNo +
                ", matchName='" + matchName + '\'' +
                ", coverPicName='" + coverPicName + '\'' +
                ", coverPicPath='" + coverPicPath + '\'' +
                ", isUsed='" + isUsed + '\'' +
                ", operUserId='" + operUserId + '\'' +
                ", operTime='" + operTime + '\'' +
                ", recordId='" + recordId + '\'' +
                ", recordNum=" + recordNum +
                ", keyWord='" + keyWord + '\'' +
                ", selectVideos='" + selectVideos + '\'' +
                ", recordIds='" + recordIds + '\'' +
                '}';
    }
}

