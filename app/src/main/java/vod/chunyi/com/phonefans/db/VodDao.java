package vod.chunyi.com.phonefans.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vod.chunyi.com.phonefans.PhoneFansApplication;
import vod.chunyi.com.phonefans.bean.CompetionInfo;
import vod.chunyi.com.phonefans.bean.CompetionDetailInfo;
import vod.chunyi.com.phonefans.bean.Recommend;
import vod.chunyi.com.phonefans.bean.Singer;
import vod.chunyi.com.phonefans.bean.Song;
import vod.chunyi.com.phonefans.bean.SongListDetail;
import vod.chunyi.com.phonefans.bean.SongType;
import vod.chunyi.com.phonefans.bean.User;

/**
 * 数据库业务操作类
 * Created by knight on 2017/4/11.
 */

public class VodDao {
    /**
     * 查询数据库完成
     */
    public static final int QUERY_SQL_FINISH = 0X1001;

    /**
     * 歌单页面数据
     */
    private List<SongListDetail> songListDetails;

    private SQLiteDatabase db;
    /**
     * 歌曲分类
     */
    private List<SongType> songTypes;
    /**
     * 已选列表
     */
    public List<Song> selectSongs = new ArrayList<Song>();
    /**
     * 收缩歌曲的集合
     */
    private List<Song> searchSongList;
    /**
     * 收缩歌手的集合
     */
    private List<Singer> searchSingerList;
    /**
     * 收缩推荐列表的集合
     */
    private List<Recommend> recommends;
    /**
     * 在已選列表當中選中指定的條數
     */
    private List<Song> songListPR;
    /**
     * 判断是否连接到服务器
     */
    private boolean isNetToServer = false;
    /**
     * 连接服务器从建立连接到取得服务器返回数据的时间
     */
    private long loadTime;
    /**
     * 判断登陆是否成功 0失败 1 成功
     */
    public int login = 0;

    /**
     * 经过加密后的机械码
     */
    public String machineCode = "";


    /**
     * 塞況列表集合
     */
    public List<CompetionInfo> competions = new ArrayList<CompetionInfo>();
    /**
     * 比赛地区详情
     */
    public List<CompetionDetailInfo> competionDetails = new ArrayList<CompetionDetailInfo>();
    /**
     * 用于储存登陆用户的信息
     */
    public User user;
    /**
     * 验证码
     */
    public String smsCode;
    /**
     * 判断是否获取验证码成功 0 失败 1 成功
     */
    public int GetsmsCode;
    /**
     * 判断注册是否成功 0 失败 1 成功 2 已经注册
     */
    public int register;
    /**
     * 用于储存临时用户的信息 比如在找回密码时用户不一定登陆需要临时储存
     */
    public User sofrtUser;
    /**
     * 请求服务器修改密码是否成功 0 失败 1 成功
     */
    public int changePassword;
    /**
     * 请求服务器查询电话 0 没有该用户 1 有
     */
    public int postQueryByNo;
    private PhoneFansApplication application;

    public VodDao(PhoneFansApplication application) {

        this.application = application;

    }

    /**
     * 功能： 查询推荐页面数据
     *
     * @param page 页码数
     * @param rows 每页显示的数量
     * @return
     */
    public List<Recommend> queryRecommendList(int page, int rows) {
        recommends = null;
        recommends = new ArrayList<Recommend>();
        String sql = "select * from info_recommnd LIMIT ?,?";
        Cursor cur = db.rawQuery(
                sql,
                new String[]{String.valueOf((page - 1) * rows),
                        String.valueOf(rows)});

        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    Recommend rec = new Recommend();

                    rec.setCover_pic_name(cur.getString(cur
                            .getColumnIndex("cover_pic_name")));
                    rec.setRecommend_id(cur.getInt(cur
                            .getColumnIndex("recommend_id")));

                    rec.setCover_pic_path(cur.getString(cur
                            .getColumnIndex("cover_pic_path")));

                    rec.setSubtitle(cur.getString(cur
                            .getColumnIndex("subtitle")));
                    rec.setTitle(cur.getString(cur.getColumnIndex("title")));
                    recommends.add(rec);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return recommends;
    }

