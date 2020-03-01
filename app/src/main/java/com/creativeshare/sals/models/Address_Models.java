package com.creativeshare.sals.models;

import java.io.Serializable;

public class Address_Models implements Serializable {
    private String message;
    private Address address;

    public String getMessage() {
        return message;
    }

    public Address getAddress() {
        return address;
    }

    public class Address implements Serializable {
        private int id;
        private double latitude;
        private double longitude;
        private String address;
        private String building_number;
        private String floor_number;
        private String flat_number;
        private String notes;
        private String address_type;
        private int is_primary;
        private int user_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public String getAddress() {
            return address;
        }

        public String getBuilding_number() {
            return building_number;
        }

        public String getFloor_number() {
            return floor_number;
        }

        public String getFlat_number() {
            return flat_number;
        }

        public String getNotes() {
            return notes;
        }

        public String getAddress_type() {
            return address_type;
        }

        public int getIs_primary() {
            return is_primary;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
