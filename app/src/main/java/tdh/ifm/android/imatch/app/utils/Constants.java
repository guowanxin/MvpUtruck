package tdh.ifm.android.imatch.app.utils;

/**
 * Author：gwx
 * Create at：2017/4/24 19:15
 */
public class Constants {


    public static final String USERID = "userId";
    public static final String USERNAME = "userName";
    public static final String PHONE = "phone";
    public static final String PLATENO = "plateNo";
    public static final String PASSWORD = "password";
    public static final String USERTYPE = "userType";
    public static final String LEVEL = "level";
    public static final String TOKEN = "token";

    public static final String URL = "url";

    /** 货主 **/
    public static final String USERTYPE_S = "S";
    /** 司机 **/
    public static final String USERTYPE_C = "C";
    /** 经纪人 **/
    public static final String USERTYPE_AGENT = "AGENT";
    /** 物流企业 **/
    public static final String USERTYPE_L = "LC";

    /** 等级 初级 **/
    public static final String LEVEL_PRIMARY = "1";
    /** 等级 高级 **/
    public static final String LEVEL_HIGHER = "11";

    /** L 装货-运输管控必填项*/
    public static final String L = "L";
    /** S 发车*/
    public static final String S = "S";
    /** A 到达*/
    public static final String A = "A";
    /** D 卸货-运输状态最终状态-运输管控必填项*/
    public static final String D = "D";

    /** 1 系统公告*/
    public static final String classCd_one="S";
    /** 2 订单消息*/
    public static final String classCd_two="O";
    /** 3 财务交易*/
    public static final String classCd_three="F";
    /** 4 异常提醒*/
    public static final String classCd_four="E";


    public static final String LAT = "lat";
    public static final String LON = "lon";






    /** 版本更新 **/
    public static final String UPDATE_WIFI = "update.wifi";
    public static final String UPDATE_SHOW = "update.show";
    public static final int TYPE_NETWORK_CELL = 1;
    public static final int TYPE_NETWORK_WIFI = 2;

    public static final String tkcarTypeValues[] = {"高栏","高低板", "低栏", "厢式", "平板"};
    public static final String tkcartypeKeys[] = {"GL","GDB", "DL", "XS", "PB"};

    public static final String stkLenValues[] = {"17.5米", "16米", "13米", "12.5米", "9.6米", "8.2米", "7.8米", "7.6米", "7.2米", "6.8米", "6.2米", "5米", "4.2米"};
    public static final String stkLenKeys[] = {"17.500", "16.000", "13.000", "12.500", "9.600", "8.200", "7.800", "7.600", "7.200", "6.800", "6.200", "5.000", "4.200"};
}
