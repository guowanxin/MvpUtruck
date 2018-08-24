package baiji.android.core.update;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import com.tdh.common.utils.APPLog;

public class UpdateCenter {

	private static UpdateCenter INSTANCE = new UpdateCenter();

	private UpdateCenter() {

	}

	public static UpdateCenter getInstance() {
		return INSTANCE;
	}

	/**
	 * 是否可以使用ROM更新
	 *
	 * @param context
	 * @param packageName
	 * @return
	 */
	public boolean canRomUpdate(Context context, String packageName) {

		// 检查ROM是否支持
		String otaver = UpdateUtil.getSystemProp(UpdateConstants.OTA_VER_PROP);

		if (TextUtils.isEmpty(otaver)) {
			return false;
		}

		try {
			Integer.parseInt(otaver);
		} catch (NumberFormatException e) {
			return false;
		}

		// 检查是否是安装在system目录
		PackageManager m = context.getPackageManager();
		String s = context.getPackageName();
		try {
			PackageInfo p = m.getPackageInfo(s, 0);
			String dir = p.applicationInfo.sourceDir;

			return dir.startsWith("/system/app");
		} catch (NameNotFoundException e) {
			APPLog.error("UpdateCenter:isRomUpdate error", e);
		}

		return false;
	}

	public boolean isSysPrivApp(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packs = packageManager.getInstalledPackages(0);

		int size = packs.size();
		for (int i = 0; i < size; i++) {
			PackageInfo p = packs.get(i);

			if (p.packageName.equals(packageName)) {
				return p.applicationInfo.sourceDir.startsWith("/system/priv-app");
			}

		}

		return false;
	}

	/**
	 * 是否可以使用APP更新
	 *
	 * @param context
	 * @param packageName
	 * @return
	 */
	public boolean canAppUpdate(Context context, String packageName) {

		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packs = packageManager.getInstalledPackages(0);

		int size = packs.size();
		for (int i = 0; i < size; i++) {
			PackageInfo p = packs.get(i);

			if (p.packageName.equals(packageName)) {
				return !p.applicationInfo.sourceDir.startsWith("/system/app");
			}

		}

		return false;
	}

	public UpdateManager getUpdateManager(Context context, String packageName) throws UpdateException {

		if (canRomUpdate(context, packageName)) {
			return new RomUpdateManager();
		} else if (isSysPrivApp(context, packageName)) {
			return new SysPrivAppUpdateManager();
		} else if (canAppUpdate(context, packageName)) {
			return new AppUpdateManger();
		}

		throw new UpdateException("Could not get update manager for package:" + packageName);
	}

	/**
	 * ROM更新是否支持patch，主要是看libbspatch.so文件是否有安装
	 */
	private static boolean SUPPORTS_PATCH;

	static {

		// FIXME 1. 如果是system app 如果用System.loadLibrary,so文件必须在system/lib目录
		// 如果用System.load需将so文件拷贝至data目录
		// System.load("/sdcard/bjsoft/libs/libbspatch.so");
		/*
		 * 1. 如果使用者是系统app，则需在system/lib下有这个文件，或者在data目录有对应so文件 2.
		 * 如果使用者是普通app，则libs目录会自动产生
		 */
		// try {
		// System.loadLibrary("bspatch");
		// } catch (UnsatisfiedLinkError e) {
		// APPLog.error("Couldn't loadLibrary bspatch", e);
		//
		// try {
		// System.load("/data/data/libbspatch.so");
		// } catch (UnsatisfiedLinkError e1) {
		// APPLog.error("Couldn't load /data/data/libbspatch.so", e);
		// }
		// }

//		try {
//			System.loadLibrary("bspatch");
//			System.loadLibrary("Patcher");
//			SUPPORTS_PATCH = true;
//		} catch (UnsatisfiedLinkError e) {
//			APPLog.error("Couldn't loadLibrary bspatch", e);
//		}

	}

	public boolean isSupportPatchUpdate() {
		return SUPPORTS_PATCH;
	}

}
