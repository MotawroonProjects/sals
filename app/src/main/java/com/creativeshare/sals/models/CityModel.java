package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class CityModel implements Serializable {
    private List<postal_codes> postal_codes;

    public List<CityModel.postal_codes> getPostal_codes() {
        return postal_codes;
    }

    public static class postal_codes implements Serializable {
        private int id;
        private String city;
        private int province_id_fk;
        private int country_id_fk;
        private String postal_code;
        private String postal_one;
        private String postal_two;
        private String code_two;

        public String getPostal_code() {
            return postal_code;
        }

        public postal_codes(String en_name) {
            this.city = en_name;
        }

        public int getId() {
            return id;
        }

        public String getCity() {
            return city;
        }

        public String getPostal_one() {
            return postal_one;
        }

        public String getPostal_two() {
            return postal_two;
        }

        public String getCode_two() {
            return code_two;
        }

        public int getProvince_id_fk() {
            return province_id_fk;
        }

        public int getCountry_id_fk() {
            return country_id_fk;
        }
    }
}