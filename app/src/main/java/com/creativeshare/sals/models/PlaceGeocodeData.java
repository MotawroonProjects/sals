package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class PlaceGeocodeData implements Serializable {

    private List<Geocode> results;

    public List<Geocode> getResults() {
        return results;
    }
    public class Geocode implements Serializable
    {
        private String formatted_address;
        private String place_id;
        private Geometry geometry;
private List<Address_components> address_components;
        public String getFormatted_address() {
            return formatted_address;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public List<Address_components> getAddress_components() {
            return address_components;
        }
    }

    public class Geometry implements Serializable
    {
        private Location location;

        public Location getLocation() {
            return location;
        }
    }
    public class Location implements Serializable
    {
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }
    public class Address_components implements Serializable
    {

        private String long_name;
           private String short_name;
            private List<String> types;

        public String getLong_name() {
            return long_name;
        }

        public String getShort_name() {
            return short_name;
        }

        public List<String> getTypes() {
            return types;
        }
    }
}
