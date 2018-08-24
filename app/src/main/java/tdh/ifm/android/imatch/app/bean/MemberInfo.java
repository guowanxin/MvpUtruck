package tdh.ifm.android.imatch.app.bean;

/**
 * Author：gwx
 * Create at：2017/5/8 19:48
 */
public class MemberInfo {
    /** 会员名称 **/
    private String username;
    /** 会员等级 **/
    private String level;
    /** 会员ID **/
    private Integer profileId;
    /** 交易数 **/
    private Integer tradeNum;
    /** 身份信息（多个用,隔开） **/
    private String usrTypeCd;
    /** 会员类型  个人还是企业 **/
    private String memberType;
    /** 手机号码 **/
    private String mobile;
    /** 个人图像 **/
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Integer tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getUsrTypeCd() {
        return usrTypeCd;
    }

    public void setUsrTypeCd(String usrTypeCd) {
        this.usrTypeCd = usrTypeCd;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
