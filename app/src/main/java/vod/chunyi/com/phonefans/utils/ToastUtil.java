package vod.chunyi.com.phonefans.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Android Toast封装
 * Created by knight on 2017/4/7.
 */

public class ToastUtil {

    // 短时间显示Toast信息
    public static void showShort(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    // 长时间显示Toast信息
    public static void showLong(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }
}
