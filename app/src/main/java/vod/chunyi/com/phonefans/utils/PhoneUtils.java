package vod.chunyi.com.phonefans.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Created by knight on 2017/4/11.
 */

public class PhoneUtils {

    /**
     * 判断是否连接到服务器
     *
     * @return
     */
    public static boolean isConnectServer(String address) {
        boolean isConn = false;
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000 * 8);
            if (conn.getResponseCode() == 200) {
                isConn = true;
            }
        } catch (Exception e) {
        } finally {
            conn.disconnect();
        }
        return isConn;
    }

    // 返回值 -1：没有网络 1：WIFI网络 2：wap网络 3：net网络
    public static int getNetType(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo == null) {
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                return Constants.NETWORN_MOBILE;
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                return Constants.NETWORN_WIFI;
            }

        }
        return Constants.NETWORN_NONE;
    }

    /**
     * 获取设备机械码 并对其做加密处理
     *
     * @return 机械码
     */
    public static String getMachineCode(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = tm.getDeviceId() + "";
        String tmSerial = tm.getSimSerialNumber() + "";
        String androidId = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

}
