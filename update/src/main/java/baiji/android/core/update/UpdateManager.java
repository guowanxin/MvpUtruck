package baiji.android.core.update;

import java.io.File;
import java.util.Map;

import android.content.Context;

public interface UpdateManager {

	/**
	 * 是否需要检查更新(日常)
	 *
	 * @param context
	 * @param packageName
	 * @return
	 */
	public boolean isRoutineCheckRequired(Context context, String packageName);

	/**
	 * 获取文件下载的目录
	 *
	 * @return
	 */
	public File getFileStoreDir();

	/**
	 * 请求服务器检查是否有更新
	 *
	 * @param version   当前版本号
	 *
	 * @return
	 * @throws UpdateException
	 */
	public UpdateInfo checkUpdate(String url, Map<String, String> parameters, Context context, String packageName)
			throws UpdateException;

	void installFile(Context context, File file, UpdateInfo updateInfo) throws UpdateException;

	void installPatch(Context context, File file, UpdateInfo updateInfo) throws UpdateException;

	public abstract void installPatchSilent(Context context, File patchFile, UpdateInfo updateInfo) throws UpdateException;

	public abstract void installFileSilent(Context context, File file, UpdateInfo updateInfo);

}
