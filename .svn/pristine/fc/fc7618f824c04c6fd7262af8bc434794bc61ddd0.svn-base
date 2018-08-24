package com.tdh.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * 所有Android应用必须调用此方法写入日志！ 每个应用的日志标记(TAG)由各应用程序负责设置，但只需设置一次即可。
 * 开发时可打开一个DOS窗口查看专属于这个TAG的日志，相应命令为：<br>
 *
 * > adb logcat -v time <TAG>:* *:S
 *
 * 一些设备默认情况下会关闭logcat的DEBUG输出，此时，你可以用下面命令把它重新打开：<br>
 *
 * > adb shell setprop log.tag.<TAG> DEBUG
 *
 * $Rev:$<br>
 * $LastChangedDate$<br>
 * $Author$<br>
 */
public final class APPLog {

	// 增加LVL_前缀，目的是使这些不常用的常量不影响其它常用的代码提示，如debug方法等。
	public static final String LVL_DEBUG = "DEBUG";
	public static final String LVL_WARN = "WARN";
	public static final String LVL_INFO = "INFO";
	public static final String LVL_ERROR = "ERROR";

	// TAG Prefix by Android
	private static final String TAG_PREFIX = "log.tag.";

	// Default TAG
	private static String TAG = "BJACommon";

	private static String FULL_TAG = null;

	private static Context appContext = null;

	// 标记是否已获得DEBUG_MODE的状态
	private static boolean GOT_DEBUG_MODE = false;

	// 保存DEBUG_MODE的状态
	private static boolean IS_DEBUG_MODE = false;

	public static String getTAG() {
		return TAG;
	}

	/**
	 * 根据规范，每个Project只允许一个日志TAG，该TAG由实现android.app.Application的程序在应用
	 * 初始化时设置，其它程序不允许再次更改TAG。
	 *
	 * @param tAG
	 */
	public synchronized static void setTAG(Context context, String tAG) {
		if ("BJACommon".equals(TAG)) {
			TAG = tAG;
			FULL_TAG = TAG_PREFIX + TAG;
			appContext = context;

			retrieveAppInDebugMode();

			if (IS_DEBUG_MODE && isWarnable()) {
				APPLog.warn("Warning!! This app is running in debug mode.");
			}

			if (isDebuggable()) {
				debug("Full log tag: " + FULL_TAG);
				debug("Log level: " + getLogLevel());
			}
		} else {
			if (isErrorable()) {
				Throwable t = new Throwable("setTAG() has already been invoked!");
				error(t.getMessage(), t);
			}
		}
	}

	/**
	 * 注：由于logLevel是保存在系统属性里的，所以修改logLevel需要系统级权限。
	 *
	 * @param logLevel
	 */
	public static void setLogLevel(String logLevel) {
		try {
			String cmd = "setprop " + FULL_TAG + " " + logLevel;
			Process p = Runtime.getRuntime().exec("su");
			DataOutputStream dos = new DataOutputStream(p.getOutputStream());
			dos.writeBytes(cmd);
			dos.flush();
		} catch (Throwable t) {
			APPLog.error(t.getMessage(), t);
		}
	}

	@SuppressWarnings("deprecation")
	public static String getLogLevel() {
		try {
			String cmd = "getprop " + FULL_TAG;
			Process p = Runtime.getRuntime().exec(cmd);

			DataInputStream dis = new DataInputStream(p.getInputStream());
			String logLevel = dis.readLine();
			dis.close();

			if (null == logLevel || "".equals(logLevel)) {
				return APPLog.LVL_INFO;
			} else {
				return logLevel;
			}
		} catch (Throwable t) {
			APPLog.error(t.getMessage(), t);
		}
		return null;
	}

	/**
	 * 如果程序是在DEBUG模式，则允许DEBUG日志输出。
	 *
	 * @return
	 */
	public static boolean isDebuggable() {
		return IS_DEBUG_MODE || Log.isLoggable(TAG, Log.DEBUG);
	}

	public static void debug(String tag, String log) {
		LogHelper.debugLog(tag, log);
		Log.d(tag, log);
	}

	public static void debug(String log) {
		LogHelper.debugLog(TAG, log);
		Log.d(TAG, log);
	}

	public static void debug(String log, Throwable t) {
		LogHelper.debugLog(TAG, log+"----"+t);
		Log.d(TAG, log, t);
	}

	public static void debug(String tag, String log, Throwable t) {
		LogHelper.debugLog(tag, log+"----"+t);
		Log.d(tag, log, t);
	}

	public static boolean isInfoable() {
		return Log.isLoggable(TAG, Log.INFO);
	}

	public static void info(String log) {
		Log.i(TAG, log);
		LogHelper.debugLog(TAG, log);
	}

	public static void info(String tag, String log) {
		Log.i(tag, log);
		LogHelper.debugLog(tag, log);
	}

	public static void info(String log, Throwable t) {
		Log.i(TAG, log, t);
		LogHelper.debugLog(TAG, log+"---"+t);
	}

	public static void info(String tag, String log, Throwable t) {
		Log.i(tag, log, t);
		LogHelper.debugLog(tag, log+"---"+t);
	}

	public static boolean isWarnable() {
		return Log.isLoggable(TAG, Log.WARN);
	}

	public static void warn(String log) {
		Log.w(TAG, log);
	}

	public static void warn(String tag, String log) {
		Log.w(tag, log);
	}

	public static void warn(String log, Throwable t) {
		Log.w(TAG, log, t);
	}

	public static void warn(String tag, String log, Throwable t) {
		Log.w(tag, log, t);
	}

	public static boolean isErrorable() {
		return Log.isLoggable(TAG, Log.ERROR);
	}

	public static void error(String log) {
		LogHelper.errorLog(TAG, log);
		Log.e(TAG, log);
	}

	public static void error(String tag, String log) {
		LogHelper.errorLog(tag, log);
		Log.e(tag, log);
	}

	public static void error(String log, Throwable t) {
		LogHelper.errorLog(TAG, log+"----"+t);
		Log.e(TAG, log, t);
	}

	public static void error(String tag, String log, Throwable t) {
		LogHelper.debugLog(tag, log+"----"+t);
		Log.e(tag, log, t);
	}

	/**
	 * 返回应用是否在Debug模式，注意这个Debug模式与日志的Debug级别是两个概念。
	 *
	 * @return
	 */
	public static boolean retrieveAppInDebugMode() {
		if (GOT_DEBUG_MODE) {
			return IS_DEBUG_MODE; // 已经获取此状态
		}
		if (null == appContext && isErrorable()) {
			String errMessage = "No context was set in APPLog utility. Please invoke APPLog.setTAG(context, TAG) first.";
			APPLog.error(errMessage, new Throwable(errMessage));
		} else {
			IS_DEBUG_MODE = (0 != (appContext.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE));
			GOT_DEBUG_MODE = true;
		}
		return GOT_DEBUG_MODE;
	}

}
