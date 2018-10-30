package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaxModel {
    private String taxCode;
    private String taxName;
    private String channel;
    private String passengerType;
    private String sector;
    private String currency;
    private Double amount;
}