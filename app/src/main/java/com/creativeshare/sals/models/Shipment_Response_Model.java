package com.creativeshare.sals.models;

import java.io.Serializable;

public class Shipment_Response_Model implements Serializable {
    private Response Response;
    private String Piece;

    public String getPiece() {
        return Piece;
    }
    public Shipment_Response_Model.Response getResponse() {
        return Response;
    }

    public class  Response implements Serializable{
        private Status Status;

        public Shipment_Response_Model.Response.Status getStatus() {
            return Status;
        }

        public class  Status implements Serializable {
     private Condition Condition;

     public Status.Condition getCondition() {
         return Condition;
     }

     public class Condition {

                private String ConditionData;

                public String getConditionData() {
                    return ConditionData;
                }
            }}
    }
}
