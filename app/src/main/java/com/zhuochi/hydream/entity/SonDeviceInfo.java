package com.zhuochi.hydream.entity;

import com.google.gson.Gson;

/**
 * @author Cuixc
 * @date on  2018/6/22
 */

public class SonDeviceInfo {

    /**
     * device_key : 9019df64-ff05-433f-a8c0-51ce63d36265_faucet_01
     * controller_key : 9019df64-ff05-433f-a8c0-51ce63d36265
     * is_controller : 0
     * device_user_id : 1
     * device_name : 水控机01
     * device_number : 01
     * org_id : 10
     * org_area_id : 15
     * building_id : 90
     * building_number :
     * device_type_key : faucet
     * v_device_type_key : dev_controller_2
     * device_area_id : 3
     * settlement_area_id : 4
     * device_status : reserved
     * need_reserve : 1
     * longitude : 117.1297480
     * latitude : 36.6872410
     * user_id : 25
     * user_device_pwd : byj1fJdKJBgk87FO4XlrrQ\u003d\u003d
     * goods_id : 0
     * last_operate_time : 0
     * max_wait_time : 480
     * reserve_time : 1529569322
     * start_time : 1529475745
     * end_time : 0
     * order_sn : 27982
     * stop_reason :
     * uuid : 9019df64-ff05-433f-a8c0-51ce63d36265_faucet_01
     * user_device_password : 159357258
     * deviceAreaName : 1号楼东浴室
     */

    private String device_key;
    private String controller_key;
    private int is_controller;
    private int device_user_id;
    private String device_name;
    private String device_number;
    private int org_id;
    private int org_area_id;
    private int building_id;
    private String building_number;
    private String device_type_key;
    private String v_device_type_key;
    private int device_area_id;
    private int settlement_area_id;
    private String device_status;
    private int need_reserve;
    private String longitude;
    private String latitude;
    private int user_id;
    private String user_device_pwd;
    private int goods_id;
    private String last_operate_time;
    private int max_wait_time;
    private String reserve_time;
    private String start_time;
    private String end_time;
    private String order_sn;
    private String stop_reason;
    private String uuid;
    private String user_device_password;
    private String deviceAreaName;

    public static SonDeviceInfo objectFromData(String str) {

        return new Gson().fromJson(str, SonDeviceInfo.class);
    }

    public String getDevice_key() {
        return device_key;
    }

    public void setDevice_key(String device_key) {
        this.device_key = device_key;
    }

    public String getController_key() {
        return controller_key;
    }

    public void setController_key(String controller_key) {
        this.controller_key = controller_key;
    }

    public int getIs_controller() {
        return is_controller;
    }

    public void setIs_controller(int is_controller) {
        this.is_controller = is_controller;
    }

    public int getDevice_user_id() {
        return device_user_id;
    }

    public void setDevice_user_id(int device_user_id) {
        this.device_user_id = device_user_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_number() {
        return device_number;
    }

    public void setDevice_number(String device_number) {
        this.device_number = device_number;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public int getOrg_area_id() {
        return org_area_id;
    }

    public void setOrg_area_id(int org_area_id) {
        this.org_area_id = org_area_id;
    }

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_number() {
        return building_number;
    }

    public void setBuilding_number(String building_number) {
        this.building_number = building_number;
    }

    public String getDevice_type_key() {
        return device_type_key;
    }

    public void setDevice_type_key(String device_type_key) {
        this.device_type_key = device_type_key;
    }

    public String getV_device_type_key() {
        return v_device_type_key;
    }

    public void setV_device_type_key(String v_device_type_key) {
        this.v_device_type_key = v_device_type_key;
    }

    public int getDevice_area_id() {
        return device_area_id;
    }

    public void setDevice_area_id(int device_area_id) {
        this.device_area_id = device_area_id;
    }

    public int getSettlement_area_id() {
        return settlement_area_id;
    }

    public void setSettlement_area_id(int settlement_area_id) {
        this.settlement_area_id = settlement_area_id;
    }

    public String getDevice_status() {
        return device_status;
    }

    public void setDevice_status(String device_status) {
        this.device_status = device_status;
    }

    public int getNeed_reserve() {
        return need_reserve;
    }

    public void setNeed_reserve(int need_reserve) {
        this.need_reserve = need_reserve;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_device_pwd() {
        return user_device_pwd;
    }

    public void setUser_device_pwd(String user_device_pwd) {
        this.user_device_pwd = user_device_pwd;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getLast_operate_time() {
        return last_operate_time;
    }

    public void setLast_operate_time(String last_operate_time) {
        this.last_operate_time = last_operate_time;
    }

    public int getMax_wait_time() {
        return max_wait_time;
    }

    public void setMax_wait_time(int max_wait_time) {
        this.max_wait_time = max_wait_time;
    }

    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getStop_reason() {
        return stop_reason;
    }

    public void setStop_reason(String stop_reason) {
        this.stop_reason = stop_reason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUser_device_password() {
        return user_device_password;
    }

    public void setUser_device_password(String user_device_password) {
        this.user_device_password = user_device_password;
    }

    public String getDeviceAreaName() {
        return deviceAreaName;
    }

    public void setDeviceAreaName(String deviceAreaName) {
        this.deviceAreaName = deviceAreaName;
    }
}
