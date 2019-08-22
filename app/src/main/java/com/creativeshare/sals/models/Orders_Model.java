package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Orders_Model implements Serializable {
private Orders orders;

    public Orders getOrders() {
        return orders;
    }

    public  class Orders  implements Serializable {
        private int current_page;
        private List<Data> data;

        public int getCurrent_page() {
            return current_page;
        }

        public List<Data> getData() {
            return data;
        }

        public class Data implements Serializable
        {
            private int id;
                private String from_company_name;
            private String to_company_name;
            private String from_address;
            private String to_address;
            private String from_city;
            private String to_city;
            private String from_postal_code;
            private String to_postal_code;
            private String from_country_code;
            private String to_country_code;
            private String from_country_name;
            private String to_country_name;
            private String from_person_name;
            private String to_person_name;
            private String from_phone_number;
            private String to_phone_number;
            private String from_phone_ext;
            private String to_phone_ext;
            private String from_email;
            private String to_email;
            private String desc;
                private int is_parcel;
                private int total_items;
                private int total_weight;
                private int status;
                private String shipment_date;
                private String awb_number;
                private String user_id;

            public int getId() {
                return id;
            }

            public String getFrom_company_name() {
                return from_company_name;
            }

            public String getTo_company_name() {
                return to_company_name;
            }

            public String getFrom_address() {
                return from_address;
            }

            public String getTo_address() {
                return to_address;
            }

            public String getFrom_city() {
                return from_city;
            }

            public String getTo_city() {
                return to_city;
            }

            public String getFrom_postal_code() {
                return from_postal_code;
            }

            public String getTo_postal_code() {
                return to_postal_code;
            }

            public String getFrom_country_code() {
                return from_country_code;
            }

            public String getTo_country_code() {
                return to_country_code;
            }

            public String getFrom_country_name() {
                return from_country_name;
            }

            public String getTo_country_name() {
                return to_country_name;
            }

            public String getFrom_person_name() {
                return from_person_name;
            }

            public String getTo_person_name() {
                return to_person_name;
            }

            public String getFrom_phone_number() {
                return from_phone_number;
            }

            public String getTo_phone_number() {
                return to_phone_number;
            }

            public String getFrom_phone_ext() {
                return from_phone_ext;
            }

            public String getTo_phone_ext() {
                return to_phone_ext;
            }

            public String getFrom_email() {
                return from_email;
            }

            public String getTo_email() {
                return to_email;
            }

            public String getDesc() {
                return desc;
            }

            public int getIs_parcel() {
                return is_parcel;
            }

            public int getTotal_items() {
                return total_items;
            }

            public int getTotal_weight() {
                return total_weight;
            }

            public int getStatus() {
                return status;
            }

            public String getShipment_date() {
                return shipment_date;
            }

            public String getAwb_number() {
                return awb_number;
            }

            public String getUser_id() {
                return user_id;
            }
        }

    }

}
