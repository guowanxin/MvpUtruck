package tdh.ifm.android.imatch.app.bean;

import java.io.Serializable;

/**
 * Created by tdh on 2017/5/8.
 */

public class RegisterDriver implements Serializable {
    private String username;
    private String vehicleLength;
    private String vehicleModel;
    private String code;
    private String vehiclePlateNo;
    private String vehicleLoad;
    private String password;
    private String mobile;

    public String getVehicleLoad() {
        return vehicleLoad;
    }

    public void setVehicleLoad(String vehicleLoad) {
        this.vehicleLoad = vehicleLoad;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(String vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVehiclePlateNo() {
        return vehiclePlateNo;
    }

    public void setVehiclePlateNo(String vehiclePlateNo) {
        this.vehiclePlateNo = vehiclePlateNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
