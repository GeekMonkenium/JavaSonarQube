package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Data
public class RefundInfoModel {
    private String passenerId;
    private String productCode;
    private Double basePriceRefunded;
    private String refundedCurrency;
    private Double refundedableAmount;
    private HashMap<String, Double> taxesRefunded;
    private HashMap<String, Double> feesRefunded;
    private HashMap<String, Double> discountsRefunded;

    @Builder
    public RefundInfoModel(
            String passenerId,
            String productCode,
            Double basePriceRefunded,
            String refundedCurrency,
            Double refundedableAmount,
            Map taxesRefunded,
            Map feesRefunded,
            Map discountsRefunded) {
        this.passenerId = passenerId;
        this.productCode = productCode;
        this.basePriceRefunded = basePriceRefunded;
        this.refundedCurrency = refundedCurrency;
        this.refundedableAmount = refundedableAmount;
        this.taxesRefunded = new HashMap<>(taxesRefunded);
        this.feesRefunded = new HashMap<>(feesRefunded);
        this.discountsRefunded = new HashMap<>(discountsRefunded);
    }
}
