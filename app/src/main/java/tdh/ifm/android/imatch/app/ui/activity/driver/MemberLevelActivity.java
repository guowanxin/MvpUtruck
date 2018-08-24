package tdh.ifm.android.imatch.app.ui.activity.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;
import tdh.ifm.android.imatch.app.utils.Util;

/**
 * 会员等级
 * Created by tdh on 2017/5/2.
 */

public class MemberLevelActivity extends BaseActivity {

    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.tv_member_level)
    TextView tvMemberLevel;
    @BindView(R.id.tv_primary_member)
    TextView tvPrimaryMember;
    @BindView(R.id.tv_advanced_member)
    TextView tvAdvancedMember;
    @BindView(R.id.btn_get_advanced_member)
    Button btnGetAdvancedMember;

    @BindString(R.string.txt_member)
    String title;

    private String userType;
    private String level;

    @Override
    public int getContentViewId() {
        return R.layout.activity_member_level;
    }

    @Override
    public void initTitleView() {
        super.initTitleView();
        titleview.getTv_title().setText(title);
        titleview.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        userType = SharedPreferencesUtil.getValue(Constants.USERTYPE, "");
        if (userType.equals(Constants.USERTYPE_S)) {
            tvPrimaryMember.setText(R.string.txt_primary_member_shipper);
            tvAdvancedMember.setText(R.string.txt_advanced_member_shipper);
        } else if (userType.equals(Constants.USERTYPE_C)) {
            tvPrimaryMember.setText(R.string.txt_primary_member_driver);
            tvAdvancedMember.setText(R.string.txt_advanced_member_driver);
        } else if (userType.equals(Constants.USERTYPE_AGENT)) {
            tvPrimaryMember.setText(R.string.txt_primary_member_broker);
            tvAdvancedMember.setText(R.string.txt_advanced_member_broker);
        } else if (userType.equals(Constants.USERTYPE_L)) {
            tvPrimaryMember.setText(R.string.txt_primary_member_enterprise);
            tvAdvancedMember.setText(R.string.txt_advanced_member_enterprise);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            level = bundle.getString(Constants.LEVEL, "");
            if (level.equals(Constants.LEVEL_PRIMARY)) {
                tvMemberLevel.setText("初级会员");
                btnGetAdvancedMember.setText(R.string.btn_get_advanced_member);
            } else if (level.equals(Constants.LEVEL_HIGHER)) {
                tvMemberLevel.setText("高级会员");
                btnGetAdvancedMember.setText("修改认证资料");
            }
        }
    }

    @OnClick(R.id.btn_get_advanced_member)
    public void onAdvancedMemberClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt("flag", 1);
        bundle.putString(Constants.LEVEL, level);
        Util.intentToActivity(context, MemberLevelEditActivity.class, 10,bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
//            level = data.getStringExtra(Constants.LEVEL);
//            if (Constants.LEVEL_PRIMARY.equals(level)) {
//                tvMemberLevel.setText("初级会员");
//                btnGetAdvancedMember.setText(R.string.btn_get_advanced_member);
//            }else if (Constants.LEVEL_HIGHER.equals(level)) {
//                tvMemberLevel.setText("高级会员");
//                btnGetAdvancedMember.setText("修改认证资料");
//            }
            setResult(10);
            finish();
        }
    }

}
