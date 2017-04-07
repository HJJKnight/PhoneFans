package vod.chunyi.com.phonefans.bean;

public class Singer {
	/**
	 * id
	 */
	private int singer_id;
	/**
	 * 歌手名
	 */
	private String singer_name;

	/**
	 * 歌手编号
	 */
	private String singer_code;
	/**
	 * 歌手拼音
	 */
	private String singer_py;
	/**
	 * 歌手图片名字
	 */
	private String singer_image;
	/**
	 * 类型编号 01华语男 	02华语女	 03日韩男  04日韩女  05欧美男  06欧美女  07其他歌手
	 */
	private String type_code;
	/**
	 * 歌曲路径
	 */
	private String singer_path;
	
	/**
	 * 歌手热度系数
	 */
	private int heat_num;
	
	
	public int getSinger_id() {
		return singer_id;
	}

	public void setSinger_id(int singer_id) {
		this.singer_id = singer_id;
	}

	public String getSinger_name() {
		return singer_name;
	}

	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
	}

	public String getSinger_code() {
		return singer_code;
	}

	public void setSinger_code(String singer_code) {
		this.singer_code = singer_code;
	}

	public String getSinger_py() {
		return singer_py;
	}

	public void setSinger_py(String singer_py) {
		this.singer_py = singer_py;
	}

	public String getSinger_image() {
		return singer_image;
	}

	public void setSinger_image(String singer_image) {
		this.singer_image = singer_image;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getSinger_path() {
		return singer_path;
	}

	public void setSinger_path(String singer_path) {
		this.singer_path = singer_path;
	}

	public int getHeat_num() {
		return heat_num;
	}

	public void setHeat_num(int heat_num) {
		this.heat_num = heat_num;
	}

	
}
