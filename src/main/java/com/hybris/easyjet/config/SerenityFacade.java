package com.hybris.easyjet.config;

import com.hybris.easyjet.TenantBeanFactoryPostProcessor;
import com.hybris.easyjet.config.constants.CommonConstants;
import com.hybris.easyjet.database.hybris.models.*;
import com.hybris.easyjet.fixture.hybris.invoke.HybrisHeaders;
import net.serenitybdd.core.Serenity;
import org.apache.commons.collections4.CollectionUtils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import static com.hybris.easyjet.config.SerenityFacade.DataKeys.SESSION;

/**
 * This class is intended to manage the access to the serenity session.
 * It provides methods with generic return type to get and set data, and methods to check parameters existence
 */
@Component
public final class SerenityFacade {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
    private static final Map<String, Object> sessionData = new HashMap<>();
    private static final Pattern GUID_FORMAT = Pattern.compile("^[A-Fa-f0-9]{8}-([A-Fa-f0-9]{4}-){3}[A-Fa-f0-9]{12}$");
    private static final Pattern CURRENCY_FORMAT = Pattern.compile("^[A-Z]{3}$");

    public DataFactory dataFactory = new DataFactory();

    public static SerenityFacade getTestDataFromSpring() {
        return (SerenityFacade) TenantBeanFactoryPostProcessor.getFactory().getBean("serenityFacade");
    }

    @SuppressWarnings("unchecked")
    public void storeTestData() {
        EnumMap<DataKeys, SessionList> oldSession = new EnumMap<>(DataKeys.class);
        for (Map.Entry<Object, Object> entry : Serenity.getCurrentSession().entrySet()) {
            SessionList<Object> value = new SessionList((SessionList) entry.getValue());
            oldSession.put((DataKeys) entry.getKey(), value);
        }
        Serenity.setSessionVariable(SESSION).to(oldSession);
    }

    @SuppressWarnings("unchecked")
    public void restoreTestData() {
        if (!Serenity.hasASessionVariableCalled(SESSION)) {
            throw new IllegalArgumentException("No session stored");
        }
        EnumMap<DataKeys, SessionList> oldSession = Serenity.sessionVariableCalled(SESSION);
        Serenity.initializeTestSession();
        for (Map.Entry<DataKeys, SessionList> entry : oldSession.entrySet()) {
            SessionList<Object> value = entry.getValue();
            Serenity.setSessionVariable(entry.getKey()).to(value);
        }
    }

    public void clearData() {
        Serenity.initializeTestSession();
    }

    public void clearData(DataKeys key) {
        Serenity.setSessionVariable(key).to(null);
    }

    public <T> void setData(DataKeys key, T value) {
        if (key.isNotSameTypeOf(value)) {
            throw new IllegalArgumentException("Tried to store invalid value in testData: cannot assign " + value.toString() + " to " + key.toString());
        }
        SessionList<T> list;
        if (keyExist(key)) {
            list = Serenity.sessionVariableCalled(key);
        } else {
            list = new SessionList<>();
        }
        Serenity.setSessionVariable(key).to(list.push(value));
    }

    public <T> void setData(DataKeys key, T value, String position) {
        if (key.isNotSameTypeOf(value)) {
            throw new IllegalArgumentException("Tried to store invalid value in testData: cannot assign " + value.toString() + " to " + key.toString());
        }
        SessionList<T> list;
        if (keyExist(key)) {
            list = Serenity.sessionVariableCalled(key);
        } else {
            list = new SessionList<>();
        }
        Serenity.setSessionVariable(key).to(list.push(value, position));
    }

    public <T> T getData(DataKeys key) {
        if (keyExist(key)) {
            SessionList<T> list = Serenity.sessionVariableCalled(key);
            return list.get(0);
        } else {
            return null;
        }
    }

    public <T> T getData(DataKeys key, int index) {
        if (keyExist(key)) {
            SessionList<T> list = Serenity.sessionVariableCalled(key);
            return list.get(index);
        } else {
            return null;
        }
    }

    public <T> T getData(DataKeys key, String position) {
        if (keyExist(key)) {
            SessionList<T> list = Serenity.sessionVariableCalled(key);
            return list.get(position);
        } else {
            return null;
        }
    }

    public SessionList getDataList(DataKeys key) {
        return Serenity.sessionVariableCalled(key);
    }

    public boolean keyExist(DataKeys key) {
        return Serenity.hasASessionVariableCalled(key);
    }

