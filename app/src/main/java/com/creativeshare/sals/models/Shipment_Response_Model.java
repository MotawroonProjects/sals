package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Shipment_Response_Model implements Serializable {
    private Response Response;
    private String Piece;
private Pieces Pieces;
private Barcodes Barcodes;

    public Shipment_Response_Model.Pieces getPieces() {
        return Pieces;
    }

    public String getPiece() {
        return Piece;
    }
    public Shipment_Response_Model.Response getResponse() {
        return Response;
    }

    public Shipment_Response_Model.Barcodes getBarcodes() {
        return Barcodes;
    }

    public class Barcodes implements Serializable {
       private String AWBBarCode;
     private String OriginDestnBarcode;
     private String ClientIDBarCode;
     private String DHLRoutingBarCode;

     public String getAWBBarCode() {
         return AWBBarCode;
     }

     public String getOriginDestnBarcode() {
         return OriginDestnBarcode;
     }

     public String getClientIDBarCode() {
         return ClientIDBarCode;
     }

     public String getDHLRoutingBarCode() {
         return DHLRoutingBarCode;
     }
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
     public class  Pieces implements Serializable{
        private List<Piece> Piece;

         public List<Pieces.Piece> getPiece() {
             return Piece;
         }

         public class  Piece implements Serializable
        {
            private String PieceNumber;

                private String Depth;
                private String Width;
                private String Height;
               private String Weight;
                private String DimWeight;
private String LicensePlateBarCode;
            public String getPieceNumber() {
                return PieceNumber;
            }

            public String getDepth() {
                return Depth;
            }

            public String getWidth() {
                return Width;
            }

            public String getHeight() {
                return Height;
            }

            public String getWeight() {
                return Weight;
            }

            public String getDimWeight() {
                return DimWeight;
            }

            public String getLicensePlateBarCode() {
                return LicensePlateBarCode;
            }
        }
}}
