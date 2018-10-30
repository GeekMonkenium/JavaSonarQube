package com.hybris.easyjet.database.hybris.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FinancialInfoModel {

    private List<FinancialInfoEntryModel> taxes;
    private List<FinancialInfoEntryModel> fees;
    private List<FinancialInfoEntryModel> promotions;
    private List<PassengerFinancialModel> passengers;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PassengerFinancialModel {
        private String code;
        private String type;
        private String sector;
        private List<FinancialItemModel> items;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FinancialItemModel {
        private String productId;
        private FinancialInfoEntryModel basePrice;
        private List<FinancialInfoEntryModel> taxes;
        private List<FinancialInfoEntryModel> fees;
        private List<FinancialInfoEntryModel> discounts;
        private List<FinancialInfoEntryModel> promotions;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FinancialInfoEntryModel {
        private String code;
        private Double amount;
        private String currency;
        private Double spotRate;
        private Double margin;
    }

}