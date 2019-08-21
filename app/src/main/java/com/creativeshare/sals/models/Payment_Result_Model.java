package com.creativeshare.sals.models;

import java.io.Serializable;

public class Payment_Result_Model implements Serializable {

        private String id;
    private String status;
           private double amount;
            private int fee;
    private String currency;
           private int refunded;
    private String refunded_at;
    private String description;
    private String amount_format;
    private String fee_format;
    private String refunded_format;
    private String invoice_id;
            private String ip;
            private String callback_url;
            private String created_at;
            private String updated_at;
            private Source source;
    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }

    public int getFee() {
        return fee;
    }

    public String getCurrency() {
        return currency;
    }

    public int getRefunded() {
        return refunded;
    }

    public String getRefunded_at() {
        return refunded_at;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount_format() {
        return amount_format;
    }

    public String getFee_format() {
        return fee_format;
    }

    public String getRefunded_format() {
        return refunded_format;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public String getIp() {
        return ip;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Source getSource() {
        return source;
    }

    public class  Source implements Serializable {
        private String type;
                private String company;
                private String name;
                private String number;
               private String message;
                private String transaction_url;

                public String getType() {
                    return type;
                }

                public String getCompany() {
                    return company;
                }

                public String getName() {
                    return name;
                }

                public String getNumber() {
                    return number;
                }

                public String getMessage() {
                    return message;
                }

                public String getTransaction_url() {
                    return transaction_url;
                }
            }
    }
