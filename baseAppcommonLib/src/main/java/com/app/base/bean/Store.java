package com.app.base.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by 7du-28 on 2017/10/16.
 */

public class Store{


    /**
     * store_id : 35
     * 0 : 35
     * store_name : 蓝山咖啡馆
     * 1 : 蓝山咖啡馆
     * store_address : 长华创意谷12
     * 2 : 长华创意谷12
     * store_position :
     * 3 :
     * store_output : 0
     * 4 : 0
     * store_order : 0
     * 5 : 0
     * store_score : 0
     * 6 : 0
     * store_introduction :
     * 7 :
     * store_mainimg :
     * 8 :
     * store_img :
     * 9 :
     * store_licence : upload/store/20171115053146244.jpg
     * 10 : upload/store/20171115053146244.jpg
     * store_linkman : 蓝山
     * 11 : 蓝山
     * store_contact : 15000000000
     * 12 : 15000000000
     * store_state : 1
     * 13 : 1
     * store_traffic :
     * 14 :
     * order_distance : 1.0
     * 15 : 1.0
     * store_balance : 0.00
     * 16 : 0.00
     * store_amount : 0.00
     * 17 : 0.00
     * store_officeorder : 0
     * 18 : 0
     * store_warning :
     * 19 :
     * store_remittee :
     * 20 :
     * store_bankcard :
     * 21 :
     * store_alipay :
     * 22 :
     * store_password :
     * 23 :
     * store_salescount : 0
     * 24 : 0
     * store_longitude :
     * 25 :
     * store_latitude :
     * 26 :
     * store_visitorall : 0
     * 27 : 0
     * store_visitorcur : 0
     * 28 : 0
     * store_visitorlea : 0
     * 29 : 0
     * store_tel :
     * 30 :
     * store_allorder : 0
     * 31 : 0
     * store_touxiang :
     * 32 :
     * store_regtime : 1510733320
     * 33 : 1510733320
     * update_time : 1512979485
     * 34 : 1512979485
     * store_areanum : 0
     * 35 : 0
     * store_citycode : 020
     * 36 : 020
     * mony_withdraw : 0.00
     * 37 : 0.00
     * score_withdraw : 0
     * 38 : 0
     * transport_id : 123
     * 39 : 123
     * passenger_openid : mrje3fa98622dee4227a252449512325640
     * 40 : mrje3fa98622dee4227a252449512325640
     * accumulate_money : 0.00
     * 41 : 0.00
     * accumulate_score : 0
     * 42 : 0
     * store_star : 2
     * 43 : 2
     * transport_start : 20.00
     * 44 : 20.00
     * distribution_id : null
     * 45 : null
     * userpic_arr : []
     * comment_total : 0
     * go_url : http://wofei_wap.7dugo.com/store_detail-35
     */

    private String sortLetters; // 显示数据拼音的首字母

    private String main_pic;

    private String store_id;

    private String store_name;

    private String store_address;

    private String store_position;

    private String store_output;

