package tdh.ifm.android.imatch.app.ui.activity.driver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.base.BaseApplication;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.FileType;
import tdh.ifm.android.imatch.app.presenter.MemberAuthPresenterImpl;
import tdh.ifm.android.imatch.app.ui.activity.ChooseImageActivity;
import tdh.ifm.android.imatch.app.ui.activity.LoginActvity;
import tdh.ifm.android.imatch.app.ui.activity.MainActivity;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.ImageLoaderUtils;
import tdh.ifm.android.imatch.app.utils.NetContant;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.MemberAuthView;

/**
 * Author：gwx
 * Create at：2017/5/3 20:20
 */
public class MemberLevelEditActivity extends BaseActivity implements MemberAuthView {

    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.iv_licence_1)
    ImageView ivLicence1;
    @BindView(R.id.iv_licence_2)
    ImageView ivLicence2;
    @BindView(R.id.iv_licence_3)
    ImageView ivLicence3;
    @BindView(R.id.iv_licence_4)
    ImageView ivLicence4;
    @BindView(R.id.iv_licence_5)
    ImageView ivLicence5;
    @BindView(R.id.iv_licence_6)
    ImageView ivLicence6;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindString(R.string.title_member_level)
    String title;
    /*身份证正面*/
    @BindBitmap(R.mipmap.icon_card_correct)
    Bitmap bmpCardCorrect;
    /*身份证反面*/
    @BindBitmap(R.mipmap.icon_card_opposite)
    Bitmap bmpCardOpposite;
    /*驾驶证*/
    @BindBitmap(R.mipmap.icon_drive_licence)
    Bitmap bmpDriveLicence;
    /*行驶证*/
    @BindBitmap(R.mipmap.icon_vehicle_licence)
    Bitmap bmpVehicleLicence;
    /*营运证 非必填*/
    @BindBitmap(R.mipmap.icon_operate_licence)
    Bitmap bmpOperateLicence;
    /*营业执照*/
    @BindBitmap(R.mipmap.icon_business_license)
    Bitmap bmpBusinessLicense;
    /*税务登记证*/
    @BindBitmap(R.mipmap.icon_tax_enrol_certificate)
    Bitmap bmpTaxEnrolCertificate;
    /*开户许可证*/
    @BindBitmap(R.mipmap.icon_openaccount_license)
    Bitmap bmpOpenAccountLicense;
    /*道路运输许可证*/
    @BindBitmap(R.mipmap.icon_accountant_licence)
    Bitmap bmpRoadTransportLicense;
    /*道路运输从业资格证 非必填*/
    @BindBitmap(R.mipmap.icon_seniority_licence)
    Bitmap bmpSeniorityLicense;

    private List<FileType> fileTypeList;

    /**
     * 区别是注册跳过来的还是会员中心跳过来的  默认0是注册跳过来的  1会员中心跳过来的
     **/
    private int flag;
    private String level;

    private String userType;

    private MemberAuthPresenterImpl memberAuthPresenter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_memberlevel_edit;
    }

    @Override
    public void initTitleView() {
        super.initTitleView();
        titleview.getTv_title().setText(title);
        titleview.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    Util.intentToActivity(context, MainActivity.class);

                    BaseApplication.getInstance().exit();
                }else {
                    finish();
                }
            }
        });
        titleview.getTv_right_title().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.intentToActivity(context, MainActivity.class);

                BaseApplication.getInstance().exit();
            }
        });
    }

    @Override
    public void initData() {
        fileTypeList = new ArrayList<>();
        for (int i = 0;i < 6;i++) {
            fileTypeList.add(null);
        }
        memberAuthPresenter = new MemberAuthPresenterImpl(context, this);

        setImageByUserType();

        setTextByFlag();
    }

    /**
     * 通过不同的用户状态决定显示哪些图片
     */
    private void setImageByUserType() {
        userType = SharedPreferencesUtil.getValue(Constants.USERTYPE, "");
        ivLicence1.setImageBitmap(bmpCardCorrect);
        ivLicence2.setImageBitmap(bmpCardOpposite);
        if (userType.equals(Constants.USERTYPE_S)) {
            ivLicence3.setVisibility(View.VISIBLE);
            ivLicence4.setVisibility(View.VISIBLE);
            ivLicence5.setVisibility(View.VISIBLE);
            ivLicence6.setVisibility(View.GONE);
            ivLicence3.setImageBitmap(bmpBusinessLicense);
            ivLicence4.setImageBitmap(bmpTaxEnrolCertificate);
            ivLicence5.setImageBitmap(bmpOpenAccountLicense);
        }else if (userType.equals(Constants.USERTYPE_C)) {
            ivLicence3.setVisibility(View.VISIBLE);
            ivLicence4.setVisibility(View.VISIBLE);
            ivLicence5.setVisibility(View.VISIBLE);
            ivLicence6.setVisibility(View.VISIBLE);
            ivLicence3.setImageBitmap(bmpDriveLicence);
            ivLicence4.setImageBitmap(bmpVehicleLicence);
            ivLicence5.setImageBitmap(bmpOperateLicence);
            ivLicence6.setImageBitmap(bmpSeniorityLicense);
        }else if (userType.equals(Constants.USERTYPE_AGENT)) {
            ivLicence3.setVisibility(View.GONE);
            ivLicence4.setVisibility(View.GONE);
            ivLicence5.setVisibility(View.GONE);
            ivLicence6.setVisibility(View.GONE);
        }else if (userType.equals(Constants.USERTYPE_L)) {
            ivLicence3.setVisibility(View.VISIBLE);
            ivLicence4.setVisibility(View.VISIBLE);
            ivLicence5.setVisibility(View.VISIBLE);
            ivLicence6.setVisibility(View.VISIBLE);
            ivLicence3.setImageBitmap(bmpBusinessLicense);
            ivLicence4.setImageBitmap(bmpTaxEnrolCertificate);
            ivLicence5.setImageBitmap(bmpOpenAccountLicense);
            ivLicence6.setImageBitmap(bmpRoadTransportLicense);
        }
    }

    /**
     * 通过从哪跳过来的决定如何显示界面等级以及提交按钮
     */
    private void setTextByFlag() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            flag = bundle.getInt("flag", 0);
            level = bundle.getString(Constants.LEVEL, "");
            if (flag == 0) {
                titleview.getTv_right_title().setVisibility(View.VISIBLE);
                titleview.getTv_right_title().setText("跳过");
                tvDes.setText("注册成功！您的会员等级：初级");
            } else {
                if (level.equals(Constants.LEVEL_PRIMARY)) {
                    tvDes.setText("您的会员等级：初级");
                } else if (level.equals(Constants.LEVEL_HIGHER)) {
                    tvDes.setText("您的会员等级：高级");
                }
                memberAuthPresenter.queryMemberAuthInfo();
            }
        } else {
            titleview.getTv_right_title().setVisibility(View.VISIBLE);
            titleview.getTv_right_title().setText("跳过");
            tvDes.setText("注册成功！您的会员等级：初级");
        }
    }

    @OnClick(R.id.iv_licence_1)
    public void onIvLicence1Clicked() {
        Util.intentToActivity(context, ChooseImageActivity.class, 1);
    }

    @OnClick(R.id.iv_licence_2)
    public void onIvLicence2Clicked() {
        Util.intentToActivity(context, ChooseImageActivity.class, 2);
    }

    @OnClick(R.id.iv_licence_3)
    public void onIvLicence3Clicked() {
        Util.intentToActivity(context, ChooseImageActivity.class, 3);
    }

    @OnClick(R.id.iv_licence_4)
    public void onIvLicence4Clicked() {
        Util.intentToActivity(context, ChooseImageActivity.class, 4);
    }

    @OnClick(R.id.iv_licence_5)
    public void onIvLicence5Clicked() {
        Util.intentToActivity(context, ChooseImageActivity.class, 5);
    }

    @OnClick(R.id.iv_licence_6)
    public void onIvLicence6Clicked() {
        Util.intentToActivity(context, ChooseImageActivity.class, 6);
    }

    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        if (TextUtils.isEmpty(level) || level.equals(Constants.LEVEL_PRIMARY)) {
            if (isNull()) {
                return;
            }
        }else {
            if (!isListNotNull()) {
                CommonUtil.getToast(context,"提交成功");
                return;
            }
        }
        memberAuthPresenter.completionMemberAuthInfo(fileTypeList);

    }

    @Override
    public void showProgress() {
        dialogShow();
    }

    @Override
    public void hideprogress() {
        dialogHide();
    }

    @Override
    public void showFailure(String str, Throwable t) {
        APPLog.error(str, t);
    }

    @Override
    public void showResState(int code) {
        Util.setResCode(context, code);
    }

    @Override
    public void onQueryMemberAuthInfoSuccess(BaseResponse<List<FileType>> baseResponse) {
        if (baseResponse.isSuccess()) {
            setNetImageByInterFace(baseResponse.getBody());
        }else {
            if (!TextUtils.isEmpty(baseResponse.getMessage())) {
                CommonUtil.getToast(context,baseResponse.getMessage());
            }
        }
    }

    @Override
    public void onCompletionMemberAuthInfoSuccess(BaseResponse baseResponse) {
        if (!TextUtils.isEmpty(baseResponse.getMessage())) {
            CommonUtil.getToast(context,baseResponse.getMessage());
        }
        if (baseResponse.isSuccess()) {
            if (flag == 0) {
                Util.intentToActivity(context, MainActivity.class);

                BaseApplication.getInstance().exit();
            }else {
                Intent intent = getIntent();
                intent.putExtra(Constants.LEVEL,Constants.LEVEL_HIGHER);
                setResult(10,intent);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            String path = data.getStringExtra("imagePath");
            APPLog.error(NetContant.TAG,"----path-----" + path);
            if (!TextUtils.isEmpty(path)) {
                dialog.dialogShow("压缩图片中…");
                comPressImage(path,requestCode);
            }
        }
    }

    /**
     * 压缩图片
     * @param path
     */
    private void comPressImage(final String path, final int flag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Util.getimage(path);
                if (bitmap == null) {
                    hideprogress();
                    return;
                }
                bitmap = Util.rotaingImage(bitmap, Util.
                        readPictureDegree(path));
                byte[] bytes = Util.compressImage(bitmap);
                FileType fileType = new FileType();
                fileType.setUrl(path);
                fileType.setBytes(bytes);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = fileType;
                msg.arg1 = flag;
                mhandler.sendMessage(msg);
                if (bitmap.isRecycled()) {
                    System.gc();
                }
            }
        }).start();
    }

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideprogress();
            FileType fileType = (FileType) msg.obj;

            switch (msg.arg1) {
                case 1:
                    ImageLoaderUtils.display(context,ivLicence1,fileType.getBytes());

                    if (userType.equals(Constants.USERTYPE_S)) {
                        fileType.setRefType("40");
                    }else if (userType.equals(Constants.USERTYPE_C)) {
                        fileType.setRefType("42");
                    }else if (userType.equals(Constants.USERTYPE_AGENT)) {
                        fileType.setRefType("42");
                    }else if (userType.equals(Constants.USERTYPE_L)) {
                        fileType.setRefType("40");
                    }
                    fileTypeList.set(0,fileType);
                    break;
                case 2:
                    ImageLoaderUtils.display(context,ivLicence2,fileType.getBytes());

                    if (userType.equals(Constants.USERTYPE_S)) {
                        fileType.setRefType("41");
                    }else if (userType.equals(Constants.USERTYPE_C)) {
                        fileType.setRefType("43");
                    }else if (userType.equals(Constants.USERTYPE_AGENT)) {
                        fileType.setRefType("43");
                    }else if (userType.equals(Constants.USERTYPE_L)) {
                        fileType.setRefType("41");
                    }
                    fileTypeList.set(1,fileType);
                    break;
                case 3:
                    ImageLoaderUtils.display(context,ivLicence3,fileType.getBytes());

                    if (userType.equals(Constants.USERTYPE_S)) {
                        fileType.setRefType("10");
                    }else if (userType.equals(Constants.USERTYPE_C)) {
                        fileType.setRefType("60");
                    }else if (userType.equals(Constants.USERTYPE_L)) {
                        fileType.setRefType("10");
                    }
                    fileTypeList.set(2,fileType);
                    break;
                case 4:
                    ImageLoaderUtils.display(context,ivLicence4,fileType.getBytes());

                    if (userType.equals(Constants.USERTYPE_S)) {
                        fileType.setRefType("20");
                    }else if (userType.equals(Constants.USERTYPE_C)) {
                        fileType.setRefType("70");
                    }else if (userType.equals(Constants.USERTYPE_L)) {
                        fileType.setRefType("20");
                    }
                    fileTypeList.set(3,fileType);
                    break;
                case 5:
                    ImageLoaderUtils.display(context,ivLicence5,fileType.getBytes());

                    if (userType.equals(Constants.USERTYPE_S)) {
                        fileType.setRefType("30");
                    }else if (userType.equals(Constants.USERTYPE_C)) {
                        fileType.setRefType("80");
                    }else if (userType.equals(Constants.USERTYPE_L)) {
                        fileType.setRefType("30");
                    }
                    fileTypeList.set(4,fileType);
                    break;
                case 6:
                    ImageLoaderUtils.display(context,ivLicence6,fileType.getBytes());

                    if (userType.equals(Constants.USERTYPE_L)) {
                        fileType.setRefType("50");
                    }else if (userType.equals(Constants.USERTYPE_C)) {
                        fileType.setRefType("90");
                    }
                    fileTypeList.set(5,fileType);
                    break;
            }
            APPLog.error(NetContant.TAG,fileTypeList.toString());

        }
    };

    /**
     * 获取会员认证信息接口获取到数据后赋值
     */
    private void setNetImageByInterFace(List<FileType> fileTypes) {
        if (fileTypes != null && fileTypes.size() > 0) {
            for (int i = 0;i < fileTypes.size();i++) {
                if (fileTypes.get(i) == null) {
                    continue;
                }
                if (userType.equals(Constants.USERTYPE_S)) {
                    if (fileTypes.get(i).getRefType().equals("40")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence1,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("41")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence2,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("10")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence3,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("20")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence4,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("30")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence5,fileTypes.get(i).getUrl());
                        }
                    }
                }else if (userType.equals(Constants.USERTYPE_C)) {
                    if (fileTypes.get(i).getRefType().equals("42")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence1,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("43")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence2,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("60")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence3,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("70")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence4,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("80")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence5,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("90")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence6,fileTypes.get(i).getUrl());
                        }
                    }
                }else if (userType.equals(Constants.USERTYPE_AGENT)) {
                    if (fileTypes.get(i).getRefType().equals("42")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence1,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("43")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence2,fileTypes.get(i).getUrl());
                        }
                    }
                }else if (userType.equals(Constants.USERTYPE_L)) {
                    if (fileTypes.get(i).getRefType().equals("40")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence1,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("41")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence2,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("10")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence3,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("20")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence4,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("30")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence5,fileTypes.get(i).getUrl());
                        }
                    }else if (fileTypes.get(i).getRefType().equals("50")) {
                        if (!TextUtils.isEmpty(fileTypes.get(i).getUrl())) {
                            ImageLoaderUtils.display(context,ivLicence6,fileTypes.get(i).getUrl());
                        }
                    }
                }
            }
        }
    }

    private boolean isNull() {
        if (userType.equals(Constants.USERTYPE_S)) {
            if (isListNull(5)) {
                CommonUtil.getToast(context,"请选择图片");
                return true;
            }
        }else if (userType.equals(Constants.USERTYPE_C)) {
            if (isListNull(4)) {
                CommonUtil.getToast(context,"请选择图片");
                return true;
            }
        }else if (userType.equals(Constants.USERTYPE_AGENT)) {
            if (isListNull(2)) {
                CommonUtil.getToast(context,"请选择图片");
                return true;
            }
        }else if (userType.equals(Constants.USERTYPE_L)) {
            if (isListNull(6)) {
                CommonUtil.getToast(context,"请选择图片");
                return true;
            }
        }
        return false;
    }

    /**
     * 判断列表中是否有空的
     * @param size
     * @return
     */
    private boolean isListNull(int size) {
        for (int i = 0;i < size;i++) {
            if (fileTypeList.get(i) == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断列表中是否有有值的
     * @return
     */
    private boolean isListNotNull() {
        for (int i = 0;i < fileTypeList.size();i++) {
            if (fileTypeList.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (flag == 0) {
                Util.intentToActivity(context, MainActivity.class);

                BaseApplication.getInstance().exit();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
