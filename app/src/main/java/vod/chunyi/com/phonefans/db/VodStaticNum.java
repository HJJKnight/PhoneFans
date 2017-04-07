package vod.chunyi.com.phonefans.db;

/**
 * ���ڴ������������
 * 
 * @author lj
 * 
 */
public class VodStaticNum {
	// ��ѯ���ݿ����
	public static final int QUERY_SQL_FINISH = 0X1001;
	public static final int POST_COMPETITION_LIST = 0X107;// ��������������б�
	public static final int POST_COMPETITION_LIST_DETAIL = 0X108;// ��������������б�������Ϣ
	public static final int POST_LOGIN = 0X101;// ��½
	public static final int NO_CONNECT_SERVER = 0X102;// û�����ӵ�������
	public static final int POST_REGISTER = 0X103;// ע��
	public static final int POST_GETSMSCODE = 0X104;// ��ȡ��֤��
	public static final int POST_CHANGPASSWORD = 0X105;// ��������
	public static final int POST_QUERY_USERBYNUM = 0X106;// �����ֻ������ѯ�û���Ϣ
	public static final int POST_QUERY_SELECT_SONG = 0X107;// �����ֻ������ѯ��ѡ����
	public static final int ADD_SONG = 0X21;// �Ӹ���
	public static final int ADD_SONG_FAILE = 0X22;// �Ӹ���ʧ��
	public static final int DETELE_SONG = 0X12;// �и�
	public static final int DETELE_SONG_FAILE = 0X13;// �и�ʧ��
	public static final int PLAY_SUCESS = 0X14;// ���ųɹ�
	public static final int PLAY_FAILE = 0X15;
	public static final int PASUE_SUCESS = 0X16;
	public static final int PASUE_FAILE = 0X17;
	public static final int SCOKET_QUERY_SELECT_SONG = 0X18;
	// ���ӳɹ�
	public static final String SCOKET_CONNECT_SUCESS = "com.chunyi.vod.mmediaPlayerService.connect.sucess";
	// ����ʧ��
	public static final String SCOKET_CONNECT_FAILE = "com.chunyi.vod.mmediaPlayerService.connect.faile";
	// ���Ӵ���
	public static final String SCOKET_CONNECT_WRONG = "com.chunyi.vod.mmediaPlayerService.connect.wrong";
	// ���ͺ�̨���µĲ����б�Ĺ㲥
	public static final String SCOKET_NEW_SELECT_SONG = "com.chunyi.vod.mmediaPlayerService.newSelectSong";
	public static final String SCOKET_NOT_FIND_SERVICE = "com.chunyi.vod.mmediaPlayerService.notFindService";
	public static final String SCOKET_PLAYSTATE = "com.chunyi.vod.mmediaPlayerService.playstate";
	public static final String SCOKET_PLAYPASUEERROR = "com.chunyi.vod.mmediaPlayerService.playpauseerror";
	public static final String SCOKET_CONNECT_TV = "com.chunyi.vod.mmediaPlayerService.connecttv";

}
