package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Ticket_Model implements Serializable {

       private String message;
       private List<Tickets> tickets;

    public String getMessage() {
        return message;
    }

    public List<Tickets> getTickets() {
        return tickets;
    }

    public class Tickets implements Serializable
        {
            private int id;
                private String email;
               private String desc;
               private int order_related;
                private int is_callable;
                private int is_read;
               private int status;
                private int order_id;
            private int order_type;
            private int definition_id;
            private int user_id;

            public int getId() {
                return id;
            }

            public String getEmail() {
                return email;
            }

            public String getDesc() {
                return desc;
            }

            public int getOrder_related() {
                return order_related;
            }

            public int getIs_callable() {
                return is_callable;
            }

            public int getIs_read() {
                return is_read;
            }

            public int getStatus() {
                return status;
            }

            public int getOrder_id() {
                return order_id;
            }

            public int getOrder_type() {
                return order_type;
            }

            public int getDefinition_id() {
                return definition_id;
            }

            public int getUser_id() {
                return user_id;
            }
        }
}
