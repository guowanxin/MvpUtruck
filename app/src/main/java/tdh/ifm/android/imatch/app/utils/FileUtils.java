package tdh.ifm.android.imatch.app.utils;

import android.os.Environment;

import com.tdh.common.utils.APPLog;

import java.io.File;

/**
 * Author：gwx
 * Create at：2017/4/21 17:59
 */
public class FileUtils {

    public String SDPATH;

    public String getSDPATH() {
        return SDPATH;
    }

    public FileUtils() {
        SDPATH = Environment.getExternalStorageDirectory()+"/utruck/";
        File file = new File(SDPATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}
