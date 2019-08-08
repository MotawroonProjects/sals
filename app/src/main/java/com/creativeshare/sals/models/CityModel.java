package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class CityModel implements Serializable {
private List<Cities> cities;

    public List<Cities> getCities() {
        return cities;
    }

    public static class Cities implements Serializable
    {
        private int id;
            private String ar_name;
            private String en_name;
            private int province_id_fk;
            private int country_id_fk;

        public Cities(String ar_name, String en_name) {
            this.ar_name = ar_name;
            this.en_name = en_name;
        }

        public int getId() {
            return id;
        }

        public String getAr_name() {
            return ar_name;
        }

        public String getEn_name() {
            return en_name;
        }

        public int getProvince_id_fk() {
            return province_id_fk;
        }

        public int getCountry_id_fk() {
            return country_id_fk;
        }
    }
}