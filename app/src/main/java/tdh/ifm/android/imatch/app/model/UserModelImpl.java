package tdh.ifm.android.imatch.app.model;

import android.content.Context;
import android.database.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import tdh.ifm.android.imatch.app.base.BaseNet;
import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BaseRequest;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.Location;
import tdh.ifm.android.imatch.app.bean.RequestAddFriendSearch;
import tdh.ifm.android.imatch.app.bean.AttentionLine;
import tdh.ifm.android.imatch.app.bean.FileType;
import tdh.ifm.android.imatch.app.bean.FriendInfo;
import tdh.ifm.android.imatch.app.bean.MemberInfo;
import tdh.ifm.android.imatch.app.bean.MyMessage;
import tdh.ifm.android.imatch.app.bean.RegisterDriver;
import tdh.ifm.android.imatch.app.bean.RegisterShipper;
import tdh.ifm.android.imatch.app.bean.CompanyInfos;
import tdh.ifm.android.imatch.app.bean.RequestAddAttentionLine;
import tdh.ifm.android.imatch.app.bean.RequestDeleteAttentionLine;
import tdh.ifm.android.imatch.app.bean.RequestDeleteFriend;
import tdh.ifm.android.imatch.app.bean.RequestFriendList;
import tdh.ifm.android.imatch.app.bean.RequestFriend;
import tdh.ifm.android.imatch.app.bean.RequestLogin;
import tdh.ifm.android.imatch.app.bean.PersonInfo;
import tdh.ifm.android.imatch.app.bean.RequestMessage;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.bean.RequestResetPwd;
import tdh.ifm.android.imatch.app.bean.RequestUpdateControl;
import tdh.ifm.android.imatch.app.bean.TransportControl;
import tdh.ifm.android.imatch.app.bean.User;
import tdh.ifm.android.imatch.app.utils.Util;

/**
 * Author：gwx
 * Create at：2017/5/5 14:54
 */
public class UserModelImpl extends BaseNet implements UserModel {

    private Context context;

