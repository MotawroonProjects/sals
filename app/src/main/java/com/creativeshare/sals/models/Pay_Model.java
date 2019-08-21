package com.creativeshare.sals.models;

import java.io.Serializable;

public class Pay_Model implements Serializable {

        private int amount;
private Source source;
private String callback_url;

    public double getAmount() {
        return amount;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public static class Source implements Serializable{
        private String type;
                private String name;
                private String number;
                private int cvc;
                private int month;
                private int year;

                public void setType(String type) {
                    this.type = type;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public void setCvc(int cvc) {
                    this.cvc = cvc;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }

}
