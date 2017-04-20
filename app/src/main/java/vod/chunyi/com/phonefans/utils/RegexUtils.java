package vod.chunyi.com.phonefans.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 * Created by knight on 2017/4/20.
 */

public class RegexUtils {

    /**
     * 手机格式验证
     * <p>
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通） //177电信4G
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     *
     * @param mobiles
     * @return
     */
    public static boolean isLegalPhoneNo(String mobiles) {

        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}";

        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 判断输入密码是否合格
     * 6-12位 大小写，无划线
     *
     * @param pwd
     * @return
     */
    public static boolean isLegalPsw(String pwd) {

        String pswRegex = "^[A-Za-z0-9]{6,12}$";
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
        return pwd.matches(pswRegex);
    }
}
