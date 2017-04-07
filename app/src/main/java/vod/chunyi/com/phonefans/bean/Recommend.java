package vod.chunyi.com.phonefans.bean;

public class Recommend {
	private int recommend_id;
	private String title;
	private String subtitle;
	private String cover_pic_name;
	private String cover_pic_path;

	public int getRecommend_id() {
		return recommend_id;
	}

	public void setRecommend_id(int recommend_id) {
		this.recommend_id = recommend_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getCover_pic_name() {
		return cover_pic_name;
	}

	public void setCover_pic_name(String cover_pic_name) {
		this.cover_pic_name = cover_pic_name;
	}

	public String getCover_pic_path() {
		return cover_pic_path;
	}

	public void setCover_pic_path(String cover_pic_path) {
		this.cover_pic_path = cover_pic_path;
	}

}
