package cn.gov.bjys.onlinetrain.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dodo on 2017/11/22.
 */

public class UpdateFileUtils {





    public static final String UPDATE  = "update";
    /**
     * assets/update/4.3.13.zip ===>/data/user/0/cn.gov.bjys.onlinetrain/cache/4.3.13.zip
     * @param context
     * @param fileName  assets文件下的update下的Zip文件名
     * @return
     *
     */
    public static String getAssetsCacheFile(Context context, String fileName)   {
        File cacheFile = new File(context.getCacheDir(), fileName);
        ///data/user/0/cn.gov.bjys.onlinetrain/cache/ + filename 本app的缓存目录
        try {
            InputStream inputStream = context.getAssets().open(UPDATE + File.separator + fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacheFile.getAbsolutePath();
    }


}
