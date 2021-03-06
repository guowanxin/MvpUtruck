package tdh.ifm.android.imatch.app.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdh.common.utils.CommonUtil;
import com.tdh.common.utils.FileUtil;
import com.tdh.common.utils.GetPathFromUri4kitkat;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.utils.FileUtils;

/**
 * Author：gwx
 * Create at：2017/4/21 11:08
 */
public class ChooseImageActivity extends Activity {

    private Context context;

    @BindView(R.id.tv_xiangce)
    TextView tvXiangce;
    @BindView(R.id.tv_xiangji)
    TextView tvXiangji;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.dialog_layout)
    LinearLayout dialogLayout;

    //照相
    private String path;
    public static final int PHOTOHRAPH = 2;// 拍照
    public static final int PHOTOPICK = 1;// 相册

    private FileUtils fileUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_chooseimage);
        ButterKnife.bind(this);

        fileUtils = new FileUtils();
    }

    @OnClick(R.id.tv_xiangce)
    public void onTvXiangceClicked() {
        openXiangCe();
    }

    @OnClick(R.id.tv_xiangji)
    public void onTvXiangjiClicked() {
        if (Build.VERSION.SDK_INT >= 23){
            try {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CAMERA)) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA},
                                13);
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA},
                                13);
                    }
                }else{
                    openXiangJi();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            openXiangJi();
        }
    }

    @OnClick(R.id.tv_cancel)
    public void onTvCancelClicked() {
        finish();
    }

    /**
     * 打开相册
     */
    private void openXiangCe() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, PHOTOPICK);
    }

    /**
     * 打开相机
     */
    private void openXiangJi() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(fileUtils.getSDPATH(),
                    String.valueOf(System.currentTimeMillis())+ ".jpg");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            path = file.getAbsolutePath();
            FileUtil.startActionCapture(this,file,PHOTOHRAPH);
        } else {
            CommonUtil.getToast(context, "没装SD卡");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 13: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    openXiangJi();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            finish();
            return;
        }
        if (requestCode == PHOTOHRAPH) {
            if (path != null && path.endsWith("jpg")) {
                Intent intent = getIntent();
                intent.putExtra("imagePath", path);
                setResult(10, intent);
                finish();
            }else {
                finish();
            }
        }
        if (data == null) {
            return;
        }
        if (requestCode == PHOTOPICK) {
            Uri uri = data.getData();
            String imagePath = GetPathFromUri4kitkat.getPath(this, uri);
            if (imagePath != null) {
                Intent intent = getIntent();
                intent.putExtra("imagePath", imagePath);
                setResult(10, intent);
                finish();
            }else {
                finish();
            }
        }

    }

}
