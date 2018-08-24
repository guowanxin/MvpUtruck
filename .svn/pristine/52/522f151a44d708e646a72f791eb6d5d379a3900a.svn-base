package baiji.android.core.update;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * 升级包信息
 *
 * @author bravo
 *
 */
public class UpdateInfo implements Serializable {
	private static final long serialVersionUID = 1506098160643418609L;

	/**
	 * 普通包url
	 */
	private String url;

	/**
	 * 普通包文件大小(bytes)
	 */
	private long fileSize;

	/**
	 * 普通包文件md5
	 */
	private String md5;

	/**
	 * 新版本名称
	 */
	private String version;

	/**
	 * 补丁包url
	 */
	private String patchUrl;

	/**
	 * 补丁包大小(bytes)
	 */
	private long patchFileSize;

	/**
	 * 补丁包md5
	 */
	private String patchMd5;

	/**
	 * 是否是重新安装，某些升级包可能会删除本地原有数据进行重新安装
	 */
	private boolean reinstall;

	/**
	 * 保存本地路径
	 */
	private File fileDir;

	/**
	 * 是否使用patch
	 */
	private boolean usePatch;

	private String fileName;

	private String patchName;

	/**
	 * 只针对ROM更新时使用，列出需合并的apk的名字和其md5值
	 */
	private Map<String, String> patchedApks;

	/**
	 * 只针对APP更新时使用，设置待升级的apk的package name
	 */
	private String packageName;

	/**
	 * is force update
	 */
	private boolean isForceUpdate;

	public boolean isForceUpdate() {
		return isForceUpdate;
	}

	public void setForceUpdate(boolean isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPatchUrl() {
		return patchUrl;
	}

	public void setPatchUrl(String patchUrl) {
		this.patchUrl = patchUrl;
	}

	public long getPatchFileSize() {
		return patchFileSize;
	}

	public void setPatchFileSize(long patchFileSize) {
		this.patchFileSize = patchFileSize;
	}

	public String getPatchMd5() {
		return patchMd5;
	}

	public void setPatchMd5(String patchMd5) {
		this.patchMd5 = patchMd5;
	}

	public boolean isReinstall() {
		return reinstall;
	}

	public void setReinstall(boolean reinstall) {
		this.reinstall = reinstall;
	}

	public boolean isUsePatch() {
		return usePatch;
	}

	public void setUsePatch(boolean usePatch) {
		this.usePatch = usePatch;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPatchName() {
		return patchName;
	}

	public void setPatchName(String patchName) {
		this.patchName = patchName;
	}

	public File getFileDir() {
		return fileDir;
	}

	public void setFileDir(File fileDir) {
		this.fileDir = fileDir;
	}

	/**
	 * 是否有差异包信息
	 *
	 * @return
	 */
	public boolean hasPatchInfo() {
		return patchFileSize > 0 && (null != patchMd5) && (null != patchUrl);
	}

	public Map<String, String> getPatchedApks() {
		return patchedApks;
	}

	public void setPatchedApks(Map<String, String> patchedApks) {
		this.patchedApks = patchedApks;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String toString() {
		return "UpdateInfo [url=" + url + ", fileSize=" + fileSize + ", md5=" + md5 + ", version=" + version
				+ ", patchUrl=" + patchUrl + ", patchFileSize=" + patchFileSize + ", patchMd5=" + patchMd5
				+ ", reinstall=" + reinstall + ", fileDir=" + fileDir + ", usePatch=" + usePatch + ", fileName="
				+ fileName + ", patchName=" + patchName + "]";
	}

}
