package com.tdh.common.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;


/**
 * 存储卡检测类
 */
public class StorageCheck {
	public static boolean isSdcardExist() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? true : false;
	}

	public static int checkAppDirsAndMkdirs() {
		int status = getStorageStatus();
		if (status == Const.STORAGE_STATUS_NONE) {
			return Const.STORAGE_STATUS_NONE;
		} else if (status == Const.LOW_STORAGE_THRESHOLD) {
			return Const.STORAGE_STATUS_LOW;
		} else {
			String[] appDirs = new String[] { Const.LOG_DIR };

			File file = null;
			for (String dir : appDirs) {
				file = new File(dir);
				if (!file.exists() || !file.isDirectory()) {
					file.mkdirs();
				}
			}
			return Const.STORAGE_STATUS_OK;
		}
	}

	public static int getStorageStatus() {
		long remaining = getAvailableStorage();
		if (remaining == Const.NO_STORAGE_ERROR) {
			return Const.STORAGE_STATUS_NONE;
		}
		return remaining < Const.LOW_STORAGE_THRESHOLD ? Const.STORAGE_STATUS_LOW : Const.STORAGE_STATUS_OK;
	}

	public static long getAvailableStorage() {
		if (!hasStorage(true)) {
			return Const.NO_STORAGE_ERROR;
		} else {
			StatFs stat = new StatFs(Const.EXTERNAL_STORAGE_DIR);
			return (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
		}
	}

	/**
	 * 返回SD卡是否由读写权限
	 *
	 * @param requireWriteAccess
	 *            是否需要写权限
	 * @return
	 */
	public static boolean hasStorage(boolean requireWriteAccess) {
		if (isSdcardExist()) {
			if (requireWriteAccess) {
				return checkFSWritable();
			} else {
				return true;
			}
		}
		return false;
	}

	private static boolean checkFSWritable() {
		String dirName = Const.LOG_DIR;
		File dir = new File(dirName);
		if (!dir.isDirectory() && !dir.mkdirs())
			return false;
		return dir.canWrite();
	}
}
