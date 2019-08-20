package com.creativeshare.sals.models;

import java.util.List;

public class Shipment_Send_Model {
    private static String date;
    private static String addreessf;
    private  static String adddresst;
    private static String desc;
    private static String cityf;
    private static String time;
    static List<String> widths;
    static List<String> hights;
    static List<String> lengths;
    static List<String> volumeweights;
    static List<String> wegights;
    static String price;
    static String day_number;
    private static String parcel;
    private static String name;
    private static String phone;
    private static String cityt;
    private static String fromcountrycode;

    public static String getFromcountrycode() {
        return fromcountrycode;
    }

    public static String getPrice() {
        return price;
    }

    public static void setPrice(String price) {
        Shipment_Send_Model.price = price;
    }

    public static String getDay_number() {
        return day_number;
    }

    public static void setDay_number(String day_number) {
        Shipment_Send_Model.day_number = day_number;
    }

    public static void setTime(String time) {
        Shipment_Send_Model.time = time;
    }

    public static List<String> getWidths() {
        return widths;
    }

    public static void setWidths(List<String> widths) {
        Shipment_Send_Model.widths = widths;
    }

    public static List<String> getHights() {
        return hights;
    }

    public static void setHights(List<String> hights) {
        Shipment_Send_Model.hights = hights;
    }

    public static List<String> getLengths() {
        return lengths;
    }

    public static void setLengths(List<String> lengths) {
        Shipment_Send_Model.lengths = lengths;
    }

    public static List<String> getVolumeweights() {
        return volumeweights;
    }

    public static void setVolumeweights(List<String> volumeweights) {
        Shipment_Send_Model.volumeweights = volumeweights;
    }

    public static List<String> getWegights() {
        return wegights;
    }

    public static void setWegights(List<String> wegights) {
        Shipment_Send_Model.wegights = wegights;
    }

    public static String getTime() {
        return time;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Shipment_Send_Model.date = date;
    }

    public static String getAddreessf() {
        return addreessf;
    }

    public static void setAddreessf(String addreessf) {
        Shipment_Send_Model.addreessf = addreessf;
    }

    public static String getAdddresst() {
        return adddresst;
    }

    public static void setAdddresst(String adddresst) {
        Shipment_Send_Model.adddresst = adddresst;
    }

    public static String getDesc() {
        return desc;
    }

    public static void setDesc(String desc) {
        Shipment_Send_Model.desc = desc;
    }

    public static void setCityf(String cityf) {
        Shipment_Send_Model.cityf=cityf;
    }

    public static String getCityf() {
        return cityf;
    }

    public static void settime(String time) {
        Shipment_Send_Model.time=time;
    }



    public static String getParcel() {
        return parcel;
    }

    public static void setParcel(String parcel) {
        Shipment_Send_Model.parcel = parcel;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Shipment_Send_Model.name = name;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        Shipment_Send_Model.phone = phone;
    }

    public static void setcityt(String to_city) {
        Shipment_Send_Model.cityt=to_city;
    }

    public static String getCityt() {
        return cityt;
    }

    public static void setcode(String short_name) {
        Shipment_Send_Model.fromcountrycode=short_name;
    }
}
