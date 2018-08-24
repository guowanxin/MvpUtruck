package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.AttentionLine;
import tdh.ifm.android.imatch.app.bean.RequestAddAttentionLine;
import tdh.ifm.android.imatch.app.bean.RequestDeleteAttentionLine;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.AttentionLineView;

/**
 * Author：gwx
 * Create at：2017/5/16 20:37
 */
public class AttentionLinePresenterImpl implements UserModel.OnAttentionLineListener,BasePresenter {

    private AttentionLineView attentionLineView;
    public UserModel userModel;

    public AttentionLinePresenterImpl(Context context, AttentionLineView attentionLineView){
        this.attentionLineView=attentionLineView;
        userModel=new UserModelImpl(context);
    }

    public void attentionLineList() {
        attentionLineView.showProgress();
        userModel.attentionLineList(this);
    }

    public void addAttentionLine(RequestAddAttentionLine request) {
        attentionLineView.showProgress();
        userModel.addAttentionLine(request,this);
    }

    public void deleteAttentionLine(RequestDeleteAttentionLine request) {
        attentionLineView.showProgress();
        userModel.deleteAttentionLine(request,this);
    }

    @Override
    public void onFailure(String str, Throwable t) {
        if (attentionLineView == null) {
            return;
        }
        attentionLineView.hideprogress();
        attentionLineView.showFailure(str, t);
    }

    @Override
    public void onResState(int code) {
        if (attentionLineView == null) {
            return;
        }
        attentionLineView.showResState(code);
        attentionLineView.hideprogress();
    }

    @Override
    public void onDestory() {
        attentionLineView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onAttentionLineListSuccess(BaseResponse<List<AttentionLine>> baseResponse) {
        if (attentionLineView == null) {
            return;
        }
        if (baseResponse != null) {
            attentionLineView.onAttentionLineListSuccess(baseResponse);
        }
        attentionLineView.hideprogress();
    }

    @Override
    public void onAddAttentionLineSuccess(BaseResponse baseResponse) {
        if (attentionLineView == null) {
            return;
        }
        if (baseResponse != null) {
            attentionLineView.onAddAttentionLineSuccess(baseResponse);
        }
        attentionLineView.hideprogress();
    }

    @Override
    public void onDeleteAttentionLineSuccess(BaseResponse baseResponse) {
        if (attentionLineView == null) {
            return;
        }
        if (baseResponse != null) {
            attentionLineView.onDeleteAttentionLineSuccess(baseResponse);
        }
        attentionLineView.hideprogress();
    }
}
