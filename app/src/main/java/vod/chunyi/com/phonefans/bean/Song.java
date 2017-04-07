package vod.chunyi.com.phonefans.bean;

/**
 * 
 * @author lj
 * 
 */
public class Song {
	/**
	 * id
	 */
	private int song_id;
	/**
	 * 歌曲编码
	 */
	private String song_code;
	/**
	 * 歌曲名
	 */
	private String song_name;

	/**
	 * 歌曲拼音
	 */
	private String song_py;

	private int words;

	private int language_id;
	private int type_id;
	/**
	 * 歌手id
	 */
	private int singer_id;
	/**
	 * 原厂音轨
	 */
	private int original_track_code;
	/**
	 * 伴唱音轨
	 */
	private int accompany_track_code;
	private int original_track_compensate;

	private String song_file;
	/**
	 * 歌曲文件网络路径
	 */
	private String song_file_path;
	/**
	 * 歌曲名字
	 */
	private String singer_name;
	/**
	 * 用于判断该歌曲是否被选择 添加到已选列表 0没有 1选择
	 */
	private int songSelect;

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}

	public String getSong_code() {
		return song_code;
	}

	public void setSong_code(String song_code) {
		this.song_code = song_code;
	}

	public String getSong_name() {
		return song_name;
	}

	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}

	public String getSong_py() {
		return song_py;
	}

	public void setSong_py(String song_py) {
		this.song_py = song_py;
	}

	public int getWords() {
		return words;
	}

	public void setWords(int words) {
		this.words = words;
	}

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public int getSinger_id() {
		return singer_id;
	}

	public void setSinger_id(int singer_id) {
		this.singer_id = singer_id;
	}

	public int getOriginal_track_code() {
		return original_track_code;
	}

	public void setOriginal_track_code(int original_track_code) {
		this.original_track_code = original_track_code;
	}

	public int getAccompany_track_code() {
		return accompany_track_code;
	}

	public void setAccompany_track_code(int accompany_track_code) {
		this.accompany_track_code = accompany_track_code;
	}

	public int getOriginal_track_compensate() {
		return original_track_compensate;
	}

	public void setOriginal_track_compensate(int original_track_compensate) {
		this.original_track_compensate = original_track_compensate;
	}

	public String getSong_file() {
		return song_file;
	}

	public void setSong_file(String song_file) {
		this.song_file = song_file;
	}

	public String getSong_file_path() {
		return song_file_path;
	}

	public void setSong_file_path(String song_file_path) {
		this.song_file_path = song_file_path;
	}

	public String getSinger_name() {
		return singer_name;
	}

	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
	}

	public int getSongSelect() {
		return songSelect;
	}

	public void setSongSelect(int songSelect) {
		this.songSelect = songSelect;
	}

	@Override
	public String toString() {
		return "song_id = " + song_id + ", song_code=" + song_code
				+ ", song_name=" + song_name + ", song_py=" + song_py
				+ ", words=" + words + ", language_id=" + language_id
				+ ", type_id=" + type_id + ", singer_id=" + singer_id
				+ ", original_track_code=" + original_track_code
				+ ", accompany_track_code=" + accompany_track_code
				+ ", original_track_compensate=" + original_track_compensate
				+ ", song_file=" + song_file + ", song_file_path="
				+ song_file_path + ", singer_name=" + singer_name
				+ ", songSelect=" + songSelect ;
	}

}