    public boolean keyNotExist(DataKeys key) {
        return !Serenity.hasASessionVariableCalled(key);
    }

    public boolean dataExist(DataKeys key) {
        return keyExist(key) && Objects.nonNull(getData(key));
    }

    public boolean dataExist(DataKeys key, String position) {
        return keyExist(key) && Objects.nonNull(getData(key, position));
    }

    public boolean dataNotExist(DataKeys key) {
        return keyNotExist(key) || Objects.isNull(getData(key));
    }

//    --------------------

//    ----- elements with default -----


    @Deprecated
    public void cleanStoredData() {
        sessionData.clear();
    }

    public enum DataKeys {
        // common data

        CHANNEL(String.class),
        HEADERS(HybrisHeaders.HybrisHeadersBuilder.class),
        SERVICE(Object.class),
        XTEST(Object.class),
        KEEP_JSESSION(String.class),
        PARENT_TYPE_CODE(String.class),

        //Refund
        REFUND_PRIMARY_REASON_NAME(Object.class),
        REFUND_PRIMARY_REASON_CODE(Object.class),
        CREDITFILE_BALANCE(Double.class),
        ORIGINAL_CREDITFILE_BALANCE(Double.class),

        // deal data
        DEALS(Object.class),
        APPLICATION_ID(Object.class),
        OFFICE_ID(Object.class),
        CORPORATE_ID(Object.class),
        //TODO quantity of what??
        QUANTITY(Object.class),

        // getSector data
        USABLE_SECTORS(Object.class),
        SECTOR(String.class),

        // getFlights data
        FIND_FLIGHT_RANDOM_SECTOR(Boolean.class),
        GET_FLIGHT_SERVICE(Object.class),
        PASSENGER_MIX(Object.class),
        PASSENGERS(Object.class),
        // insurance code
        POLICY_CODE(Object.class),
        // policy entry number
        POLICY_ORDER_ENTRY_NUMBER(Object.class),
        //Insurance Amount
        INSURANCE_AMOUNT(Double.class),
        //TODO this is useless; passenger total is stored in passengers object
        PASSENGERS_TOTAL(Object.class),
        // field inside disruption -> find flight
        NUM_OF_PASSENGER(Object.class),
        NUM_ADULT(String.class),
        NUM_CHILD(String.class),
        NUM_INFANT(String.class),
        // list of option(Object.class), field inside disruption -> find flight
        // transfer key
        TRANSFER_KEY(Object.class),
        REASON_CODE(Object.class),
        FLIGHT_KEY_TRANSFER(Object.class),
        FLIGHT_KEYS_ALREADY_TRANSFERED(Object.class),
        FLIGHT_LEVEL_OVERRIDE(Object.class),
        TRANSFER_LEVEL_OVERRIDE(Object.class),
        OPTIONS(Object.class),
        PASSENGERS_TO_TRANSFER(Object.class),
        DISRUPTION_REQUIRED_APIS_SECTOR(Object.class),
        SUBMIT_APIS_VOLUNTARY(Object.class),
        BOOKING_BASKET_MAP(Object.class),
        CUSTOM_ROUTE(Object.class),
        // flight number(Object.class), filed inside disruption -> find flight
        FLIGHT_NUMBER(Object.class),
        FARE_TYPE(Object.class),
        IS_STAFF(Object.class),
        ORIGIN(Object.class),
        DESTINATION(Object.class),
        IS_APIS_SECTOR(Boolean.class),
        OUTBOUND_DATE(Object.class),
        INDIRECT_OUTBOUND(Object.class),
        ARRIVAL_DATE_TIME(Object.class),
        DEPARTURE_DATE_TIME(Object.class),
        INBOUND_DATE(Object.class),
        FLEXIBLE_DAYS(Object.class),
        CURRENCY(Object.class),
        JOURNEY(Object.class),
        OUTBOUND_JOURNEY(Object.class),
        INBOUND_JOURNEY(Object.class),
        OUTBOUND_FLIGHT(Object.class),
        INBOUND_FLIGHT(Object.class),
        FIND_FLIGHT_EXCLUDE_ADMIN_FEE(Boolean.class),
        FIND_FLIGHT_GROUP_BOOKING(Boolean.class),
        ROUTEPAIR(Object.class),
        ORIGINAL_ADMIN_FEE(Double.class),
        IS_ADD_APIS(Boolean.class),
        FIND_FLIGHT_CHANGED_FLIGHT_KEY(String.class),
        FIND_FLIGHT_CHANGE_BOOKING_ID(String.class),
        FIND_FLIGHT_CHANGE_PASSENGERS_IDS(String.class, true),
        FIND_FLIGHT_INDIRECT(Boolean.class),

