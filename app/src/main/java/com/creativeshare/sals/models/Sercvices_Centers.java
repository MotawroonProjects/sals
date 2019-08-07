package com.creativeshare.sals.models;


import java.io.Serializable;
import java.util.List;

public class Sercvices_Centers implements Serializable {
    private List<Centers> centers;

    public List<Centers> getCenters() {
        return centers;
    }

    public class  Centers
    {
        private int id;
           private double latitude;
            private double longitude;
            private String mobile_num;
            private String  working_hours;
            private String created_at;
            private String updated_at;
            private String title;
            private String address;

        public int getId() {
            return id;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public String getMobile_num() {
            return mobile_num;
        }

        public String getWorking_hours() {
            return working_hours;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getTitle() {
            return title;
        }

        public String getAddress() {
            return address;
        }
    }
}
