package tdh.ifm.android.imatch.app.model;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.AttentionLine;
import tdh.ifm.android.imatch.app.bean.CompanyInfos;
import tdh.ifm.android.imatch.app.bean.FileType;
import tdh.ifm.android.imatch.app.bean.FriendInfo;
import tdh.ifm.android.imatch.app.bean.MemberInfo;
import tdh.ifm.android.imatch.app.bean.MyMessage;
import tdh.ifm.android.imatch.app.bean.PersonInfo;
import tdh.ifm.android.imatch.app.bean.RequestMessage;
import tdh.ifm.android.imatch.app.bean.TransportControl;
import tdh.ifm.android.imatch.app.bean.User;
import tdh.ifm.android.imatch.app.utils.NetContant;

/**
 * Author：gwx
 * Create at：2017/4/24 19:08
 */
public interface Api {

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.LOGININ)
    Call<BaseResponse<User>> loginIn(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.LOGINOUT)
    Call<BaseResponse> loginOut(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.SENDLOGINCODE)
    Call<BaseResponse> sendLoginCode(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.QUERYMEMBER)
    Call<BaseResponse<MemberInfo>> queryMember(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.FRIEND_LIST)
    Call<BaseResponse<BasePageList<List<FriendInfo>>>> friendList(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.ADD_FRIEND_SEARCH)
    Call<BaseResponse<List<FriendInfo>>> addFriendSearch(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.ADD_FRIEND)
    Call<BaseResponse> addFriend(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.DELETE_FRIEND)
    Call<BaseResponse> deleteFriend(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.REGISTER_DRIVER)
    Call<BaseResponse<User>> registerDriver(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.REGISTER_SHIPPER)
    Call<BaseResponse> registerShipper(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.REGISTER_AGENT)
    Call<BaseResponse<User>> registerAgent(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.REGISTER_COMPANY)
    Call<BaseResponse> registerCompany(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.SEND_REGISTER_CODE)
    Call<BaseResponse> sendRegisterCode(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.RESETPASSWORD)
    Call<BaseResponse> resetPassword(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.SENDPASSWOIDCODE)
    Call<BaseResponse> sendPasswordCode(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.QUERY_PERSON_MESSAGE)
    Call<BaseResponse<PersonInfo>> personMessage(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.QUERY_COMPANY_MESSAGE)
    Call<BaseResponse<CompanyInfos>> companyMessage(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.UPDATE_PERSON_MESSAGE)
    Call<BaseResponse> updatePersonMessage(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.UPDATE_COMPANY_MESSAGE)
    Call<BaseResponse> updateCompanyMessage(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.QUERY_MEMBERAUTH_INFO)
    Call<BaseResponse<List<FileType>>> queryMemberAuthInfo(@Body RequestBody requestBody);

    @Multipart
    @POST(NetContant.COMPLETION_MEMBERAUTH_INFO)
    Call<BaseResponse> completionMemberInfo(@Part() List<MultipartBody.Part> files);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.MYMESSAGE_LIST)
    Call<BaseResponse<List<MyMessage>>> myMessageList(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.MYMESSAGE_DATEIL)
    Call<BaseResponse<BasePageList<List<MyMessage>>>> myMessageDetail(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.ATTENTIONLINE_LIST)
    Call<BaseResponse<List<AttentionLine>>> attentionLineList(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.ADDATTENTIONLINE)
    Call<BaseResponse> addAttentionLine(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.DELETEATTENTIONLINE)
    Call<BaseResponse> deleteAttentionLine(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.CONTROL_LIST)
    Call<BaseResponse<List<TransportControl>>> getControlList(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.UPDATECONTROL)
    Call<BaseResponse> updateControl(@Body RequestBody requestBody);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST(NetContant.UPLOCATION)
    Call<BaseResponse> location(@Body RequestBody requestBody);
}