        // addFlight data
        JOURNEY_TYPE(String.class),
        ADD_FLIGHT_OVERRIDE_WARNING(Boolean.class),
        OLD_BASKET(Object.class), // use to check change basket content
        NEW_BASKET(Object.class),
        BASKET_SERVICE(Object.class),
        BASKET_ID(Object.class),
        PASSENGER_ID(Object.class),
        FLIGHT_KEY(Object.class),
        FLIGHT_KEYS(List.class),
        OUTBOUND_FLIGHT_KEY(Object.class),
        INBOUND_FLIGHT_KEY(Object.class),
        //TODO what's this? what's the difference with flightKey
        NEW_FLIGHT_KEY(Object.class),
        BUNDLE(Object.class),
        PASSENGER_CODES(Object.class),
        PASSENGER_TYPE(Object.class),
        RESPONSIBLE_ADULT_LIST(Object.class),
        IS_ALREADY_ALLOCATED_SEAT(Object.class),
        SAVED_PASSENGER_CODE(Object.class),
        OLD_SAVED_PASSENGER_CODE(Object.class),
        SAVED_PASSENGER_SIZE(Object.class),
        SAME_PASSENGER_NOT_LINKING(Boolean.class),
        STORE_SAVED_PASSENGER_CODE(Object.class),
        //TODO infant on lap is a passenger; why it's defined separately?
        INFANT_ON_LAP_ID(Object.class),
        // Seat map data
        EXTRA_LEGROOM_BASEPRICE(Object.class),
        UPFRONT_BASEPRICE(Object.class),
        STANDARD_BASEPRICE(Object.class),
        CHECKIN_ALL(Boolean.class),
        IS_DANGEROUS_GOODS_ACCEPTED(Boolean.class),
        CHECKIN_OVERRIDE(Boolean.class),
        LOCK_LEVEL(String.class),
        BASKET_TOTAL_AMOUNT(Double.class),

        // convertBasketCurrency
        CONVERT_CURRENCY(CurrencyModel.class),

        //recalculatePrices data
        PERSIST_FLAG(Object.class),

        TOTAL_AMOUNT_CREDIT_CARD(Object.class),

        // add hold items service
        ADD_HOLD_BAG_SERVICE(Object.class),
        HOLD_BAG_PRODUCT(Object.class),
        HOLD_BAG_NAME(Object.class),
        HOLD_BAG_PRICE(Object.class),
        EXCESS_WEIGHT_PRODUCT(Object.class),
        EXCESS_WEIGHT_NAME(String.class),
        EXCESS_WEIGHT_QUANTITY(Object.class),
        EXCESS_WEIGHT_PRICE(Object.class),
        ADD_SPORTS_ITEM_SERVICE(Object.class),
        SPORT_EQUIPMENT_PRODUCT(Object.class),
        SPORT_EQUIPMENT_NAME(Object.class),
        SPORT_EQUIPMENT_PRICE(Object.class),
        PROMOTION_CODE(Object.class),
        STOCK(Object.class),
        NO_OF_CABIN_ITEMS(Object.class),
        ADD_SPORT_EQ(Object.class),
        HOLD_BAG_INDEX(String.class),

        // remove hold items service
        PRODUCT_ORDER_ENTRY_NUMBER(Object.class),

        // add hotel service
        ADD_HOTEL_PRODUCT_SERVICE(Object.class),
        LEAD_PASSENGER_ID(Object.class),

        // price override service
        PRICE_OVERRIDE_CODE(String.class),
        IS_APPLICABLE_TO_ALL_PASSENGER(Object.class),
        PRODUCT_CODE(Object.class),
        OVERRIDE_TOTAL_AMOUNT(Object.class),
        FEE_CHARGE_CODE(Object.class),
        COMMENT(Object.class),
        REMOVE(String.class),

        ADD_SEAT_ALL_PASSENGER(Boolean.class), // if is false it will add seat only on first
        ADD_SEAT_REMAINING_PASSENGER(Boolean.class),
        CHANGE_SEAT_ALL_PASSENGER(Boolean.class),
        SEAT_TYPE(String.class),

