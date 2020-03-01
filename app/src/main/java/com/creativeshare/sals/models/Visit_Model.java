package com.creativeshare.sals.models;

import java.io.Serializable;

public class Visit_Model implements Serializable {

    private String message;

    public class Record implements Serializable {
        private int id;
        private int typel;
        private int count;
        private String date;
        private String created_at;
        private String updated_at;

    }
}
