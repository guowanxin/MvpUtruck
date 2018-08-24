package baiji.android.core.update;

public class PatchUtil {
	public native static int applyPatch(String oldApkPath, String newApkPath, String patchPath);
}
