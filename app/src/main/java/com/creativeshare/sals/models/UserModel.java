package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {


        private String message;
        private User user;
        private List<Addresses> addresses;
private String token;
    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public List<Addresses> getAddresses() {
        return addresses;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public  class User implements Serializable {
        private int id;
               private String first_name;
                private String last_name;
              private String mobile_code;
               private String mobile_number;
                private String email;
               private String is_logged;
               private String is_registered;
               private String software_type;
                private String verification;
                private String deleted_at;
               private int is_notifiable;
                private String email_verified_at;
                private String created_at;
               private String updated_at;

        public int getId() {
            return id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getMobile_code() {
            return mobile_code;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public String getEmail() {
            return email;
        }

        public String getIs_logged() {
            return is_logged;
        }

        public String getIs_registered() {
            return is_registered;
        }

        public String getSoftware_type() {
            return software_type;
        }

        public String getVerification() {
            return verification;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public int getIs_notifiable() {
            return is_notifiable;
        }

        public String getEmail_verified_at() {
            return email_verified_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
       public class Addresses implements Serializable{

       }

}
