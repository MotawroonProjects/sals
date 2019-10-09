package com.creativeshare.sals.models;

import java.util.List;

public class Move_Data_Model {
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
    private static String tocountrycode;

    private static String sadad;
    private static String postalf;
    private static String countryf;
    private static String countryt;

    private static String postalt;
    private static String emailt;
    private static Shipment_Response_Model shipment_response_model;
    private static String cityfe;
    private static String cityte;
    private static String phonecodeto;

    public static String getTocountrycode() {
        return tocountrycode;
    }

    public static void setTocountrycode(String tocountrycode) {
        Move_Data_Model.tocountrycode = tocountrycode;
    }

    public static String getPhonecodeto() {
        return phonecodeto;
    }

    public static String getCountryt() {
        return countryt;
    }

    public static void setCountryt(String countryt) {
        Move_Data_Model.countryt = countryt;
    }

    private static List<Help_Cat_Model.Categories.Faqs> faqs;

    public static List<Help_Cat_Model.Categories.Faqs> getFaqs() {
        return faqs;
    }

    public static String getEmailt() {
        return emailt;
    }

    public static String getPostalt() {
        return postalt;
    }

    public static String getCountryf() {
        return countryf;
    }

    public static String getPostalf() {
        return postalf;
    }

    public static String getSadad() {
        return sadad;
    }

    public static String getFromcountrycode() {
        return fromcountrycode;
    }

    public static String getPrice() {
        return price;
    }

    public static void setPrice(String price) {
        Move_Data_Model.price = price;
    }

    public static String getDay_number() {
        return day_number;
    }

    public static void setDay_number(String day_number) {
        Move_Data_Model.day_number = day_number;
    }

    public static void setTime(String time) {
        Move_Data_Model.time = time;
    }

    public static List<String> getWidths() {
        return widths;
    }

    public static void setWidths(List<String> widths) {
        Move_Data_Model.widths = widths;
    }

    public static List<String> getHights() {
        return hights;
    }

    public static void setHights(List<String> hights) {
        Move_Data_Model.hights = hights;
    }

    public static List<String> getLengths() {
        return lengths;
    }

    public static void setLengths(List<String> lengths) {
        Move_Data_Model.lengths = lengths;
    }

    public static List<String> getVolumeweights() {
        return volumeweights;
    }

    public static void setVolumeweights(List<String> volumeweights) {
        Move_Data_Model.volumeweights = volumeweights;
    }

    public static List<String> getWegights() {
        return wegights;
    }

    public static void setWegights(List<String> wegights) {
        Move_Data_Model.wegights = wegights;
    }

    public static String getTime() {
        return time;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Move_Data_Model.date = date;
    }

    public static String getAddreessf() {
        return addreessf;
    }

    public static void setAddreessf(String addreessf) {
        Move_Data_Model.addreessf = addreessf;
    }

    public static String getAdddresst() {
        return adddresst;
    }

    public static void setAdddresst(String adddresst) {
        Move_Data_Model.adddresst = adddresst;
    }

    public static String getDesc() {
        return desc;
    }

    public static void setDesc(String desc) {
        Move_Data_Model.desc = desc;
    }

    public static void setCityf(String cityf) {
        Move_Data_Model.cityf=cityf;
    }

    public static String getCityf() {
        return cityf;
    }

    public static void settime(String time) {
        Move_Data_Model.time=time;
    }



    public static String getParcel() {
        return parcel;
    }

    public static void setParcel(String parcel) {
        Move_Data_Model.parcel = parcel;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Move_Data_Model.name = name;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        Move_Data_Model.phone = phone;
    }

    public static void setcityt(String to_city) {
        Move_Data_Model.cityt=to_city;
    }

    public static String getCityt() {
        return cityt;
    }

    public static void setcode(String short_name) {
        Move_Data_Model.fromcountrycode=short_name;
    }

    public static void setcredit(String sadad) {
        Move_Data_Model.sadad=sadad;
    }


    public static void setpostalf(String postal_code) {
        Move_Data_Model.postalf=postal_code;
    }

    public static void setcountryf(String long_name) {
        Move_Data_Model.countryf=long_name;
    }

    public static void setpostalt(String postal_code) {
        Move_Data_Model.postalt=postal_code;
    }

    public static void setemailt(String email) {
        Move_Data_Model.emailt=email;
    }

    public static void setshipment(Shipment_Response_Model body) {
        Move_Data_Model.shipment_response_model=body;
    }

    public static Shipment_Response_Model getShipment_response_model() {
        return shipment_response_model;
    }

    public static void setcityfe(String en_name) {
        Move_Data_Model.cityfe=en_name;
    }

    public static String getCityfe() {
        return cityfe;
    }

    public static String getCityte() {
        return cityte;
    }

    public static void setCityte(String cityte) {
        Move_Data_Model.cityte = cityte;
    }

    public static void setQuestions(List<Help_Cat_Model.Categories.Faqs> faqs) {
        Move_Data_Model.faqs=faqs;
    }

    public static void settophonecode(String phone_code) {
        Move_Data_Model.phonecodeto=phone_code;
    }
}
