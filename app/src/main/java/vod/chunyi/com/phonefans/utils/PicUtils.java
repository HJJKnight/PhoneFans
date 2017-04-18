package vod.chunyi.com.phonefans.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by knight on 2017/4/18.
 */

public class PicUtils {

    /**
     * 图片转化成base64字符串
     *
     * @param imgPath
     * @return
     */
    public static String ImageToBase64Str(String imgPath) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //返回Base64编码过的字节数组字符串
        return Base64.encodeToString(data, Base64.DEFAULT);
    }


    /**
     * base64字符串转化成图片
     *
     * @param imgStr
     * @param destPath
     * @return
     */
    public static boolean Base64ToImage(String imgStr, String destPath) {
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;

        try {
            //Base64解码
            byte[] b = Base64.decode(imgStr, Base64.DEFAULT);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgName = "temp.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(destPath + File.separator + imgName);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
