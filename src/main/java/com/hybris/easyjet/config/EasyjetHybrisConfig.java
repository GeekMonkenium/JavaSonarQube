package com.hybris.easyjet.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by giuseppedimartino on 21/04/17.
 */
@Component
@Getter
public class EasyjetHybrisConfig extends EasyjetTestConfig {

    private final String hybrisBuildVersion;
    private final String hybrisFlightsEndpoint;
    private final String hybrisGetBasketEndpoint;
    private final String hybrisAddBasketEndpoint;
    private final String hybrisBookingsEndpoint;
    private final String hybrisRefundBookingsEndpoint;
    private final String hybrisCommentsEndpoint;
    private final String hybrisCustomers;
    private final String hybrisAnonymousResetPassword;
    private final String hybrisCustomerLoginEndPoint;
    private final String hybrisAgentLoginEndPoint;
    private final String hybrisAgentLogoutEndPoint;
    private final String hybrisAgentChangePasswordEndPoint;
    private final String DCSAgentLoginEndPoint;
    private final String hybrisAirportsEndpoint;
    private final String hybrisCountriesEndpoint;
    private final String hybrisCurrenciesEndpoint;
    private final String hybrisCommercialFlightScheduleEndpoint;
    private final String hybrisMarketGroupsEndpoint;
    private final String hybrisLanguagesEndpoint;
    private final String hybrisPassengerTitlesEndpoint;
    private final String hybrisPassengerTypesEndpoint;
    private final String hybrisGetPaymentMethodsEndPoint;
    private final String hybrisGetInternalPaymentMethodsEndPoint;
    private final String hybrisGetSectorsEndPoint;
    private final String hybrisPreferences;
    private final String hybrisTravelDocumentTypes;
    private final String hybrisSSRData;
    private final String hybrisSavedSSRData;
    private final String hybrisHoldItems;
    private final String getAvailableFareTypes;
    private final String hybrisAlternateAirportsEndpoint;
    private final String getDiscountReason;
    private final String hybrisSeatMapEndpoint;
    private final String hybrisReCalculatePricesEndpoint;
    private final String hybrisAmendBasicDetailsEndpoint;
    private final String hybrisAmendSSREndpoint;
    private final String hybrisAdditionalFareEndpoint;
    private final String hybrisCheckInEndPoint;
    private final String hybrisgetRefundReasonsEndPoint;
    private final String hybrisCurrencyConversion;
    private final String hybrisValidateMembership;
    private final String hybrisOfferPricesEndpoint;
    private final String hybrisGetStationsEndpoint;
    private final String hybrisTermsAndConditionsEndpoint;
    private final String hybrisEvent;
    private final String hybrisBulkTransferReasonsEndpoint;
    private final String hybrisAdditionalSeatReasons;
    private final String hybrisRefreshToken;
    private final String hybrisIdentifyPassengerEndpoint;
    private final String hybrisFeesTaxes;
    private final String hybrisGetVouchers;
    private final String agentFindFlights;
    private final String hybrisDisruption;
    private final String hybrisTransactionalSystem;
    private final String hybrisSessions;
    private final String hybrisgetPromotionsEndpoint;
    private final String hybrisgetBookingTypes;
    private final String hybrisgetICTSStatus;
    private final String hybrisUpdatePassengerContactDetails;
    private final String hybrisGetProducts;
    private final String hybrisReportingEndPoint;
    private final String hybrisFindCarsEndpoint;
    private final String hybrisGetInsuranceEndpoint;
    private final String IEXSymbolEndpoint;

