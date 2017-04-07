package vod.chunyi.com.phonefans.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by knight on 2017/4/7.
 */

public class FileUtils {

    /**
     * 复制文件
     * @param srcFile
     * @param destFile
     */
    public static void copyFile(File srcFile, File destFile) {

        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel channelIn = null;
        FileChannel channelOut = null;

        try {
            fin = new FileInputStream(srcFile);
            fout = new FileOutputStream(destFile);

            channelIn = fin.getChannel();
            channelOut = fout.getChannel();

            channelIn.transferTo(0,channelIn.size(),channelOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                fin.close();
                fout.close();
                channelIn.close();
                channelOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
