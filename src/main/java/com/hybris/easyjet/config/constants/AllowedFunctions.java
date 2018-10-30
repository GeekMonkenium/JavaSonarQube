package com.hybris.easyjet.config.constants;

import java.util.Arrays;
import java.util.List;

public class AllowedFunctions {

    private static final List<String> viewBookingDetail = Arrays.asList("SendFlightDetailsEmail", "ChangePurchasedSeatNumber", "DisplayPassengersDetailsBooking", "RequestVATInvoiceForPaymentsMade");

    public static List<String> getViewBookingDetail() {
        return viewBookingDetail;
    }

    private static final List<String> changePassengerDetail = Arrays.asList("ChangePassengerNames", "CorrectPassengerName", "AddSSRdetails", "ChangeSSRdetails", "ChangePassengerAge", "AddEJPlusdetails", "ChangeEJPlusdetails", "AddAPIS", "ChangeAPIS", "AddPassengerContactDetails", "ChangePassengerContactDetails", "AddRemoveMoveInfantOnLap");

    public static List<String> getChangePassengerDetail() {
        return changePassengerDetail;
    }
}