    @Autowired
    public EasyjetHybrisConfig(Environment environment) {
        super(environment);
        this.hybrisBuildVersion = environment.getProperty("hybris.buildVersion");
        this.hybrisUpdatePassengerContactDetails = environment.getProperty("hybris.updatePassengerContactDetails");
        this.hybrisFlightsEndpoint = environment.getProperty("hybris.searchFlights");
        this.hybrisSeatMapEndpoint = environment.getProperty("hybris.flights");
        this.hybrisOfferPricesEndpoint = environment.getProperty("hybris.flights");
        this.hybrisGetBasketEndpoint = environment.getProperty("hybris.getBasket");
        this.hybrisAddBasketEndpoint = environment.getProperty("hybris.addBasket");
        this.hybrisBookingsEndpoint = environment.getProperty("hybris.bookings");
        this.hybrisRefundBookingsEndpoint = environment.getProperty("hybris.refundBookings");
        this.hybrisCommentsEndpoint = environment.getProperty("hybris.comments");
        this.hybrisCustomers = environment.getProperty("hybris.customers");
        this.hybrisAnonymousResetPassword = environment.getProperty("hybris.anonymousResetPassword");
        this.hybrisCustomerLoginEndPoint = environment.getProperty("hybris.customerLogin");
        this.hybrisAgentLoginEndPoint = environment.getProperty("hybris.agentLogin");
        this.hybrisAgentLogoutEndPoint = environment.getProperty("hybris.agentLogout");
        this.hybrisAgentChangePasswordEndPoint = environment.getProperty("hybris.agentChangePassword");
        this.DCSAgentLoginEndPoint = environment.getProperty("DCS.agentLogin");
        this.hybrisAirportsEndpoint = environment.getProperty("hybris.airports");
        this.hybrisCountriesEndpoint = environment.getProperty("hybris.countries");
        this.hybrisCurrenciesEndpoint = environment.getProperty("hybris.currencies");
        this.hybrisCommercialFlightScheduleEndpoint = environment.getProperty("hybris.commercialFlightSchedule");
        this.hybrisLanguagesEndpoint = environment.getProperty("hybris.languages");
        this.hybrisMarketGroupsEndpoint = environment.getProperty("hybris.market.groups");
        this.hybrisPassengerTypesEndpoint = environment.getProperty("hybris.passengerTypes");
        this.hybrisPassengerTitlesEndpoint = environment.getProperty("hybris.passengerTitles");
        this.hybrisGetPaymentMethodsEndPoint = environment.getProperty("hybris.getPaymentMethods");
        this.hybrisGetInternalPaymentMethodsEndPoint = environment.getProperty("hybris.getInternalPaymentFunds");
        this.hybrisGetSectorsEndPoint = environment.getProperty("hybris.getSectors");
        this.hybrisPreferences = environment.getProperty("hybris.preferences");
        this.hybrisTravelDocumentTypes = environment.getProperty("hybris.travel.document.types");
        this.hybrisSSRData = environment.getProperty("hybris.ssr.data");
        this.hybrisHoldItems = environment.getProperty("hybris.holdItems");
        this.getAvailableFareTypes = environment.getProperty("hybris.getAvailableFareTypes");
        this.hybrisAlternateAirportsEndpoint = environment.getProperty("hybris.getAlternateAirports");
        this.getDiscountReason = environment.getProperty("hybris.discountReason");
        this.hybrisSavedSSRData = environment.getProperty("hybris.saved.ssr");
        this.hybrisReCalculatePricesEndpoint = environment.getProperty("hybris.recalculatePrices");
        this.hybrisAmendBasicDetailsEndpoint = environment.getProperty("hybris.amendBasicDetails");
        this.hybrisCheckInEndPoint = environment.getProperty("hybris.checkIn");
        this.hybrisAmendSSREndpoint = environment.getProperty("hybris.amendSSR");
        this.hybrisAdditionalFareEndpoint = environment.getProperty("hybris.additionalfare");
        this.hybrisgetRefundReasonsEndPoint = environment.getProperty("hybris.getRefundReasons");
        this.hybrisCurrencyConversion = environment.getProperty("hybris.currencyConversion");
        this.hybrisValidateMembership = environment.getProperty("hybris.validateMembership");
        this.hybrisTermsAndConditionsEndpoint = environment.getProperty("hybris.termsAndConditions");
        this.hybrisGetStationsEndpoint = environment.getProperty("hybris.getStations");
        this.hybrisEvent = environment.getProperty("hybris.getEvent");
        this.hybrisBulkTransferReasonsEndpoint = environment.getProperty("hybris.getBulkTransferReasons");
        this.hybrisAdditionalSeatReasons = environment.getProperty("hybris.getAdditionalSeatReasons");
        this.hybrisRefreshToken = environment.getProperty("hybris.refreshToken");
        this.hybrisFeesTaxes = environment.getProperty("hybris.feesTaxes");
        this.hybrisIdentifyPassengerEndpoint = environment.getProperty("hybris.identifyPassenger");
        this.agentFindFlights = environment.getProperty("hybris.agentFindFlights");
        this.hybrisGetVouchers = environment.getProperty("hybris.getVoucher");
        this.hybrisDisruption = environment.getProperty("hybris.Disruption");
        this.hybrisTransactionalSystem = environment.getProperty("hybris.transactionalSystem");
        this.hybrisSessions = environment.getProperty("hybris.sessions");
        this.hybrisgetPromotionsEndpoint = environment.getProperty("hybris.getPromotions");
        this.hybrisgetBookingTypes = environment.getProperty("hybris.bookingTypes");
        this.hybrisgetICTSStatus = environment.getProperty("hybris.getICTSStatus");
        this.hybrisGetProducts = environment.getProperty("hybris.getProducts");
        this.hybrisReportingEndPoint = environment.getProperty("hybris.reporting");
        this.hybrisFindCarsEndpoint = environment.getProperty("hybris.findCars");
        this.hybrisGetInsuranceEndpoint = environment.getProperty("hybris.findInsurance");
        this.IEXSymbolEndpoint = environment.getProperty("IEX.SymbolEndpoint");
    }

}
