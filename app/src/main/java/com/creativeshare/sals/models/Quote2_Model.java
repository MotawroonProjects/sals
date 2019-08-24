package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Quote_Model implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }




    public class Data implements Serializable{
        private Response Response;

        public Response getResponse() {
            return Response;
        }
        public class  Response implements Serializable{
            private Response.Status Status;

            public Response.Status getStatus() {
                return Status;
            }

            public class  Status implements Serializable {
                private Response.Status.Condition Condition;

                public Response.Status.Condition getCondition() {
                    return Condition;
                }

                public class Condition {

                    private String ConditionData;

                    public String getConditionData() {
                        return ConditionData;
                    }
                }}

        }

        private GetQuoteResponse GetQuoteResponse;

        public Data.GetQuoteResponse getGetQuoteResponse() {
            return GetQuoteResponse;
        }

        public class GetQuoteResponse implements Serializable{
private BkgDetails BkgDetails;


            public Data.GetQuoteResponse.BkgDetails getBkgDetails() {
                return BkgDetails;
            }

            public class BkgDetails implements Serializable{
private QtdShp QtdShp;


                public Data.GetQuoteResponse.BkgDetails.QtdShp getQtdShp() {
                    return QtdShp;
                }

                public class QtdShp implements Serializable {

                        private String GlobalProductCode;
                            private String LocalProductCode;
                            private String ProductShortName;
                            private String LocalProductName;
                            private String NetworkTypeCode;
                            private String POfferedCustAgreement;
                            private String TransInd;
                            private String PickupDate;
                            private String PickupCutoffTime;
                            private String BookingTime;
                            private String CurrencyCode;
                            private String ExchangeRate;
                           private String WeightCharge;
                            private String WeightChargeTax;
                           private String TotalTransitDays;
                            private String PickupPostalLocAddDays;
                            private String DeliveryPostalLocAddDays;
                            public class PickupNonDHLCourierCode implements Serializable{

                    }
                        public class  DeliveryNonDHLCourierCode implements Serializable {

                    }
                        private String DeliveryDate;
                            private String DeliveryTime;
                            private String DimensionalWeight;
                            private String WeightUnit;
                            private String PickupDayOfWeekNum;
                            private String DestinationDayOfWeekNum;

                    public String getGlobalProductCode() {
                        return GlobalProductCode;
                    }

                    public String getLocalProductCode() {
                        return LocalProductCode;
                    }

                    public String getProductShortName() {
                        return ProductShortName;
                    }

                    public String getLocalProductName() {
                        return LocalProductName;
                    }

                    public String getNetworkTypeCode() {
                        return NetworkTypeCode;
                    }

                    public String getPOfferedCustAgreement() {
                        return POfferedCustAgreement;
                    }

                    public String getTransInd() {
                        return TransInd;
                    }

                    public String getPickupDate() {
                        return PickupDate;
                    }

                    public String getPickupCutoffTime() {
                        return PickupCutoffTime;
                    }

                    public String getBookingTime() {
                        return BookingTime;
                    }

                    public String getCurrencyCode() {
                        return CurrencyCode;
                    }

                    public String getExchangeRate() {
                        return ExchangeRate;
                    }

                    public String getWeightCharge() {
                        return WeightCharge;
                    }

                    public String getWeightChargeTax() {
                        return WeightChargeTax;
                    }

                    public String getTotalTransitDays() {
                        return TotalTransitDays;
                    }

                    public String getPickupPostalLocAddDays() {
                        return PickupPostalLocAddDays;
                    }

                    public String getDeliveryPostalLocAddDays() {
                        return DeliveryPostalLocAddDays;
                    }

                    public String getDeliveryDate() {
                        return DeliveryDate;
                    }

                    public String getDeliveryTime() {
                        return DeliveryTime;
                    }

                    public String getDimensionalWeight() {
                        return DimensionalWeight;
                    }

                    public String getWeightUnit() {
                        return WeightUnit;
                    }

                    public String getPickupDayOfWeekNum() {
                        return PickupDayOfWeekNum;
                    }

                    public String getDestinationDayOfWeekNum() {
                        return DestinationDayOfWeekNum;
                    }
                }

                    }
                }
            }
        }



