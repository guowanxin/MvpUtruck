package tdh.ifm.android.imatch.app.utils;

/**
 * Author：gwx
 * Create at：2017/4/21 19:11
 */
public class NetContant {

    public static final String TAG = "utruck";

//    public static final String HOST_URL = "http://192.168.65.78:8080/";

//    public static final String HOST_URL = "http://utruck.dev.56pingtai.baiji/";
//    public static final String HTML_URL = "http://192.168.65.54:7777/";

//    public static final String HOST_URL = "http://utruck.dev.56pingtai.baiji/";
//    public static final String HTML_URL = "http://h5.utruck.dev.56pingtai.baiji/";

//    public static final String HOST_URL = "http://utruck.test.56pingtai.baiji/";
//    public static final String HTML_URL = "http://h5.utruck.test.56pingtai.baiji/";

    public static final String HOST_URL = "http://utruck.oms.56pingtai.net/";
    public static final String HTML_URL = "http://h5.utruck.oms.56pingtai.net/";

    public static final String UPDATE_URL = "http://admin.56pingtai.net/dev/check-app-update.do";

    public static final String UT = "api/ut/";
    public static final String INDEX = "api/index/";
    public static final String MEMBER = "api/member/center/";
    public static final String TDPAY = "api/tdpay/";

    /**  我的好友  **/
    public static final String FRIEND_LIST = UT + "friend/list.do";
    /**  添加好友查询  **/
    public static final String ADD_FRIEND_SEARCH=UT+"friend/add.do";
    /**  添加好友  **/
    public static final String ADD_FRIEND = UT + "friend/create.do";
    /**  删除好友  **/
    public static final String DELETE_FRIEND = UT + "friend/delete.do";

    /**
     * 会员相关
     */
    /**  登录  **/
    public static final String LOGININ = INDEX + "login.do";
    /**  司机注册  **/
    public static final String REGISTER_DRIVER=INDEX+"registerDriver.do";
    /**  货主注册  **/
    public static final String REGISTER_SHIPPER=INDEX+"registerShipper.do";
    /**  经纪人注册  **/
    public static final String REGISTER_AGENT=INDEX+"registerAgent.do";
    /**  物流企业注册  **/
    public static final String REGISTER_COMPANY=INDEX+"registerLogisticsCompany.do";
    /**  退出登录  **/
    public static final String LOGINOUT = INDEX + "logout.do";
    /**  发送登录验证码  **/
    public static final String SENDLOGINCODE = INDEX + "sendMobileLoginCode.do";
    /**  查询当前登录基本信息  **/
    public static final String QUERYMEMBER = MEMBER + "queryMember.do";
    /**  发送注册验证码 **/
    public static final String SEND_REGISTER_CODE=INDEX+"sendRegisterCode.do";
    /**  重置登录密码  **/
    public static final String RESETPASSWORD = INDEX+"resetLoginPassword.do";
    /**  发送重置密码验证码  **/
    public static final String SENDPASSWOIDCODE=INDEX+"sendResetPasswordCode.do";
    /**  查询当前会员认证信息  **/
    public static final String QUERY_MEMBERAUTH_INFO = MEMBER+"queryMemberAuthInfo.do";
    /**  补全认证信息  **/
    public static final String COMPLETION_MEMBERAUTH_INFO = MEMBER+"completionMemberData.do";
    /**  查询当前个人登录信息  **/
    public static final String QUERY_PERSON_MESSAGE=MEMBER+"queryPersonal.do";
    /**  查询当前企业信息 **/
    public static final String QUERY_COMPANY_MESSAGE=MEMBER+"queryCompany.do";
    /**  修改个人信息审核  **/
    public static final String UPDATE_PERSON_MESSAGE=MEMBER+"updatePersonal.do";
    /** 修改企业信息审核  **/
    public static final String UPDATE_COMPANY_MESSAGE=MEMBER+"updateCompany.do";

    /**  我的钱包  **/
    public static final String MYWALLET = HOST_URL + TDPAY + "account.do";

    /**  我的消息列表  **/
    public static final String MYMESSAGE_LIST=UT+"message/latest.do";
    /**  我的消息详情  **/
    public static final String MYMESSAGE_DATEIL=UT+"message/list.do";

    /**  关注路线列表  **/
    public static final String ATTENTIONLINE_LIST=UT+"route/list.do";
    /**  添加关注路线  **/
    public static final String ADDATTENTIONLINE=UT+"route/create.do";
    /**  删除关注路线  **/
    public static final String DELETEATTENTIONLINE=UT+"route/delete.do";

    /**  运输管控节点列表  **/
    public static final String CONTROL_LIST=UT+"control/list.do";
    /**  运输管控节点设置  **/
    public static final String UPDATECONTROL=UT+"control/update.do";
    /**  上传位置信息  **/
    public static final String UPLOCATION="api/location/native/push.do";

    /**
     * html5部分
     */
    /**  首页  **/
    public static final String HTML = "#/";
    public static final String HTML_SHOUYE = HTML_URL + HTML;
//    public static final String HTML_SHOUYE = "http://utruck.dev.56pingtai.baiji/api/h5index.do";
    /**  我的运单  **/
    public static final String HTML_ORDERLIST = HTML_URL + HTML+ "myOrder";
    /**  运单详情  **/
    public static final String HTML_ORDERDETAL = HTML_URL + HTML+ "orderDetal";
    /**  已报价运单详情  **/
    public static final String HTML_WAITDETAL = HTML_URL + HTML+ "waitDetal";
    /**  查找货源  **/
    public static final String HTML_FINDSOURCE = HTML_URL + HTML+ "findSource";
    /**  历史报价  **/
    public static final String HTML_HISTORYORDER = HTML_URL + HTML+ "historyOrder";
    /**  等待成交  **/
    public static final String HTML_WAITFORPAY_C = HTML_URL + HTML+ "waitForPay";
    public static final String HTML_WAITFORPAY_A = HTML_URL + HTML+ "agentForPay";
    /**  选择城市  **/
    public static final String HTML_CITY = HTML_URL + HTML+ "cityList";
    /**  用户协议  **/
    public static final String HTML_USERAGREE = HTML_URL + HTML+ "userAgree";

}
