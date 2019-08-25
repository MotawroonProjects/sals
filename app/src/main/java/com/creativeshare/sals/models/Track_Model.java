package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Track_Model implements Serializable {
private List<AWBInfo> AWBInfo;

    public List<Track_Model.AWBInfo> getAWBInfo() {
        return AWBInfo;
    }

    public class AWBInfo implements Serializable {
        private String AWBNumber;
private ShipmentInfo ShipmentInfo;

        public String getAWBNumber() {
            return AWBNumber;
        }

        public AWBInfo.ShipmentInfo getShipmentInfo() {
            return ShipmentInfo;
        }

        public class ShipmentInfo implements Serializable {
            private OriginServiceArea OriginServiceArea;
            private DestinationServiceArea DestinationServiceArea;
private String ShipperName;
            public ShipmentInfo.OriginServiceArea getOriginServiceArea() {
                return OriginServiceArea;
            }

            public ShipmentInfo.DestinationServiceArea getDestinationServiceArea() {
                return DestinationServiceArea;
            }

            public String getShipperName() {
                return ShipperName;
            }

            public class OriginServiceArea implements Serializable {
                private String ServiceAreaCode;
                        private String Description;

                public String getServiceAreaCode() {
                    return ServiceAreaCode;
                }

                public String getDescription() {
                    return Description;
                }
            }
             public class  DestinationServiceArea implements Serializable {
                private String ServiceAreaCode;
                private String Description;

                 public String getServiceAreaCode() {
                     return ServiceAreaCode;
                 }

                 public String getDescription() {
                     return Description;
                 }
             }
        }
    }
}