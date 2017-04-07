package vod.chunyi.com.phonefans.bean;

/**
 * ��������
 * 
 * @author lj
 * 
 */
public class SongType {
	/**
	 * ����id
	 */
	private int type_id;
	/**
	 * ����
	 */
	private String type_name;
	/**
	 * ����
	 */
	private int sort_no;
	/**
	 * �Ƿ���Ч 1 ��Ч 0 ��Ч
	 */
	private int is_used;

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public int getSort_no() {
		return sort_no;
	}

	public void setSort_no(int sort_no) {
		this.sort_no = sort_no;
	}

	public int getIs_used() {
		return is_used;
	}

	public void setIs_used(int is_used) {
		this.is_used = is_used;
	}

}