        // paymentMethods data
        ALLOWED_PAYMENT_METHODS(Object.class),

        // booking data
        BOOKING_COMMENT_CODE(Object.class),
        BOOKING_DOCUMENT_TYPE(Object.class),
        BOOKING_DOCUMENT_OUTPUT(Object.class),
        BOOKING_DATE(Object.class),
        BOOKING_TOTAL_AMOUNT(Double.class),
        BOOKING_SUBTOTAL_AMOUNT(Double.class),

        // commitBooking data
        COMMIT_BOOKING_SERVICE(Object.class),
        BOOKING_REASON(String.class),
        BASKET_CONTENT(Object.class),
        BOOKING_ID(Object.class),
        BOOKING_IDS(List.class),
        BOOKING_STATUS(Object.class),
        BOOKING_TYPE(Object.class),
        PREFERRED_LANGUAGE(Object.class),

        EXISTING_GUEST_BOOKING_ID(Object.class),
        NUMBER_OF_BOOKINGS(Object.class),

        // getBooking data
        GET_BOOKING_SERVICE(Object.class),
        GET_BOOKING_RESPONSE(Object.class),
        //TODO what is this needed for?
        CREDIT_CARD_FEE(Object.class),


        // getRefundPaymentMethod data
        PAYMENT_TRANSACTION_ID(Object.class),
        REFUND_AMOUNT(Object.class),
        ALTERNATIVE_PAYMENT_METHODS_FOR_REFUND(Object.class),
        // in order to store saved payment method during commit booking and use it
        STORE_SAVED_PAYMENT_METHOD(Boolean.class),
        USE_SAVE_PAYMENT_METHOD(Boolean.class),
        // resendVoucher data
        VOUCHER_EMAIL(Object.class),

        // cancelBooking data
        REFUND_AND_FEES(Object.class),
        CANCELLATION_AMOUNT(Double.class),

        // manageCompensation data
        COMPENSATION_REQUEST_BODY(Object.class),

        // findBooking data
        BOOKING_DETAILS(BookingDetailsModel.class),

        // agent data
        AGENT_ID(Object.class),
        EMPLOYEE_ID(Object.class),
        AGENT_ACCESS_TOKEN(String.class, GUID_FORMAT),
        AGENT_REFRESH_TOKEN(String.class),
        EMPLOYEE_NAME(Object.class),
        AGENT_COOKIE(String.class),
        AGENT_EMAIL(String.class),
        AGENT_PASSWORD(String.class),
        AGENT_NEW_PASSWORD(String.class),
        NUMBER_OF_PREVIOUS_PASSWORDS(Integer.class),

        // agent find flight data
        GENERATE_LOADING_PRIORITY_REPORT(Boolean.class),

        //last response status line
        LAST_RESPONSE_STATUS_LINE(String.class),

        // customer data
        CUSTOMER_ID(Object.class),
        CUSTOMER_DATA(Object.class),
        CUSTOMER_EMAIL(Object.class),
        CUSTOMER_ADDRESS_LINE_1(Object.class),
        GUEST_EMAIL(Object.class),
        CUSTOMER_PHONE_NUMBER(Object.class),
        CUSTOMER_PASSWORD(Object.class),
        CUSTOMER_ACCESS_TOKEN(String.class, GUID_FORMAT),
        CUSTOMER_REFRESH_TOKEN(String.class),
        SIGNIFICANT_OTHER_ID(Object.class),
        DEPENDENT_ID(Object.class),
        GET_CUSTOMER_PROFILE_SERVICE(Object.class),
        CUSTOMER_SERVICE_RESPONSE(Object.class),
        CUSTOMER_COMMENT_CODE(Object.class),
        PASSENGER_LAST_NAME(Object.class),
        PASSENGER_FIRST_NAME(Object.class),
        PASSENGER_FULL_NAME(String.class),
        PASSENGER_EMAIL(String.class),
        PASSENGER_NEW_AGE(Object.class),
        SIGNIFICANT_OTHER_STAFF_PRIVILEGES(Boolean.class),
        CUSTOMER_COOKIE(Object.class),
        AUTHENTICATION_CUSTOMER_LOGIN(Object.class),

        REGISTER_CUSTOMER_REQUEST(Object.class),
        REMEMBER_ME(Object.class),
        REGISTRATION_TYPE(String.class),
        SEND_EMAIL(Object.class),

