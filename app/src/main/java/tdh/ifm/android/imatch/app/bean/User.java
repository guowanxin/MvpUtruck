package tdh.ifm.android.imatch.app.bean;

/**
 * Author：gwx
 * Create at：2017/5/8 13:39
 */
public class User {
    /** 会员名称 **/
    private String username;
    /** 会话ID **/
    private String sessionId;
    /** 会员ID **/
    private Integer profileId;
    /** 企业ID **/
    private Integer companyId;
    /** 企业名称 **/
    private String companyName;
    /** 优卡用户身份（货主/物流企业/经纪人/司机） **/
    private String usrTypeCd;
    private String plateNo;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUsrTypeCd() {
        return usrTypeCd;
    }

    public void setUsrTypeCd(String usrTypeCd) {
        this.usrTypeCd = usrTypeCd;
    }
}
