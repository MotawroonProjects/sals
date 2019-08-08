package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Country_Model implements Serializable {
    private List<Countries> countries;

    public List<Countries> getCountries() {
        return countries;
    }

    public static class Countries implements Serializable
    {
        private int id;
            private String en_name;
        private String ar_name;

           private String code;
            private String created_at;
            private String updated_at;

        public Countries(String en_name, String ar_name) {
            this.en_name = en_name;
            this.ar_name = ar_name;
        }

        public int getId() {
            return id;
        }

        public String getEn_name() {
            return en_name;
        }

        public String getAr_name() {
            return ar_name;
        }

        public String getCode() {
            return code;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

}