        //refundable item service
        GET_REFUNDABLE_ITEM_SERVICE(Object.class),

        // searchFor Customer TODO what service is this?
        IDENTIFY_CUSTOMER_SERVICE(Object.class),

        // updateBasicDetails service
        UPDATE_BASIC_DETAILS_REQUEST_BODY(Object.class),
        IS_BOOKER(Object.class),
        IS_PASSENGER_SAVE_TO_PROFILE(Object.class),
        ALL_FLIGHT(String.class),
        PASSENGER_STATUS(String.class),

        // changeFlight data
        PASSENGER_LIST(Object.class),
        CHANGE_FLIGHT_KEY(String.class),
        CHANGE_FLIGHT_PRICE(Double.class),
        CHANGE_FLIGHT_PASSENGERS(String.class, true),
        FOR_MISSED_FLIGHT(Boolean.class),
        CHANGE_FLIGHT_EARLIER_FLIGHT(Boolean.class),

        // update original booking for group booking
        GB_UPDATE_ORIGINAL(Boolean.class),
        // number of sub booking for series seating
        NUMBER_SUB_BOOKING(Integer.class),
        // sub booking id
        SUB_BOOKING_ID(String.class),
        STOCK_BEFORE_CHANGE_FLIGHT(Object.class),
        STOCK_AFTER_CHANGE_FLIGHT(Object.class),

        // carHire data
        // get station service
        STATION_COUNTRY_CODE(String.class),
        STATION_DATE(String.class),
        STATION_CODE(String.class),
        // find cars service
        PICK_UP_STATION(String.class),
        DROP_OFF_STATION(String.class),
        FIND_CARS_SERVICE(Object.class),
        // car order entry number
        CAR_ORDER_ENTRY_NUMBER(Object.class),


        // cleaning data
        BASKET_FLIGHTS(Object.class),
        COMMIT_FLIGHTS(Object.class),

        // store service calls
        //TODO which service calls? we already have service in common data
        SERVICE_CALLS(Object.class),

        // AL data
        ALLOCATED_SEATS(Object.class),
        ALLOCATED_FLIGHTS(Object.class),
        FLIGHT_FULLY_ALLOCATED(Object.class),

        // voucher data
        VOUCHER_CODE(Object.class),
        // voucher
        VOUCHERS(Object.class),
        VOUCHER_CURRENCY(String.class),

        // update Fraudolent Status
        IS_FRAUDOLENT(Object.class),

        // findTravelInsuranceProducts
        NUM_PASSENGER_OVER_64(Integer.class),

        //Manage Compensation
        COMPENSATION_TRANSACTION_ID(Object.class),
        COMPENSATION_AMOUNT(Double.class),

        //Update password
        NEW_PASSWORD(String.class),

        //manage apis
        OVERRIDE_NAME(Boolean.class),
        MANAGE_API_REQUEST_BODY(Object.class),

        //Disruptions
        NOTIFY_PASSENGER(Boolean.class),

        //AddPassengerToFlight
        IS_INFANT_ON_LAP(Boolean.class),
        INFANT_OUTBOUND(String.class),

        //UpdatePassengerContactDetails
        UPDATED_EMAIL(String.class),
        NIF_NUMBER(String.class),
        UPDATED_PHONE_NUMBER(String.class),
        PHONE_NUMBER(String.class),
        DIALLING_CODE(String.class),

        //Passenger Details
        PASSENGER_TITLE(String.class),
        PASSENGER_AGE(Integer.class),


        //Update Passenger dateOfBirth
        PASSENGER_DOB(String.class),

        //GetRefundReasons
        TYPE_PARAMETER(String.class),

        //Update Passenger Contact Details
        PASSENGER_CONTACT_UPDATE_LEAD(Boolean.class),
        PASSENGER_CONTACT_SAVE_CUSTOMER_PROFILE(Boolean.class),


        //ManageICTSStatus
        ICTS_STATUS_CODE(String.class),
        ICTS_MESSAGE_CODE(String.class),
        ICTS_MESSAGE_STATUS_CODE(String.class),
        ALL_RELATED_FLIGHT(String.class),

        RETRY_AUTO_ICTS(Boolean.class),
        REJECT_ICTS(Boolean.class),
        CHECK_ICTS(Boolean.class),
        REJECTED_FLAG(Boolean.class),
        PASSENGER_LIST_AUTO_ICTS(String.class, true),