    /**
     * 查询推荐页面总数
     *
     * @return
     */
    public int queryRecommendNum() {
        int count = 0;
        String sql = "select * from info_recommnd";

        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }
        return count;
    }

    /**
     * 功能： 根据推荐id获取推荐列表 详细信息歌曲
     *
     * @param recommend_id
     * @param page
     * @param rows
     * @return
     */
    public List<Song> searchSongsByRecomendId(int recommend_id, int page, int rows) {

        searchSongList = null;
        searchSongList = new ArrayList<Song>();
        String sql = null;

        sql = "select song.* FROM info_recommnd_song rs LEFT JOIN info_song song ON song.song_id =rs.song_id WHERE rs.recommend_id = "
                + recommend_id + " LIMIT ?,?";

        Cursor cur = db.rawQuery(
                sql,
                new String[]{String.valueOf((page - 1) * rows),
                        String.valueOf(rows)});

        if (cur != null) {
            if (cur.moveToFirst()) {
                do {

                    Song song = new Song();
                    song.setSinger_id(cur.getInt(cur
                            .getColumnIndex("singer_id")));
                    song.setSong_name(cur.getString(cur
                            .getColumnIndex("song_name")));
                    song.setSong_id(cur.getInt(cur.getColumnIndex("song_id")));
                    song.setSong_file_path(cur.getString(cur
                            .getColumnIndex("song_file_path")));
                    song.setSinger_name(cur.getString(cur
                            .getColumnIndex("singer_name")));

                    if (songSelect(song)) {
                        song.setSongSelect(1);
                    } else {
                        song.setSongSelect(0);
                    }

                    searchSongList.add(song);

                } while (cur.moveToNext());
            }
            cur.close();
        }

        return searchSongList;

    }

    /**
     * 某一个推荐列表下的歌曲总数
     *
     * @param recommend_id 推荐列表的id
     * @return
     */
    public int searchSongsByRecomendIdNum(int recommend_id) {
        int count = 0;
        String sql = "select * from info_recommnd_song where recommend_id="
                + recommend_id;

        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }
        return count;
    }

    /**
     * 功能：查询歌单页面数据
     *
     * @param page 页数
     * @return recommendList页面数据
     */
    public List<SongListDetail> getSongListDetail(int page, int rows) {

        songListDetails = null;
        songListDetails = new ArrayList<SongListDetail>();
        String sql = "select * from info_song_sheet ORDER BY heat_num DESC LIMIT ?,?";
        Cursor cur = db.rawQuery(
                sql,
                new String[]{String.valueOf((page - 1) * rows),
                        String.valueOf(rows)});
        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    SongListDetail rec = new SongListDetail();

                    rec.setSong_sheet_name(cur.getString(cur
                            .getColumnIndex("song_sheet_name")));
                    rec.setHeat_num(cur.getInt(cur.getColumnIndex("heat_num")));
                    rec.setSong_sheet_id(cur.getInt(cur
                            .getColumnIndex("song_sheet_id")));
                    rec.setCover_pic_name(cur.getString(cur
                            .getColumnIndex("cover_pic_name")));
                    rec.setCover_pic_path(cur.getString(cur
                            .getColumnIndex("cover_pic_path")));
                    songListDetails.add(rec);
                } while (cur.moveToNext());
            }
        }
        cur.close();

        return songListDetails;
    }

    /**
     * 某一个歌单下的所有歌曲总数
     *
     * @param song_sheet_id
     * @return
     */
    public int getSongListDetailNum(int song_sheet_id) {
        int count = 0;
        String sql = "select * from info_song_sheet_list where song_sheet_id="
                + song_sheet_id;

        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }
        return count;
    }

    /**
     * 根据歌单id查询该歌单下面的所有歌曲
     *
     * @param song_sheet_id
     * @param page
     * @param rows
     * @return
     */
    public List<Song> searchSongsBySongListId(int song_sheet_id, int page, int rows) {

        searchSongList = null;
        searchSongList = new ArrayList<Song>();

        String sql = "select song.* FROM info_song_sheet_list rs LEFT JOIN info_song song ON song.song_id =rs.song_id WHERE rs.song_sheet_id = "
                + song_sheet_id + " LIMIT ?,?";

        Cursor cur = db.rawQuery(
                sql,
                new String[]{String.valueOf((page - 1) * rows),
                        String.valueOf(rows)});

        if (cur != null) {
            if (cur.moveToFirst()) {
                do {

                    Song song = new Song();
                    song.setSinger_id(cur.getInt(cur
                            .getColumnIndex("singer_id")));
                    song.setSong_name(cur.getString(cur
                            .getColumnIndex("song_name")));
                    song.setSong_id(cur.getInt(cur.getColumnIndex("song_id")));
                    song.setSong_file_path(cur.getString(cur
                            .getColumnIndex("song_file_path")));
                    song.setSinger_name(cur.getString(cur
                            .getColumnIndex("singer_name")));

                    if (songSelect(song)) {
                        song.setSongSelect(1);
                    } else {
                        song.setSongSelect(0);
                    }

                    searchSongList.add(song);

                } while (cur.moveToNext());
            }
            cur.close();
        }

        return searchSongList;

    }

    /**
     * 查询推荐页面的所有信息
     *
     * @return
     */
    public List<SongType> getSongTypes() {

        songTypes = new ArrayList<SongType>();
        Cursor cur = db.rawQuery("SELECT * FROM info_song_type", null);
        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    SongType songType = new SongType();
                    songType.setType_id(cur.getInt(cur
                            .getColumnIndex("type_id")));
                    songType.setIs_used(cur.getInt(cur
                            .getColumnIndex("is_used")));
                    songType.setSort_no(cur.getInt(cur
                            .getColumnIndex("sort_no")));
                    songType.setType_name(cur.getString(cur
                            .getColumnIndex("type_name")));
                    songTypes.add(songType);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return songTypes;

    }

    /**
     * 根据歌曲拼音查询指定歌曲类型的歌曲 对于比较耗时间的查询操作 放在子线程当中进行 所以加入了hander 以便更新ui
     *
     * @param type_id 歌曲类型id
     * @param song_py 拼音
     * @param page    查询页码数 指定从第一开始
     * @param rows    每页显示的数量
     * @return 歌曲集合
     */
    public List<Song> searchSongsBySongTypeIdAndSongpy(int type_id, String song_py, int page, int rows) {

        searchSongList = null;
        searchSongList = new ArrayList<Song>();
        String sql = "";
        if (song_py == null || song_py.equals("")) {
            sql = "select * from info_song where type_id=" + type_id
                    + " LIMIT ?,?";
        } else {
            sql = "select * from info_song where type_id=" + type_id
                    + " and song_py LIKE '%" + song_py + "%' LIMIT ?,?";
        }

        Cursor cur = db.rawQuery(
                sql,
                new String[]{String.valueOf((page - 1) * rows),
                        String.valueOf(rows)});

        if (cur != null) {
            if (cur.moveToFirst()) {
                do {

                    Song song = new Song();
                    song.setSinger_id(cur.getInt(cur
                            .getColumnIndex("singer_id")));
                    song.setSinger_name(cur.getString(cur
                            .getColumnIndex("singer_name")));
                    song.setSong_name(cur.getString(cur
                            .getColumnIndex("song_name")));
                    song.setSong_id(cur.getInt(cur.getColumnIndex("song_id")));
                    song.setSong_file_path(cur.getString(cur
                            .getColumnIndex("song_file_path")));
                    if (songSelect(song)) {
                        song.setSongSelect(1);
                    } else {
                        song.setSongSelect(0);
                    }

                    searchSongList.add(song);

                } while (cur.moveToNext());
            }
            cur.close();

        }
        return searchSongList;
    }

    /**
     * 根据歌手id查询该歌手的歌曲总数
     *
     * @param singer_id
     * @return
     */
    public int searchSongNumBySingerID(int singer_id) {
        int count = 0;
        String sql = "select * from info_song where singer_id=" + singer_id;
        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }
        return count;
    }

    /**
     * 根据歌曲类型和歌曲拼音查询歌曲总数
     *
     * @param type_id 类型id
     * @param song_py 歌曲拼音
     * @return
     */
    public int searchSongNumBypyAndType(int type_id, String song_py) {
        int count = 0;
        String sql = "";
        if (song_py == null || song_py.equals("")) {
            sql = "select * from info_song where type_id=" + type_id;

        } else {

            sql = "select * from info_song where type_id=" + type_id
                    + " and song_py LIKE '%" + song_py + "%'";
        }

        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }
        return count;
    }

    /**
     * 根据歌曲类型名字查询 歌曲类型的id
     *
     * @param songTypeName 歌曲类型名字
     * @return 歌曲类型的id
     */
    public int getSongTypeIdByName(String songTypeName) {
        int type_id = 0;
        Cursor cur = db.rawQuery(
                "SELECT * FROM info_song_type WHERE type_name = "
                        + songTypeName, null);
        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    type_id = cur.getInt(cur.getColumnIndex("type_id"));
                } while (cur.moveToNext());
            }
        }
        cur.close();

        return type_id;
    }

    /**
     * 根据歌手id查询歌手的歌曲
     *
     * @param singer_id
     * @param page
     * @param rows
     * @return
     */
    public List<Song> querBySingerId(int singer_id, int page, int rows) {

        searchSongList = null;
        searchSongList = new ArrayList<Song>();
        String sql = "select * from info_song where singer_id=" + singer_id
                + " LIMIT ?,?";

        Cursor cur = db.rawQuery(
                sql,
                new String[]{String.valueOf((page - 1) * rows),
                        String.valueOf(rows)});
        if (cur != null) {
            if (cur.moveToFirst()) {
                do {

                    Song song = new Song();
                    song.setSinger_id(singer_id);
                    song.setSong_name(cur.getString(cur
                            .getColumnIndex("song_name")));
                    song.setSong_id(cur.getInt(cur.getColumnIndex("song_id")));
                    song.setSong_file_path(cur.getString(cur
                            .getColumnIndex("song_file_path")));
                    song.setSinger_name(cur.getString(cur
                            .getColumnIndex("singer_name")));

                    if (songSelect(song)) {
                        song.setSongSelect(1);
                    } else {
                        song.setSongSelect(0);
                    }

                    searchSongList.add(song);

                } while (cur.moveToNext());
            }
            cur.close();
        }
        return searchSongList;
    }

    /**
     * 查询所有歌手的总数
     *
     * @return
     */
    public int searchsingersNum() {

        int count = 0;
        String sql = "select * from info_singer";
        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }

        return count;

    }

    /**
     * 查询歌单的总数
     *
     * @return
     */
    public int searchsongListsNum() {

        int count = 0;
        String sql = "select * from info_song_sheet";
        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }

        return count;
    }

    /**
     * 查询推荐总数
     *
     * @return
     */
    public int searchrecommendsNum() {

        int count = 0;
        String sql = "select * from info_recommnd";
        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }

        return count;

    }

    /**
     * 根据歌手类型对歌手进行收缩
     *
     * @param type_code 歌手类型 类型编号 01 华语男 02 华语女 03日韩男 04 日韩女 05 欧美男 06 欧美女 07 其他歌手
     *                  如果没有传入null或者空
     * @return 整个歌手列表
     */
    public List<Singer> searchSingerBySingerType(String type_code, int page,
                                                 int rows) {

        searchSingerList = null;
        searchSingerList = new ArrayList<Singer>();
        String sql;
        if (type_code == null || type_code.equals("")) {
            sql = "select * from info_singer ORDER BY HEAT_NUM DESC LIMIT ?,?";
        } else {

            sql = "select * from info_singer where type_code=" + type_code
                    + " ORDER BY HEAT_NUM DESC LIMIT ?,?";
        }

        Cursor cur = db.rawQuery(
                sql,
                new String[]{String.valueOf((page - 1) * rows),
                        String.valueOf(rows)});
        if (cur != null) {

            if (cur.moveToFirst()) {
                do {

                    Singer singer = new Singer();
                    singer.setSinger_id(cur.getInt(cur
                            .getColumnIndex("singer_id")));
                    singer.setHeat_num(cur.getInt(cur
                            .getColumnIndex("heat_num")));
                    singer.setSinger_code(cur.getString(cur
                            .getColumnIndex("singer_code")));
                    singer.setSinger_image(cur.getString(cur
                            .getColumnIndex("singer_image")));
                    singer.setSinger_path(cur.getString(cur
                            .getColumnIndex("singer_path")));
                    singer.setSinger_name(cur.getString(cur
                            .getColumnIndex("singer_name")));
                    singer.setSinger_py(cur.getString(cur
                            .getColumnIndex("singer_py")));
                    singer.setType_code(cur.getString(cur
                            .getColumnIndex("type_code")));

                    searchSingerList.add(singer);

                } while (cur.moveToNext());
            }
            cur.close();
        }

        return searchSingerList;
    }

    /**
     * 根據歌手拼音查詢歌手
     *
     * @param singer_py
     * @param page
     * @param rows
     * @return
     */
    public List<Singer> searchSingerBySingerPy(String singer_py, int page,
                                               int rows) {

        searchSingerList = null;
        searchSingerList = new ArrayList<Singer>();
        String sql;
        if (singer_py == null || singer_py.equals("")) {
            sql = "select * from info_singer ORDER BY HEAT_NUM DESC LIMIT ?,?";
        } else {

            sql = "select * from info_singer where singer_py  LIKE '%"
                    + singer_py + "%' ORDER BY HEAT_NUM DESC LIMIT ?,?";
        }

        Cursor cur = db.rawQuery(
                sql,
                new String[]{String.valueOf((page - 1) * rows),
                        String.valueOf(rows)});
        if (cur != null) {

            if (cur.moveToFirst()) {
                do {

                    Singer singer = new Singer();
                    singer.setSinger_id(cur.getInt(cur
                            .getColumnIndex("singer_id")));
                    singer.setHeat_num(cur.getInt(cur
                            .getColumnIndex("heat_num")));
                    singer.setSinger_code(cur.getString(cur
                            .getColumnIndex("singer_code")));
                    singer.setSinger_image(cur.getString(cur
                            .getColumnIndex("singer_image")));
                    singer.setSinger_path(cur.getString(cur
                            .getColumnIndex("singer_path")));
                    singer.setSinger_name(cur.getString(cur
                            .getColumnIndex("singer_name")));
                    singer.setSinger_py(cur.getString(cur
                            .getColumnIndex("singer_py")));
                    singer.setType_code(cur.getString(cur
                            .getColumnIndex("type_code")));

                    searchSingerList.add(singer);

                } while (cur.moveToNext());
            }
            cur.close();
        }

        return searchSingerList;
    }

    /**
     * 根據歌曲拼音查询歌曲
     *
     * @param song_py
     * @param page
     * @param rows
     * @return
     */
    public List<Song> searchSongBySongPy(String song_py, int page, int rows) {

        searchSongList = null;
        searchSongList = new ArrayList<Song>();
        String sql;
        if (song_py == null || song_py.equals("")) {
            sql = "select * from info_song LIMIT ?,?";
        } else {

            sql = "select * from info_song where song_py  LIKE '%" + song_py
                    + "%' LIMIT ?,?";
        }

        Cursor cur = db.rawQuery(
                sql,
                new String[]{String.valueOf((page - 1) * rows),
                        String.valueOf(rows)});
        if (cur != null) {

            if (cur.moveToFirst()) {
                do {

                    Song song = new Song();
                    song.setSinger_id(cur.getInt(cur
                            .getColumnIndex("singer_id")));

                    song.setSong_name(cur.getString(cur
                            .getColumnIndex("song_name")));
                    song.setSong_id(cur.getInt(cur.getColumnIndex("song_id")));
                    song.setSong_file_path(cur.getString(cur
                            .getColumnIndex("song_file_path")));
                    if (songSelect(song)) {
                        song.setSongSelect(1);
                    } else {
                        song.setSongSelect(0);
                    }

                    searchSongList.add(song);

                } while (cur.moveToNext());
            }
            cur.close();
        }

        return searchSongList;
    }

    /**
     * 根据歌曲拼音查询歌曲总数
     *
     * @param song_py 如果没有传入null或者空
     * @return
     */
    public int querySongConutBySongpy(String song_py) {
        int count = 0;
        String sql;

        if (song_py == null || "".equals(song_py)) {
            sql = "select * from info_song";
        } else {
            sql = "select * from info_song where  song_py LIKE '%" + song_py
                    + "%'";
        }
        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }
        return count;
    }

    /**
     * 根据歌手类型查询歌手总数
     *
     * @param type_code 歌手类型 类型编号 01 华语男 02 华语女 03日韩男 04 日韩女 05 欧美男 06 欧美女 07 其他歌手
     *             如果没有传入null或者空
     * @return
     */
    public int querySingerConutByTypeCode(String type_code) {
        int count = 0;
        String sql;

        if (type_code == null || "".equals(type_code)) {
            sql = "select * from info_singer";
        } else {
            sql = "select * from info_singer where type_code=" + type_code;

        }
        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }
        return count;
    }

    /**
     * 根据歌手类型查询歌手总数
     *
     * @param singer_py
     * @return
     */
    public int querySingerCountBySingerpy(String singer_py) {
        int count = 0;
        String sql;

        if (singer_py == null || "".equals(singer_py)) {
            sql = "select * from info_singer";
        } else {
            sql = "select * from info_singer where singer_py LIKE '%"
                    + singer_py + "%'";

        }
        Cursor cur = db.rawQuery(sql, null);
        if (cur != null) {
            count = cur.getCount();
            cur.close();
        }
        return count;
    }

    /**
     * 按照歌曲名查询歌曲
     *
     * @param type     输入类型 0 默认全部歌曲 1 查询指定推荐列表下的歌曲 2 查询指定歌手下面的歌曲 3 查询指定歌单下面的歌曲
     * @param songName 歌曲名
     * @param ID       查询id 如果是全部歌曲则输入0
     * @return
     */
    public List<Song> searchSongBySongName(int type, String songName, int ID) {

        searchSongList = null;
        searchSongList = new ArrayList<Song>();
        String sql = null;
        if (type == 0) {
            sql = "select * from info_song where song_name LIKE '%" + songName
                    + "%'";
        } else if (type == 1) {
            sql = "select song.* FROM info_recommnd_song rs LEFT JOIN info_song song ON song.song_id =rs.song_id WHERE rs.recommend_id = "
                    + ID + " AND song_name LIKE '%" + songName + "%'";
        } else if (type == 2) {
            sql = "select * FROM info_song WHERE singer_id = " + ID
                    + " AND song_name LIKE '%" + songName + "%'";
        } else if (type == 3) {
            sql = "select song.* FROM info_song_sheet_list rs LEFT JOIN info_song song ON song.song_id =rs.song_id WHERE rs.song_sheet_id = "
                    + ID + " AND song_name LIKE '%" + songName + "%'";
        }

        Cursor cur = db.rawQuery(sql, null);

        if (cur != null) {
            if (cur.moveToFirst()) {
                do {

                    Song song = new Song();
                    song.setSinger_id(cur.getInt(cur
                            .getColumnIndex("singer_id")));
                    song.setSong_name(cur.getString(cur
                            .getColumnIndex("song_name")));
                    song.setSong_id(cur.getInt(cur.getColumnIndex("song_id")));
                    song.setSong_file_path(cur.getString(cur
                            .getColumnIndex("song_file_path")));
                    song.setSinger_name(cur.getString(cur
                            .getColumnIndex("singer_name")));

                    if (songSelect(song)) {
                        song.setSongSelect(1);
                    } else {
                        song.setSongSelect(0);
                    }

                    searchSongList.add(song);

                } while (cur.moveToNext());
            }
            cur.close();
        }

        return searchSongList;
    }

    /**
     * 判断该首歌曲是否在已选列表当中
     *
     * @param song
     * @return
     */
    public boolean songSelect(Song song) {
        boolean flag = false;

        if (selectSongs != null && selectSongs.size() > 0) {

            for (int i = 0; i < selectSongs.size(); i++) {
                if (song.getSong_id() == selectSongs.get(i).getSong_id()) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    /**
     * 功能：将歌曲移除已选列表
     *
     * @param song 歌曲
     */
    public boolean deleteSongFromSongList(Song song) {
        int songId = song.getSong_id();

        boolean flag = false;
        if (selectSongs != null && selectSongs.size() > 0) {
            for (int i = 0; i < selectSongs.size(); i++) {
                // 第一首不做移除操作
                // if (songId != selectSongs.get(0).getSong_id()) {
                if (songId == selectSongs.get(i).getSong_id()) {
                    selectSongs.remove(i);
                    flag = true;
                }
                // } else {
                // flag = false;
                // }
            }
        }
        return flag;
    }

    public SQLiteDatabase getDB() {
        return db;
    }

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * 功能：根据传入的页码数和每页显示的数量 返回指定已选歌曲的列表
     *
     * @param page 页码数
     * @param rows 每页显示的数量
     * @return
     */
    public List<Song> getSelectedSongByPR(int page, int rows) {
        songListPR = null;
        songListPR = new ArrayList<Song>();
        if (selectSongs != null && selectSongs.size() > (page - 1) * rows) {

            for (int i = (page - 1) * rows; i < ((selectSongs.size() - (page - 1)
                    * rows) > rows ? rows : (selectSongs.size() - (page - 1)
                    * rows)); i++) {
                songListPR.add(selectSongs.get(i));
            }
        }

        return songListPR;
    }

    public void setSelectSongs(List<Song> selectSongs) {
        this.selectSongs = selectSongs;
    }

    public boolean isNetToServer() {
        return isNetToServer;
    }

    public void setNetToServer(boolean isNetToServer) {
        this.isNetToServer = isNetToServer;
    }

    public long getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }

    public List<CompetionInfo> getCompetions() {
        return competions;
    }

    public void setCompetions(List<CompetionInfo> competions) {
        this.competions = competions;
    }

    public List<CompetionDetailInfo> getCompetionDetails() {
        return competionDetails;
    }

    public void setCompetionDetails(List<CompetionDetailInfo> competionDetails) {
        this.competionDetails = competionDetails;
    }

    public List<Song> getSelectSongs() {
        return selectSongs;
    }

    public List<Song> getSearchSongList() {
        return searchSongList;
    }

    public void setSearchSongList(List<Song> searchSongList) {
        this.searchSongList = searchSongList;
    }

}
