package com.creativeshare.sals.models;

public class Shipment_Send_Model {
    private static String date;
    private static String addreessf;
    private  static String adddresst;
    private static String desc;

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
}
