package com.hybris.easyjet.config.constants;

/**
 * Created by tejaldudhale on 23/06/2017.
 */
public class CommonConstants {
    //*****CHANNEL*****
    public static final String DIGITAL_CHANNEL = "Digital";
    public static final String PUBLIC_API_MOBILE_CHANNEL = "PublicApiMobile";
    public static final String PUBLIC_API_B2B_CHANNEL = "PublicApiB2B";
    public static final String AD_CHANNEL = "ADAirport";
    public static final String AD_CUSTOMER_SERVICE = "ADCustomerService";
    //*****JOURNEY TYPE*****
    public static final String OUTBOUND = "outbound";
    public static final String INBOUND = "inbound";
    public static final String SINGLE = "single";
    public static final String OUTBOUND_INBOUND  = "outbound/inbound";
    //*****PRODUCT*****
    public static final String FARE_PRODUCT = "FARE_PRODUCT";
    public static final String INFANTONLAP_PRODUCT = "InfantOnLapProduct";
    //*****FARE TYPE*****
    public static final String STANDARD = "Standard";
    public static final String FLEXI = "Flexi";
    public static final String STAFF = "Staff";
    public static final String STANDBY = "Standby";
    public static final String STAFF_STANDARD = "StaffStandard";
    //*****BOOKING TYPE*****
    public static final String BUSINESS = "BUSINESS";
    //*****NAME ATTRIBUTES*****
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String TITLE = "title";
    public static final String MIDDLENAME = "middlename";
    //*****FLIGHT STATUS*****
    public static final String AVAILABLE = "AVAILABLE";
    //*****COMMON*****
    public static final String SHOULD_NOT = "should not";
    public static final String SHOULD = "should";
    public static final String DEPARTURE_THRESHOLD_NAME_CHANGE = "thresholdForNameChangeBasedOnDeparture";
    public static final String ONE = "1";
    public static final String INCORRECT = "incorrect";
    public static final String INVALID = "invalid";
    public static final String GROUP = "Group";
    public static final String APIS = "apis";
    public static final String DCS = "DCS";
    public static final String INFOL = "INF-OL-BP";
    public static final int FLIGHT_KEY_LENGTH = 18;
    //*****PAYMENT-TYPES*****
    public static final String COMBINATION = "combination";
    public static final String DEBIT = "debit";
    public static final String CREDIT = "credit";
    public static final String DEBIT_CARD = "debit card";
    public static final String CREDIT_CARD = "credit card";
    public static final String CREDITFILEFUND = "creditfilefund";
    public static final String CREDITFILE = "creditfile";
    public static final String ELV = "elv";
    public static final String CASH = "cash";
    public static final String VOUCHER = "voucher";
    //*****PAYMENT-RELATED*****
    public static final String REFUND = "refund";
    public static String REFUND_PENDING = "REFUND_PENDING";
    public static final String CARD = "card";
    public static final String CREDITFILEFUNDREFUND = "CREDITFILEFUND";
    public static final String CARDREFUND = "CARD";
    //*****PASSENGER*****
    public static final String ONE_ADULT = "1 Adult";
    //*****ERROR MESSAGE*****
    public static final String SEATMAP_ERROR = "Error getting a valid seat map";
    public static final String NO_SEAT_AVAILABLE = "No seat are available from seating service for the desired type ";
    public static final String NO_FARE_AVAILABLE = "No stock available for STANDBY fare";
    //*****PASSENGER TYPE******
    public static final String ADULT = "adult";
    public static final String CHILD = "child";
    public static final String INFANT = "infant";
    public static final String INFANT_ON_LAP = "InfantOnLap";
    //*****PASSENGER TITLE******
    public static final String MR = "mr";
    //*****ICTS MESSAGE STATUS REFERENCE DATA******
    public static final String DENY_TRAVEL = "3000";
    public static final String MANUAL_ICTS_DENY_TRAVEL = "3000";
    public static final String DENY_TRAVEL_MESSAGE_STATUS_CODE = "1";
    public static final String MANUAL_ICTS_NO_FLY = "999";
    public static final String NO_FLY_MESSAGE_STATUS_CODE = "1";
    public static final String BODY_MESSAGE_STATUS_CODE = "1";
    //*****CURRENCY*****
    public static final String GBP = "GBP";
    //*****TAG******
    public static final String NEGATIVE_SCENARIO = "@negative";
    //allowed cap for oubound date from current date
    public static final int NUMBER_OF_HOURS = 2;
//    *****CREDIT CARD FEE ******
    public static final double CREDITCARD_FEE_PERCENTAGE = 0.05;
    //generic
    public static final String COMPLETED = "COMPLETED";
    public static final String LEISURE = "LEISURE";
    public static final String EMPTY = "empty";
    public static final String BLANK = "blank";
    public static final String WORLDWIDE = "WORLDWIDE";
    // DEFAULT PRODUCTS
    public static final String DEFAULT_HOLD_BAG_PRODUCT = "23kgbag";
    public static final String DEFAULT_HOLD_BAG_PRODUCT_NAME = "Hold Luggage 23kg";
    public static final String FIFTEEN_KG_HOLD_BAG_PRODUCT = "15kgbag";
    public static final String DEFAULT_EXCESS_WEIGHT_PRODUCT = "3kgextraweight";
    public static final String DEFAULT_SPORT_ITEM_PRODUCT = "Snowboard";
    public static final String DEFAULT_SPORT_ITEM_PRODUCT_NAME = "Snowboard";
    // CAR PRODUCT
    public static final String CAR_HIRES = "carHires";
    public static final String TOTAL_CAR_HIRE_AMOUNT = "totalCarHireAmount";
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String REPLACE = "replace";
    public static final String ADD = "add";
    //*****PROMOTION*****
    public static final String PUBLISHED_ENUM="PUBLISH";
    public enum BookingType {
        ;
        public static final String BUSINESS = "BUSINESS";
        public static final String STAFF = "STAFF";
        public static final String STANDARD_CUSTOMER = "STANDARD_CUSTOMER";
        public static final String SERIES_SEATING = "SERIES_SEATING";
    }

}
