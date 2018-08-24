package baiji.android.core.update;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import tdh.thunder.common.utils.CommonUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.tdh.common.utils.APPLog;

public class UpdateUtil {

	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(UpdateConstants.PREFS_UPDATE, Context.MODE_PRIVATE);
	}

	public static void setPrefValue(Context context, String key, boolean value) {
		SharedPreferences pref = getSharedPreferences(context);
		pref.edit().putBoolean(key, value).commit();
	}

	public static void setPrefValue(Context context, String key, String value) {
		SharedPreferences pref = getSharedPreferences(context);
		pref.edit().putString(key, value).commit();
	}

	public static void setPrefValue(Context context, String key, long value) {
		SharedPreferences pref = getSharedPreferences(context);
		pref.edit().putLong(key, value).commit();
	}

	public static String getPrefValue(Context context, String key, String defaultValue) {
		return getSharedPreferences(context).getString(key, defaultValue);
	}

	public static long getPrefValueAsLong(Context context, String key, long defaultValue) {
		return getSharedPreferences(context).getLong(key, defaultValue);
	}

//	static {
//		// 调用.so文件,引入打包库
//		System.loadLibrary("Patcher");
//	}

	/**
	 * 调用/system/bin/getprop获取系统属性
	 */
	public static String getSystemProp(String name) {
		ProcessBuilder pb = new ProcessBuilder("/system/bin/getprop", name);
		pb.redirectErrorStream(true);

		Process p = null;
		InputStream is = null;
		try {
			p = pb.start();
			is = p.getInputStream();
			Scanner scan = new Scanner(is);
			scan.useDelimiter("\n");
			String prop = scan.next();
			if (prop.length() == 0)
				return null;
			return prop;
		} catch (NoSuchElementException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	private static final int BUFFER_SIZE = 1024 * 8;

	// /**
	// * 返回压缩包中的文件InputStream
	// *
	// * @param zipFileString
	// * 压缩文件的名字
	// * @param fileString
	// * 解压文件的名字
	// * @return InputStream
	// * @throws Exception
	// */
	// public static InputStream UpZip(String zipFileString, String
	// fileString) throws Exception {
	// ZipFile zipFile = new ZipFile(zipFileString);
	// ZipEntry zipEntry = zipFile.getEntry(fileString);
	//
	// return zipFile.getInputStream(zipEntry);
	//
	// }

	/**
	 * 解压一个压缩文档 到指定位置
	 *
	 * @param zipFile
	 *            压缩包的名字
	 * @param outputFolder
	 *            指定的路径
	 * @throws Exception
	 */
	public static void unzip(String zipFile, String outputFolder) throws Exception {

		byte[] buffer = new byte[BUFFER_SIZE];

		// create output directory is not exists
		File folder = new File(outputFolder);
		if (!folder.exists()) {
			folder.mkdir();
		}

		// get the zip file content

		BufferedInputStream is = new BufferedInputStream(new FileInputStream(zipFile));

		ZipInputStream zis = new ZipInputStream(is);
		// get the zipped file list entry
		ZipEntry ze = zis.getNextEntry();

		while (ze != null) {

			String fileName = ze.getName();

			Log.d("XXX", "Extracting:" + fileName);

			File newFile = new File(outputFolder + File.separator + fileName);

			if (ze.isDirectory()) {
				newFile.mkdirs();
			} else {

				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();

				BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(newFile));

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
			}

			ze = zis.getNextEntry();
		}

		zis.closeEntry();
		zis.close();

	}

	/**
	 * 压缩文件,文件夹
	 *
	 * @param src
	 *            要压缩的文件/文件夹名字
	 * @param dest
	 *            指定压缩的目的和名字
	 * @param includeSelf
	 *            是否包含本身(只针对目录)
	 * @throws Exception
	 */
	public static void zip(String src, String dest, boolean includeSelf) throws Exception {

		// 创建Zip包
		ZipOutputStream outZip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
		outZip.setLevel(0);

		// 打开要输出的文件
		File file = new File(src);

		if (file.isDirectory() && !includeSelf) {

			File[] files = file.listFiles();

			for (File f : files) {
				doZip(f.getParent() + File.separator, f.getName(), outZip);
			}

		} else {
			doZip(file.getParent() + File.separator, file.getName(), outZip);
		}

		// 完成,关闭
		outZip.finish();
		outZip.close();

	}

	/**
	 * 压缩文件
	 *
	 * @param parent
	 * @param fileName
	 * @param zipOutputSteam
	 * @throws Exception
	 */
	private static void doZip(String parent, String fileName, ZipOutputStream zipOutputSteam) throws Exception {

		if (zipOutputSteam == null)
			return;

		File file = new File(parent + fileName);

		// 判断是不是文件
		if (file.isFile()) {

			Log.d("XXX", "Compressing file:" + fileName);

			ZipEntry zipEntry = new ZipEntry(fileName);
			zipEntry.setTime(1);
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			zipOutputSteam.putNextEntry(zipEntry);

			int len;
			byte[] buffer = new byte[BUFFER_SIZE];

			while ((len = inputStream.read(buffer)) != -1) {
				zipOutputSteam.write(buffer, 0, len);
			}

			zipOutputSteam.closeEntry();
		} else {

			// 文件夹的方式,获取文件夹下的子文件
			String fileList[] = file.list();

			// 如果没有子文件, 则添加进去即可
			if (fileList.length <= 0) {
				ZipEntry zipEntry = new ZipEntry(fileName + File.separator);
				zipEntry.setTime(1);
				zipOutputSteam.putNextEntry(zipEntry);
				zipOutputSteam.closeEntry();
			}

			// 如果有子文件, 遍历子文件
			for (int i = 0; i < fileList.length; i++) {
				doZip(parent, fileName + File.separator + fileList[i], zipOutputSteam);
			}// end of for

		}// end of if

	}// end of func

	public static void delete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles()) {
				delete(c);
			}
		}
		if (!f.delete()) {
			throw new IOException("Failed to delete file: " + f);
		}
	}

	public static String getFileMd5(String file) throws IOException {
		// 缓冲区大小（这个可以抽出一个参数）
		int bufferSize = 8 * 1024;
		BufferedInputStream bis = null;
		DigestInputStream digestInputStream = null;
		try {
			// 拿到一个MD5转换器（同样，这里可以换成SHA1）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 使用DigestInputStream
			bis = new BufferedInputStream(new FileInputStream(file));
			digestInputStream = new DigestInputStream(bis, messageDigest);
			// read的过程中进行MD5处理，直到读完文件
			byte[] buffer = new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0)
				;
			// 获取最终的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();
			// 拿到结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();
			// 同样，把字节数组转换成字符串
			return CommonUtils.bytes2Hex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		} finally {
			try {
				digestInputStream.close();
			} catch (Exception e) {
			}
			try {
				bis.close();
			} catch (Exception e) {
			}
		}
	}

	/*
	 * 获取/system/app下的apk信息
	 */
	public static PackageInfo findSystemApk(Context context, String apkName) {
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packs = packageManager.getInstalledPackages(0);

		String apkDir = "/system/app/" + apkName;

		for (PackageInfo pack : packs) {
			String dir = pack.applicationInfo.sourceDir;

			if (dir.equals(apkDir)) {
				return pack;
			}
		}

		return null;
	}

	/*
	 * 合并文件
	 */
	public static void mergeApk(Context context, File patch, String sourceDir, String destDir, String md5)
			throws UpdateException {

		APPLog.debug("Merging patch:" + patch.getAbsolutePath() + " to " + sourceDir + " dest: " + destDir);

		// 合并文件
		PatchUtil.applyPatch(sourceDir, destDir, patch.getAbsolutePath());

		// 计算MD5值
		try {
			String mergedFileMd5 = UpdateUtil.getFileMd5(destDir);

			APPLog.debug("mergedFileMd5:" + mergedFileMd5);

			// 如果md5不一致，则表示system下文件与正式版本不一致，不能使用差异更新
			if (!mergedFileMd5.equalsIgnoreCase(md5)) {
				new File(destDir).delete();

				APPLog.error("Merge " + patch + " failed, md5 not match. Released:" + md5 + ", merged:" + mergedFileMd5);
				throw new UpdateException("文件校验失败");
			}
		} catch (IOException e) {
			throw new UpdateException("计算文件校验码失败", e);
		}

	}
}
