package com.hybris.easyjet.database.hybris.models;

import lombok.Data;

@Data
public class FeesAndTaxesModel {

    private String feeCode;
    private String feeName;
    private String passengerType;
    private Double feeValue;
    private String feeCurrency;

    public FeesAndTaxesModel(String feeCode, String feeName, String passengerType, Double feeValue, String feeCurrency) {
        this.feeCode = feeCode;
        this.feeName = feeName;
        this.passengerType = passengerType;
        this.feeValue = feeValue;
        this.feeCurrency = feeCurrency;
    }

}