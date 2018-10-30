package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class BookingDetailsModel {
    private String bookingReference;
    private LocalDate bookingDate;
    private String bookingStatus;
    private String bookingType;
    private String channel;
    private String ipAddress;
    private String currencyIsoCode;
    private String flightNumber;
    private String originAirport;
    private String destinationAirport;
    private LocalDate travelFromDate;
    private LocalDate travelToDate;
    private LocalDate transactionDate;
    private Double paymentAmount;

    private Customer customer;
    private List<Passenger> passengers;

    @Getter
    @Builder
    public static class Customer {
        @Builder.Default
        private String title = "";
        @Builder.Default
        private String firstName = "";
        @Builder.Default
        private String lastName = "";
        @Builder.Default
        private String email = "";
        @Builder.Default
        private String postcode = "";
        @Builder.Default
        private String contactNumber = "";
    }

    @Getter
    @Builder
    public static class Passenger {
        private String title;
        private String firstName;
        private String lastName;
        private String contactNumber;
        private String email;
        @Builder.Default
        private LocalDate dob = null;
        private String travelDocumentType;
        private String travelDocumentNumber;
        private String ejPlusNumber;
        private String ssrCode;
        private String sequenceNumber;
    }
}