package com.creativeshare.sals.models;

import java.io.Serializable;
import java.util.List;

public class Quote_Model implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable{
        private GetQuoteResponse GetQuoteResponse;

        public Data.GetQuoteResponse getGetQuoteResponse() {
            return GetQuoteResponse;
        }

        public class GetQuoteResponse implements Serializable{
            private Response  Response;
private BkgDetails BkgDetails;

            public Data.GetQuoteResponse.Response getResponse() {
                return Response;
            }

            public Data.GetQuoteResponse.BkgDetails getBkgDetails() {
                return BkgDetails;
            }

            public class Response implements Serializable{
                private ServiceHeader ServiceHeader;

                public Data.GetQuoteResponse.Response.ServiceHeader getServiceHeader() {
                    return ServiceHeader;
                }

                public class ServiceHeader implements Serializable{
                    private String MessageTime;
                            private String MessageReference;
                           private String SiteID;

                    public String getMessageTime() {
                        return MessageTime;
                    }

                    public String getMessageReference() {
                        return MessageReference;
                    }

                    public String getSiteID() {
                        return SiteID;
                    }
                }
            }
            public class BkgDetails implements Serializable{
                private OriginServiceArea OriginServiceArea;
private DestinationServiceArea DestinationServiceArea;
private List<QtdShp> QtdShp;
                public Data.GetQuoteResponse.BkgDetails.OriginServiceArea getOriginServiceArea() {
                    return OriginServiceArea;
                }

                public Data.GetQuoteResponse.BkgDetails.DestinationServiceArea getDestinationServiceArea() {
                    return DestinationServiceArea;
                }

                public List<Data.GetQuoteResponse.BkgDetails.QtdShp> getQtdShp() {
                    return QtdShp;
                }

                public class  OriginServiceArea  implements Serializable{
                    private String FacilityCode;
                            private String ServiceAreaCode;

                    public String getFacilityCode() {
                        return FacilityCode;
                    }

                    public String getServiceAreaCode() {
                        return ServiceAreaCode;
                    }
                }
                public class DestinationServiceArea implements Serializable{
                        private String FacilityCode;
                        private String ServiceAreaCode;

                    public String getFacilityCode() {
                        return FacilityCode;
                    }

                    public String getServiceAreaCode() {
                        return ServiceAreaCode;
                    }
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
                            public class QtdShpExChrg  implements Serializable{
                        private String SpecialServiceType;
                                private String LocalServiceType;
                                private String GlobalServiceName;
                                private String LocalServiceTypeName;
                                private String SOfferedCustAgreement;
                                private String ChargeCodeType;
                                private String CurrencyCode;
                                private String ChargeValue;
                                private String ChargeTaxAmount;
                                public class  ChargeTaxAmountDet implements Serializable {
                            private String TaxTypeRate;
                                    private String TaxTypeCode;
                                   private String TaxAmount;
                                    private String BaseAmount;
                        }
                        private List<QtdSExtrChrgInAdCur>  QtdSExtrChrgInAdCur;
                        public class QtdSExtrChrgInAdCur implements Serializable
                        {
                            private String ChargeValue;
                                private String ChargeTaxAmount;
                                private String CurrencyCode;
                                private String CurrencyRoleTypeCode;
                                private String ChargeTaxAmountDet;
                           private String TaxTypeRate;;
                                   private String TaxTypeCode;
                                    private String TaxAmount;
                                    private String BaseAmount;
                        }
                        }


                    }
                        private String PricingDate;

                    }
                }
            }
        }