        //RefreshToken
        TYPE_REQUEST(String.class),

        //Update Passenger
        UPDATE_PASSENGER_REQUEST_BODY(Object.class),

        //Agent Revenue
        PRODUCT_TYPE(String.class),

        // getFeesAndTaxes service
        FEES_AND_TAXES_CODE(String.class),
        FEES_AND_TAXES_CHANNEL(String.class),
        FEES_AND_TAXES_SECTOR(String.class),
        FEES_AND_TAXES_CURRENCY(String.class),
        FEES_AND_TAXES_PASSENGER(String.class),

        // other data
        SCENARIO(Object.class),
        SESSION(Object.class),
        TRANSACTION_ID(Object.class),
        //TODO what's invalid?
        INVALID(Object.class),
        //TODO where is used this flight inventory?
        FLIGHT_INVENTORY(Object.class),
        //TODO where is used this flight hold items?
        FLIGHT_HOLD_ITEMS(Object.class),
        //TODO required for what?
        REQUIRED_EJPLUS(Object.class),
        //TODO ??
        EJPLUS_MEMBERSHIPTYPE(Object.class),
        //TODO what field?
        FIELD(Object.class),
        //TODO which field?
        OLD_VALUE_FIELD(Object.class),
        //TODO what's this model
        MEMBERSHIP_MODEL(Object.class),
        //TODO what is that?
        UPDATE_PASSENGER_BASED_SAVED_PASSENGER(Object.class),
        //TODO for what you want deal?
        WANT_DEAL(Object.class),
        //TODO there's already a basket(Object.class), what is this needed for?
        ORIGINAL_BASKET(Object.class),
        //TODO of what?
        PRICE_DIFFERENCE(Object.class),
        //TODO there's already a basket(Object.class), what is this needed for?
        AMENDED_BASKET(Object.class),
        //TODO what comment?
        COMMENT_CODE(Object.class),
        AMENDED_BASKET_SERVICE(Object.class),
        AGENTOVERRIDE(Object.class),
        EMAIL(Object.class),
        EMAILS(String.class, true),
        IS_LINKED(Boolean.class),
        NON_REFUNDED_ITEM(Double.class),
        IS_RECENT_SEARCH(Boolean.class),

        // Identify Customer query Params
        EJ_CARD(String.class),
        FLIGHT_CLUB_NUMBER(String.class),
        POSTCODE(String.class),
        COUNTRY(String.class),
        COUNTRY_ISO_CODE(String.class),
        SORT_FIELD(String.class),
        SORT_ORDER(String.class),
        SIGNIFICANT_OTHER_IDENTITY(String.class),

        FLEXI_THRESHOLD_DATE_FOR_FLIGHT_CHANGE_BEFORE_DEPARTURE(String.class),
        FLEXI_THRESHOLD_DATE_FOR_FLIGHT_CHANGE_AFTER_DEPARTURE(String.class),

        ADDITIONAL_SEAT_REASON_CODE(String.class),
        EXPECTED_MARKET_GROUP(ExpectedMarketGroup.class, true),
        EXPECTED_MARKETGROUP_DESCRIPTION(ExpectedLocalizedDescription.class, true),
        EXPECTED_MARKETGROUP_NAMES(ExpectedLocalizedName.class, true),
        SYMBOL(String.class);


        private Class type;
        private boolean isList;
        private Pattern pattern;

        DataKeys(Class type) {
            this.type = type;
            this.isList = false;
            this.pattern = null;
        }

        DataKeys(Class type, boolean isList) {
            this.type = type;
            this.isList = isList;
            this.pattern = null;
        }

        DataKeys(Class<String> type, Pattern pattern) {
            this.type = type;
            this.isList = false;
            this.pattern = pattern;
        }

        public Class getType() {
            return type;
        }

        public <T> boolean isSameTypeOf(T value) {
            if (isList) {
                List generic = (List) value;
                return CollectionUtils.isEmpty(generic) || this.type.isInstance(generic.get(0));
            } else if (Objects.nonNull(this.pattern)) {
                return Objects.isNull(value) || pattern.matcher((String) value).find();
            } else {
                return Objects.isNull(value) || this.type.isInstance(value);
            }
        }

        public <T> boolean isNotSameTypeOf(T value) {
            return !(Objects.isNull(value) || isSameTypeOf(value));
        }
    }

    public enum testData {
        SYMBOL;
    }
}