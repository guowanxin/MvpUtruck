package com.tdh.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;


/**
 * 日志辅助类
 *
 * @author wangsheng
 * @date 2011-11-24 上午11:51:07
 */

public class LogHelper {

	private static final boolean LOG_D_SWITCH = true; // 调试日志开关
	private static final boolean IS_PRODUCTION_ENV = true; // 标识是否是产品环境

	/**
	 * 调试日志封装接口
	 *
	 * @param tag
	 * @param logMsg
	 */
	public static void debugLog(String tag, String logMsg) {
		if (LOG_D_SWITCH) {
			writeError2File(tag, logMsg, true);
		}
	}

	/**
	 * 错误日志封装接口
	 *
	 * @param tag
	 * @param logMsg
	 */
	public static void errorLog(String tag, String logMsg) {
		if (IS_PRODUCTION_ENV) {
			writeError2File(tag, logMsg, false);
			writeError2File(tag, logMsg, true);
		}
	}

	/**
	 * 将错误日志写入文件，供产品模式查找BUG
	 *
	 * @param tag
	 * @param logMsg
	 */
	private static void writeError2File(String tag, String logMsg, boolean bool) {
		if (!StorageCheck.isSdcardExist())
			return;
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		StringBuffer sb = new StringBuffer();
		sb.append(date);
		sb.append("\t");
		sb.append(tag);
		sb.append("\t");
		sb.append(logMsg);
		sb.append("\n");

		String logName = "error.txt";
		if (bool) {
			logName = "errorInfo.txt";
		}
		if (Const.STORAGE_STATUS_OK == StorageCheck.checkAppDirsAndMkdirs()) {
			File logFile = new File(Const.LOG_DIR, logName);
			if (logFile.exists() && (float) logFile.length() / 1024 > 1024) {
				logFile.delete();
			}
			try {
				OutputStream os = new FileOutputStream(logFile, true);
				os.write(sb.toString().getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
