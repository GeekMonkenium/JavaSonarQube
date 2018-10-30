package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HoldItemModel {
    private String productCode;
    private String productType;
    private String currency;
    private List<Price> prices;

    @Getter
    @Builder
    public static class Price {
        private Integer qtyFrom;
        private Double basePrice;
    }

}