    private String store_order;

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }


    private String store_score;

    private String store_introduction;

    private String store_mainimg;

    private String store_img;

    private String store_licence;

    private String store_linkman;

    private String store_contact;

    private String store_state;

    private String store_traffic;

    private String order_distance;

    private String store_balance;

    private String store_amount;

    private String store_officeorder;

    private String store_warning;

    private String store_remittee;

    private String store_bankcard;

    private String store_alipay;

    private String store_password;

    private String store_salescount;

    private String store_longitude;

    private String store_latitude;

    private String store_visitorall;

    private String store_visitorcur;

    private String store_visitorlea;

    private String store_tel;

    private String store_allorder;
    private String store_touxiang;
    private String store_regtime;
    private String update_time;
    private String store_areanum;
    private String store_citycode;
    private String mony_withdraw;
    private String score_withdraw;
    private String transport_id;
    private String passenger_openid;
    private String accumulate_money;
    private String accumulate_score;
    private String store_star;
    private String transport_start;
    private Object distribution_id;
    private int comment_total;
    private String go_url;
    private List<String> userpic_arr;

    private float distance;

    public float getDistance() {
        return distance;
    }

    public String getMain_pic() {
        return main_pic;
    }

    public void setMain_pic(String main_pic) {
        this.main_pic = main_pic;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public String getStore_position() {
        return store_position;
    }

    public void setStore_position(String store_position) {
        this.store_position = store_position;
    }

    public String getStore_output() {
        return store_output;
    }

    public void setStore_output(String store_output) {
        this.store_output = store_output;
    }

    public String getStore_order() {
        return store_order;
    }

    public void setStore_order(String store_order) {
        this.store_order = store_order;
    }

    public String getStore_score() {
        return store_score;
    }

    public void setStore_score(String store_score) {
        this.store_score = store_score;
    }

    public String getStore_introduction() {
        return store_introduction;
    }

    public void setStore_introduction(String store_introduction) {
        this.store_introduction = store_introduction;
    }

    public String getStore_mainimg() {
        return store_mainimg;
    }

    public void setStore_mainimg(String store_mainimg) {
        this.store_mainimg = store_mainimg;
    }

    public String getStore_img() {
        return store_img;
    }

    public void setStore_img(String store_img) {
        this.store_img = store_img;
    }

    public String getStore_licence() {
        return store_licence;
    }

    public void setStore_licence(String store_licence) {
        this.store_licence = store_licence;
    }

    public String getStore_linkman() {
        return store_linkman;
    }

    public void setStore_linkman(String store_linkman) {
        this.store_linkman = store_linkman;
    }

    public String getStore_contact() {
        return store_contact;
    }

    public void setStore_contact(String store_contact) {
        this.store_contact = store_contact;
    }

    public String getStore_state() {
        return store_state;
    }

    public void setStore_state(String store_state) {
        this.store_state = store_state;
    }

    public String getStore_traffic() {
        return store_traffic;
    }

    public void setStore_traffic(String store_traffic) {
        this.store_traffic = store_traffic;
    }

    public String getOrder_distance() {
        return order_distance;
    }

    public void setOrder_distance(String order_distance) {
        this.order_distance = order_distance;
    }

    public String getStore_balance() {
        return store_balance;
    }

    public void setStore_balance(String store_balance) {
        this.store_balance = store_balance;
    }

    public String getStore_amount() {
        return store_amount;
    }

    public void setStore_amount(String store_amount) {
        this.store_amount = store_amount;
    }

    public String getStore_officeorder() {
        return store_officeorder;
    }

    public void setStore_officeorder(String store_officeorder) {
        this.store_officeorder = store_officeorder;
    }

    public String getStore_warning() {
        return store_warning;
    }

    public void setStore_warning(String store_warning) {
        this.store_warning = store_warning;
    }

    public String getStore_remittee() {
        return store_remittee;
    }

    public void setStore_remittee(String store_remittee) {
        this.store_remittee = store_remittee;
    }

    public String getStore_bankcard() {
        return store_bankcard;
    }

    public void setStore_bankcard(String store_bankcard) {
        this.store_bankcard = store_bankcard;
    }

    public String getStore_alipay() {
        return store_alipay;
    }

    public void setStore_alipay(String store_alipay) {
        this.store_alipay = store_alipay;
    }

    public String getStore_password() {
        return store_password;
    }

    public void setStore_password(String store_password) {
        this.store_password = store_password;
    }

    public String getStore_salescount() {
        return store_salescount;
    }

    public void setStore_salescount(String store_salescount) {
        this.store_salescount = store_salescount;
    }

    public String getStore_longitude() {
        return store_longitude;
    }

    public void setStore_longitude(String store_longitude) {
        this.store_longitude = store_longitude;
    }

    public String getStore_latitude() {
        return store_latitude;
    }

    public void setStore_latitude(String store_latitude) {
        this.store_latitude = store_latitude;
    }

    public String getStore_visitorall() {
        return store_visitorall;
    }

    public void setStore_visitorall(String store_visitorall) {
        this.store_visitorall = store_visitorall;
    }

    public String getStore_visitorcur() {
        return store_visitorcur;
    }

    public void setStore_visitorcur(String store_visitorcur) {
        this.store_visitorcur = store_visitorcur;
    }

    public String getStore_visitorlea() {
        return store_visitorlea;
    }

    public void setStore_visitorlea(String store_visitorlea) {
        this.store_visitorlea = store_visitorlea;
    }

    public String getStore_tel() {
        return store_tel;
    }

    public void setStore_tel(String store_tel) {
        this.store_tel = store_tel;
    }

    public String getStore_allorder() {
        return store_allorder;
    }

    public void setStore_allorder(String store_allorder) {
        this.store_allorder = store_allorder;
    }

    public String getStore_touxiang() {
        return store_touxiang;
    }

    public void setStore_touxiang(String store_touxiang) {
        this.store_touxiang = store_touxiang;
    }

    public String getStore_regtime() {
        return store_regtime;
    }

    public void setStore_regtime(String store_regtime) {
        this.store_regtime = store_regtime;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getStore_areanum() {
        return store_areanum;
    }

    public void setStore_areanum(String store_areanum) {
        this.store_areanum = store_areanum;
    }

    public String getStore_citycode() {
        return store_citycode;
    }

    public void setStore_citycode(String store_citycode) {
        this.store_citycode = store_citycode;
    }

    public String getMony_withdraw() {
        return mony_withdraw;
    }

    public void setMony_withdraw(String mony_withdraw) {
        this.mony_withdraw = mony_withdraw;
    }

    public String getScore_withdraw() {
        return score_withdraw;
    }

    public void setScore_withdraw(String score_withdraw) {
        this.score_withdraw = score_withdraw;
    }

    public String getTransport_id() {
        return transport_id;
    }

    public void setTransport_id(String transport_id) {
        this.transport_id = transport_id;
    }

    public String getPassenger_openid() {
        return passenger_openid;
    }

    public void setPassenger_openid(String passenger_openid) {
        this.passenger_openid = passenger_openid;
    }

    public String getAccumulate_money() {
        return accumulate_money;
    }

    public void setAccumulate_money(String accumulate_money) {
        this.accumulate_money = accumulate_money;
    }

    public String getAccumulate_score() {
        return accumulate_score;
    }

    public void setAccumulate_score(String accumulate_score) {
        this.accumulate_score = accumulate_score;
    }

    public String getStore_star() {
        return store_star;
    }

    public void setStore_star(String store_star) {
        this.store_star = store_star;
    }

    public String getTransport_start() {
        return transport_start;
    }

    public void setTransport_start(String transport_start) {
        this.transport_start = transport_start;
    }

    public Object getDistribution_id() {
        return distribution_id;
    }

    public void setDistribution_id(Object distribution_id) {
        this.distribution_id = distribution_id;
    }

    public int getComment_total() {
        return comment_total;
    }

    public void setComment_total(int comment_total) {
        this.comment_total = comment_total;
    }

    public String getGo_url() {
        return go_url;
    }

    public void setGo_url(String go_url) {
        this.go_url = go_url;
    }

    public List<String> getUserpic_arr() {
        return userpic_arr;
    }

    public void setUserpic_arr(List<String> userpic_arr) {
        this.userpic_arr = userpic_arr;
    }
}
