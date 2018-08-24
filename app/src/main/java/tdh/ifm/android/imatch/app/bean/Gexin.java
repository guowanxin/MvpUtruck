package tdh.ifm.android.imatch.app.bean;

/**
 * Created by tdh on 2017/6/20.
 */

public class Gexin {
    /** 大类消息类型  1系统公告  2订单消息  3财务交易  4异常提醒 **/
    private String msgTypeCd;
    /** 订单消息类型 **/
    private String orderTypeCd;
    /** 用户id **/
    private String profileId;
    /** 订单id **/
    private String orderId;
    /** 扩展字段  暂时不用 **/
    private String refid1;
    private String refid2;


    public String getMsgTypeCd() {
        return msgTypeCd;
    }

    public void setMsgTypeCd(String msgTypeCd) {
        this.msgTypeCd = msgTypeCd;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRefid1() {
        return refid1;
    }

    public void setRefid1(String refid1) {
        this.refid1 = refid1;
    }

    public String getRefid2() {
        return refid2;
    }

    public void setRefid2(String refid2) {
        this.refid2 = refid2;
    }
}
