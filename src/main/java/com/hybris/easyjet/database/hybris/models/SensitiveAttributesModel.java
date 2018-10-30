package com.hybris.easyjet.database.hybris.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SensitiveAttributesModel {
    private String customerId;
    private String customer_ejPlusNumber;
    private String customer_apis;
    private String ssr;
    private String savedPaymentMethods;
    private String significantOthers;
    private String savedPassenger;
    private String contactMethodOption;
    private String contactTypeOption;
    private String frequencyOption;
    private String keyDateOption;
    private String marketingCommunicationOptions;
    private String seatingPreferenceOptions;
    private String travellingStart;
    private String travellingEnd;
}
