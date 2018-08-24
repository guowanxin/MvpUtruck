package com.tdh.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;


/**
 * 获取设备各种信息的小程序
 *
 * <pre>
 * Description
 * Copyright:	Copyright (c)2012
 * Company:		百及科技
 * Author:		Liumc
 * Version:		1.0
 * Create at:	2013-1-14 下午3:31:02
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 *
 * </pre>
 */
public class SysUtil {

	/**
	 * 获取屏幕宽度
	 * @return
	 */
	public static int getWidth(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);

		return metric.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * @return
	 */
	public static int getHeight(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);

		return metric.heightPixels;
	}

	/**
	 * 动态获取图片的宽高
	 * @param oldSize 图片原本大小
	 * @param width   手机宽度大小
	 * @return
	 */
	public static int getImageSize(int oldSize, int width) {

		return oldSize * width / 720;
	}

	public static boolean isExternalStorageMounted() {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
			return true;
		} else
			return false;
	}

	private static final String[] EXT_NAME = new String[] { "sdcard2", "extSdCard", "extsd", "sdcard-ext",
			"external_sd", "sdcard", "emmc" };

	public static File getRealExternalStorage() {

		if (isExternalStorageMounted()) {
			return getExternalStorage();
		}

		String path = "/mnt";

		for (String name : EXT_NAME) {
			path = "/mnt/" + name;

			try {
				File file = new File(path);
				if (file.exists() && file.canWrite()) {
					return file;
				}
			} catch (Exception e) {
				APPLog.warn(e.getMessage(), e);
			}
		}

		return null;
	}

	public static File getExternalStorage() {
		return Environment.getExternalStorageDirectory();
	}

	public static File getSdcard2Storage() {
		String path = "/mnt/sdcard2";

		try {
			File file = new File(path);
			if (file.exists()) {
				return file;
			}
		} catch (Exception e) {
			APPLog.error(e.getMessage(), e);
		}

		return null;
	}

	public static File getEmmcStorage() {
		String path = "/mnt/emmc";
		try {
			File file = new File(path);
			if (file.exists())
				return file;
		} catch (Exception e) {
			APPLog.error(e.getMessage(), e);
		}

		return null;
	}

	public static File getInternalStorage() {

		try {
			File file = SharedAppContext.getContentContext().getFilesDir();

			if (file.exists()) {
				return file;
			}
		} catch (Exception e) {
			APPLog.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 0 : not connected <br/>
	 * 1 : wifi connected <br/>
	 * 2 : cell network connected <br/>
	 * 3 : both wifi and cell network connected <br/>
	 * 4 : network connected(有线网络) <br/>
	 *
	 * @return
	 */
	@SuppressWarnings("finally")
	public static int getNetworkState() {
		boolean wifiNetwork = false;
		boolean cellNetwork = false;
		boolean hasNetwork = false;
		try {
			// 获得网络连接服务
			ConnectivityManager connManager = (ConnectivityManager) SharedAppContext.getContentContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			// 获取WIFI网络连接状态
			State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

			// 判断是否正在使用WIFI网络
			if (State.CONNECTED == state) {
				wifiNetwork = true;
			}

			// 获取GPRS网络连接状态
			state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

			// 判断是否正在使用手机网络
			if (State.CONNECTED == state) {
				cellNetwork = true;
			}

			// 获取活动网络状态
			state = connManager.getActiveNetworkInfo().getState();

			// 判断是否有网络连接
			if (State.CONNECTED == state) {
				hasNetwork = true;
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			APPLog.error("..net is error....");
		} finally {
			int val = 0;

			if (wifiNetwork) {
				val += 1;
			} else if (cellNetwork) {
				val += 2;
			} else if (wifiNetwork && cellNetwork) {
				val += 3;
			} else if (hasNetwork) {
				val += 4;
			}

			return val;
		}
	}

	public static String getDefaultIMEI() {
		String imei = ((TelephonyManager) SharedAppContext.getContentContext().getSystemService(
				Context.TELEPHONY_SERVICE)).getDeviceId();

		if (null != imei) {
			// imei = imei.toUpperCase();
		}

		return imei;
	}

	public static String getDefaultICCID() {
		return ((TelephonyManager) SharedAppContext.getContentContext().getSystemService(Context.TELEPHONY_SERVICE))
				.getSimSerialNumber();
	}

	public static String getLine1Number() {
		String number = ((TelephonyManager) SharedAppContext.getContentContext().getSystemService(
				Context.TELEPHONY_SERVICE)).getLine1Number();

		if (null != number) {
			number = number.replace("+86", "");
		}

		return number;
	}

	public static boolean isProcessStarted(Context context, String processName) {

		ActivityManager activityMan = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> process = activityMan.getRunningAppProcesses();
		for (Iterator iterator = process.iterator(); iterator.hasNext();) {
			RunningAppProcessInfo runningAppProcessInfo = (RunningAppProcessInfo) iterator.next();
			String strProcessName = runningAppProcessInfo.processName;

			APPLog.info("current running process:" + strProcessName);

			if (processName.equals(strProcessName)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 返回一个可以写日志的磁盘路径
	 * <p>
	 * <li>日志位置优先级：SD卡、/data目录
	 * <li>在以上有效的位置里创建bjlog文件夹
	 * <li>返回日志路径
	 *
	 * @return
	 */
	public static String getValidLogPath() {

		File parent = null;

		try {
			parent = getValidLogDirectory();

			if (null == parent) {
				return null;
			}

			File logDir = new File(parent, "bjlog");

			if (!logDir.exists() || !logDir.isDirectory()) {
				logDir.mkdir();
			}

			return logDir.getAbsolutePath();

		} catch (Exception e) {
			if (APPLog.isErrorable()) {
				APPLog.error(e.getMessage(), e);
			}
		}

		return null;
	}

	private static File getValidLogDirectory() {
		File file = null;

		if (isExternalStorageMounted()) {

			file = getExternalStorage();

			if (null != file) {
				return file;
			}

			file = getSdcard2Storage();
			if (null != file) {
				return file;
			}

			file = getEmmcStorage();
			if (null != file) {
				return file;
			}
		}

		if (null == file) {
			file = getInternalStorage();
		}

		return file;
	}

	/**
	 * 获取数据网络是否开启
	 *
	 * @param context
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean getMobileDataEnabled(Context context) throws ClassNotFoundException, NoSuchFieldException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final Class clz = Class.forName(connMgr.getClass().getName());
		final Field mServiceField = clz.getDeclaredField("mService");
		mServiceField.setAccessible(true);

		final Object iConnectivityManager = mServiceField.get(connMgr);
		final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());

		final Method method = iConnectivityManagerClass.getDeclaredMethod("getMobileDataEnabled");
		method.setAccessible(true);

		Object result = method.invoke(iConnectivityManager);

		return (Boolean) result;

	}

	/**
	 * 更改数据网络的开启状态
	 *
	 * @param context
	 * @param enable
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setMobileDataEnabled(Context context, boolean enable) throws ClassNotFoundException,
			NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final Class conmanClass = Class.forName(connMgr.getClass().getName());
		final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
		iConnectivityManagerField.setAccessible(true);
		final Object iConnectivityManager = iConnectivityManagerField.get(connMgr);
		final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
		final Method method = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		method.setAccessible(true);

		method.invoke(iConnectivityManager, enable);
	}
}