    public UserModelImpl(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void loginIn(RequestLogin request, final OnLoginInListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,request));
        Call<BaseResponse<User>> call = api.loginIn(getBody(json));
        call.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onLoginInSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void loginOut(final OnLoginOutListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,null));
        Call<BaseResponse> call = api.loginOut(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                if (response.code() == 200) {
//                    int code = Util.getResCode(response.body());
//                    if (code == 200) {
//                        listener.onLoginOutSuccess(response.body());
//                    } else {
//                        listener.onResState(code);
//                    }
//                } else {
//                    listener.onResState(response.code());
//                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
//                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void sendLoginCode(RequestPhone requestPhone, final OnLoginInListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,requestPhone));
        Call<BaseResponse> call = api.sendLoginCode(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onSendLoginCodeSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void queryMember(final OnQueryMemberListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,null));
        Call<BaseResponse<MemberInfo>> call = api.queryMember(getBody(json));
        call.enqueue(new Callback<BaseResponse<MemberInfo>>() {
            @Override
            public void onResponse(Call<BaseResponse<MemberInfo>> call, Response<BaseResponse<MemberInfo>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onQueryMemberSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MemberInfo>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void getFriendList(final RequestFriendList request, final  OnFriendListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,request));
        Call<BaseResponse<BasePageList<List<FriendInfo>>>> call = api.friendList(getBody(json));
        call.enqueue(new Callback<BaseResponse<BasePageList<List<FriendInfo>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BasePageList<List<FriendInfo>>>> call, Response<BaseResponse<BasePageList<List<FriendInfo>>>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onFriendListSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BasePageList<List<FriendInfo>>>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void deleteFriend(RequestDeleteFriend request, final  OnDeleteFriendListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,request));
        Call<BaseResponse> call = api.deleteFriend(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onDeleteFriendSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void addFriendSearch(RequestAddFriendSearch requestAddFriendSearch, final OnAddFriendList listener) {
        String json = gson.toJson(Util.getBaseRequest(context,requestAddFriendSearch));
        Call<BaseResponse<List<FriendInfo>>> call = api.addFriendSearch(getBody(json));
        call.enqueue(new Callback<BaseResponse<List<FriendInfo>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<FriendInfo>>> call, Response<BaseResponse<List<FriendInfo>>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onAddFriendSearchSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<FriendInfo>>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void addFriend(RequestFriend request, final  OnAddFriendList listener) {
        String json = gson.toJson(Util.getBaseRequest(context,request));
        Call<BaseResponse> call = api.addFriend(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onAddFriendSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void registerDriver(RegisterDriver request, final OnRegisterDriverListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,request));
        Call<BaseResponse<User>> call = api.registerDriver(getBody(json));
        call.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onRegisterDriverSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void registerAgent(RegisterDriver registerDriver,final OnRegisterAgentListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,registerDriver));
        Call<BaseResponse<User>> call = api.registerAgent(getBody(json));
        call.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onRegisterAgentSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void registerShipper(RegisterShipper registerShipper,final OnRegisterShipperListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,registerShipper));
        Call<BaseResponse> call = api.registerShipper(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onRegisterShipperSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void registerCompany(RegisterShipper registerShipper,final OnRegisterCompanyListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,registerShipper));
        Call<BaseResponse> call = api.registerCompany(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onRegisterCompanySuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void sendRegisterCode(RequestPhone requestPhone,final OnRegisterCodeListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,requestPhone));
        Call<BaseResponse> call = api.sendRegisterCode(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onSendRegisterCodeSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }


    @Override
    public void resetPassword(RequestResetPwd request, final OnResetPwdListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,request));
        Call<BaseResponse> call = api.resetPassword(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onResetPwdSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void sendPasswordCode(RequestPhone requestPhone,final OnResetPwdListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,requestPhone));
        Call<BaseResponse> call = api.sendPasswordCode(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onSendPasswordCodeSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void queryMemberAuthInfo(final OnMemberAuthInfoListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,null));
        Call<BaseResponse<List<FileType>>> call = api.queryMemberAuthInfo(getBody(json));
        call.enqueue(new Callback<BaseResponse<List<FileType>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<FileType>>> call, Response<BaseResponse<List<FileType>>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onQueryMemberAuthInfoSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<FileType>>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void completionMemberAuthInfo(List<FileType> fileTypes, final OnMemberAuthInfoListener listener) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        if (fileTypes != null && fileTypes.size() > 0) {
            for (int i = 0; i < fileTypes.size(); i++) {
                if (fileTypes.get(i) == null) {
                    continue;
                }
                File file = new File(fileTypes.get(i).getUrl());
                RequestBody requestBody;
                if (fileTypes.get(i).getBytes() != null) {
                    requestBody = RequestBody.create(MediaType.
                            parse("multipart/form-data"), fileTypes.get(i).getBytes());
                } else {
                    requestBody = RequestBody.create(MediaType.
                            parse("multipart/form-data"), fileTypes.get(i).getUrl());
                }
                MultipartBody.Part part = MultipartBody.Part.createFormData(fileTypes.get(i).getRefType(),
                        file.getName(), requestBody);
                parts.add(part);
            }
        }
        Call<BaseResponse> call = api.completionMemberInfo(parts);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onCompletionMemberAuthInfoSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void personMessage(final OnPersonMessageListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,null));
        Call<BaseResponse<PersonInfo>> call = api.personMessage(getBody(json));
        call.enqueue(new Callback<BaseResponse<PersonInfo>>() {
            @Override
            public void onResponse(Call<BaseResponse<PersonInfo>> call, Response<BaseResponse<PersonInfo>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onPersonMessageSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PersonInfo>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void companyMessage(final OnPersonMessageListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,null));
        Call<BaseResponse<CompanyInfos>> call = api.companyMessage(getBody(json));
        call.enqueue(new Callback<BaseResponse<CompanyInfos>>() {
            @Override
            public void onResponse(Call<BaseResponse<CompanyInfos>> call, Response<BaseResponse<CompanyInfos>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onCompanyMessageSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CompanyInfos>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void updatePersonMessage(PersonInfo request, final OnPersonMessageListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,request));
        Call<BaseResponse> call = api.updatePersonMessage(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onUpdatePersonMessageSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void updateCompanyMessage(CompanyInfos request, final OnPersonMessageListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,request));
        Call<BaseResponse> call = api.updateCompanyMessage(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onUpdateCompanyMessageSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void myMessageList(final OnMyMessageListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,null));
        Call<BaseResponse<List<MyMessage>>> call = api.myMessageList(getBody(json));
        call.enqueue(new Callback<BaseResponse<List<MyMessage>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<MyMessage>>> call, Response<BaseResponse<List<MyMessage>>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onMyMessageSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<MyMessage>>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void myMessageDetail(RequestMessage MyMessage,final OnMyMessageDetailListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,MyMessage));
        Call<BaseResponse<BasePageList<List<MyMessage>>>> call = api.myMessageDetail(getBody(json));
        call.enqueue(new Callback<BaseResponse<BasePageList<List<MyMessage>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BasePageList<List<MyMessage>>>> call, Response<BaseResponse<BasePageList<List<MyMessage>>>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onMyMessageDetailSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BasePageList<List<MyMessage>>>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void attentionLineList(final OnAttentionLineListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,null));
        Call<BaseResponse<List<AttentionLine>>> call = api.attentionLineList(getBody(json));
        call.enqueue(new Callback<BaseResponse<List<AttentionLine>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<AttentionLine>>> call, Response<BaseResponse<List<AttentionLine>>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onAttentionLineListSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<AttentionLine>>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void addAttentionLine(RequestAddAttentionLine requestAddAttentionLine, final OnAttentionLineListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,requestAddAttentionLine));
        Call<BaseResponse> call = api.addAttentionLine(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onAddAttentionLineSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void deleteAttentionLine(RequestDeleteAttentionLine requestDeleteAttentionLine, final OnAttentionLineListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,requestDeleteAttentionLine));
        Call<BaseResponse> call = api.deleteAttentionLine(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onDeleteAttentionLineSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void getControlList(final OnTransportControlListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,null));
        Call<BaseResponse<List<TransportControl>>> call = api.getControlList(getBody(json));
        call.enqueue(new Callback<BaseResponse<List<TransportControl>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<TransportControl>>> call, Response<BaseResponse<List<TransportControl>>> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onTransportControlListSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<TransportControl>>> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void updateControl(RequestUpdateControl requestUpdateControl, final OnTransportControlListener listener) {
        String json = gson.toJson(Util.getBaseRequest(context,requestUpdateControl));
        Call<BaseResponse> call = api.updateControl(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    int code = Util.getResCode(response.body());
                    if (code == 200) {
                        listener.onUpdateControlSuccess(response.body());
                    } else {
                        listener.onResState(code);
                    }
                } else {
                    listener.onResState(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(t.getMessage(),t);
            }
        });
    }

    @Override
    public void location(Location location) {
        String json = gson.toJson(Util.getBaseRequest(context,location));
        Call<BaseResponse> call = api.location(getBody(json));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
            }
        });
    }
}
