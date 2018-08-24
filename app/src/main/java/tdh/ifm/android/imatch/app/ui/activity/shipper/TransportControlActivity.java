package tdh.ifm.android.imatch.app.ui.activity.shipper;

import android.view.View;
import android.widget.LinearLayout;

import com.tdh.common.utils.APPLog;

import java.util.List;

import butterknife.BindView;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RequestUpdateControl;
import tdh.ifm.android.imatch.app.bean.TransportControl;
import tdh.ifm.android.imatch.app.presenter.TransportControlPresenterImpl;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.ui.view.SwitchView;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.TransportControlView;

/**
 * Author：gwx
 * Create at：2017/5/18 14:36
 */
public class TransportControlActivity extends BaseActivity implements TransportControlView{

    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.switchview1)
    SwitchView switchview1;
    @BindView(R.id.switchview_img1)
    SwitchView switchviewImg1;
    @BindView(R.id.ll_state_img1)
    LinearLayout llStateImg1;
    @BindView(R.id.switchview2)
    SwitchView switchview2;
    @BindView(R.id.switchview_img2)
    SwitchView switchviewImg2;
    @BindView(R.id.ll_state_img2)
    LinearLayout llStateImg2;
    @BindView(R.id.switchview3)
    SwitchView switchview3;
    @BindView(R.id.switchview_img3)
    SwitchView switchviewImg3;
    @BindView(R.id.ll_state_img3)
    LinearLayout llStateImg3;
    @BindView(R.id.switchview4)
    SwitchView switchview4;
    @BindView(R.id.switchview_img4)
    SwitchView switchviewImg4;
    @BindView(R.id.ll_state_img4)
    LinearLayout llStateImg4;

    private TransportControlPresenterImpl transportControlPresenter;

    private List<TransportControl> transportControlList;

    private String uuidL;
    private String uuidS;
    private String uuidA;
    private String uuidD;


    @Override
    public int getContentViewId() {
        return R.layout.activity_transport_control;
    }

    @Override
    public void initTitleView() {
        super.initTitleView();
        titleview.getTv_title().setText("运输管控");
        titleview.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        setSwitchState();

        transportControlPresenter = new TransportControlPresenterImpl(context,this);

        transportControlPresenter.getControlList();
    }

    private void setSwitchState() {
        switchview1.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {

            }

            @Override
            public void toggleToOff() {

            }
        });
        switchview4.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {

            }

            @Override
            public void toggleToOff() {

            }
        });
        switchview2.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                llStateImg2.setVisibility(View.VISIBLE);
                switchview2.setState(true);

                updateControlHttp(uuidS,Constants.S,true,false);
            }

            @Override
            public void toggleToOff() {
                llStateImg2.setVisibility(View.GONE);
                switchview2.setState(false);
                switchviewImg2.setState(false);

                updateControlHttp(uuidS,Constants.S,false,false);
            }
        });
        switchview3.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                llStateImg3.setVisibility(View.VISIBLE);
                switchview3.setState(true);

                updateControlHttp(uuidA,Constants.A,true,false);
            }

            @Override
            public void toggleToOff() {
                llStateImg3.setVisibility(View.GONE);
                switchview3.setState(false);
                switchviewImg3.setState(false);

                updateControlHttp(uuidA,Constants.A,false,false);
            }
        });
        switchviewImg1.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                updateControlHttp(uuidL,Constants.L,true,true);

                switchviewImg1.setState(true);
            }

            @Override
            public void toggleToOff() {
                updateControlHttp(uuidL,Constants.L,true,false);

                switchviewImg1.setState(false);
            }
        });
        switchviewImg2.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                updateControlHttp(uuidS,Constants.S,true,true);

                switchviewImg2.setState(true);
            }

            @Override
            public void toggleToOff() {
                updateControlHttp(uuidS,Constants.S,true,false);

                switchviewImg2.setState(false);
            }
        });
        switchviewImg3.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                updateControlHttp(uuidA,Constants.A,true,true);

                switchviewImg3.setState(true);
            }

            @Override
            public void toggleToOff() {
                updateControlHttp(uuidA,Constants.A,true,false);

                switchviewImg3.setState(false);
            }
        });
        switchviewImg4.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                updateControlHttp(uuidD,Constants.D,true,true);

                switchviewImg4.setState(true);
            }

            @Override
            public void toggleToOff() {
                updateControlHttp(uuidD,Constants.D,true,false);

                switchviewImg4.setState(false);
            }
        });
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
    public void onTransportControlListSuccess(BaseResponse<List<TransportControl>> baseResponse) {
        if (baseResponse.isSuccess()) {
            transportControlList = baseResponse.getBody();
            setControlValue();
        }
    }

    @Override
    public void onUpdateControlSuccess(BaseResponse baseResponse) {
//        if (!TextUtils.isEmpty(baseResponse.getMessage())) {
//            CommonUtil.getToast(context,baseResponse.getMessage());
//        }
    }

    private void setControlValue() {
        if (transportControlList != null && transportControlList.size() > 0) {
            for (int i = 0;i < transportControlList.size();i++) {
                TransportControl transportControl = transportControlList.get(i);
                if (transportControl.getNodeClassCd().equals(Constants.L)) {
                    uuidL = transportControl.getUuid();
                    if (transportControl.isOpen()) {
                        llStateImg1.setVisibility(View.VISIBLE);
                        switchview1.setState(true);
                        if (transportControl.isUpload()) {
                            switchviewImg1.setState(true);
                        }else {
                            switchviewImg1.setState(false);
                        }
                    }else {
                        llStateImg1.setVisibility(View.GONE);
                        switchview1.setState(false);
                    }
                }else if (transportControl.getNodeClassCd().equals(Constants.S)) {
                    uuidS = transportControl.getUuid();
                    if (transportControl.isOpen()) {
                        llStateImg2.setVisibility(View.VISIBLE);
                        switchview2.setState(true);
                        if (transportControl.isUpload()) {
                            switchviewImg2.setState(true);
                        }else {
                            switchviewImg2.setState(false);
                        }
                    }else {
                        llStateImg2.setVisibility(View.GONE);
                        switchview2.setState(false);
                    }
                }else if (transportControl.getNodeClassCd().equals(Constants.A)) {
                    uuidA = transportControl.getUuid();
                    if (transportControl.isOpen()) {
                        llStateImg3.setVisibility(View.VISIBLE);
                        switchview3.setState(true);
                        if (transportControl.isUpload()) {
                            switchviewImg3.setState(true);
                        }else {
                            switchviewImg3.setState(false);
                        }
                    }else {
                        llStateImg3.setVisibility(View.GONE);
                        switchview3.setState(false);
                    }
                }else if (transportControl.getNodeClassCd().equals(Constants.D)) {
                    uuidD = transportControl.getUuid();
                    if (transportControl.isOpen()) {
                        llStateImg4.setVisibility(View.VISIBLE);
                        switchview4.setState(true);
                        if (transportControl.isUpload()) {
                            switchviewImg4.setState(true);
                        }else {
                            switchviewImg4.setState(false);
                        }
                    }else {
                        llStateImg4.setVisibility(View.GONE);
                        switchview4.setState(false);
                    }
                }
            }
        }
    }

    private void updateControlHttp(String uuid,String nodeClassCd,boolean isOpen,boolean isUpLoad) {
        RequestUpdateControl requestUpdateControl = new RequestUpdateControl();
        requestUpdateControl.setUuid(uuid);
        requestUpdateControl.setNodeClassCd(nodeClassCd);
        requestUpdateControl.setOpen(isOpen);
        requestUpdateControl.setUpload(isUpLoad);
        transportControlPresenter.updateControl(requestUpdateControl);
    }
}
