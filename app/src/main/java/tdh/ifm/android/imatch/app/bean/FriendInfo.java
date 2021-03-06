package tdh.ifm.android.imatch.app.bean;

import java.io.Serializable;

/**
 * Created by tdh on 2017/5/5.
 */

public class FriendInfo implements Serializable{
    private Integer buddyMemberId;
    private Integer memberId;
    private String fullName;
    private String mobile;
    private String userType;
    private  String userLevel;
    private String uuid;

    private CompanyInfo companyInfo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private DriverInfo driverInfo;

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
    public Integer getBuddyMemberId() {
        return buddyMemberId;
    }

    public void setBuddyMemberId(Integer buddyMemberId) {
        this.buddyMemberId = buddyMemberId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }
}
