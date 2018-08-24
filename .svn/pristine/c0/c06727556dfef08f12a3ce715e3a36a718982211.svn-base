package tdh.ifm.android.imatch.app.bean;

import com.baidu.location.BDLocation;

import java.util.Date;

import tdh.ifm.android.imatch.app.utils.Util;

/**
 * Author：gwx
 * Create at：2017/5/11 17:33
 */
public class Location {

    private String speed;

    private String bearing;
    private String coorType;
    private double lng;
    private double lat;
    private String plateNo;

    private long positionTime;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getBearing() {
        return bearing;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public String getCoorType() {
        return coorType;
    }

    public void setCoorType(String coorType) {
        this.coorType = coorType;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public long getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(long positionTime) {
        this.positionTime = positionTime;
    }

    public static Location fromBaiduLocation(BDLocation bdl) {
        Location loc = new Location();

        loc.setSpeed(bdl.getSpeed()+"");
        loc.setCoorType(bdl.getCoorType());
        loc.setLat(bdl.getLatitude());
        loc.setLng(bdl.getLongitude());
        loc.setPositionTime(new Date().getTime());

        return loc;
    }
}
