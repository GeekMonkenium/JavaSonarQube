package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GBPEquivilantRefundModel {

    private Double gbpEquivalent;
    private Double originalAmount;
    private Double conversion;

    public GBPEquivilantRefundModel(Double gbpEquivalent, Double originalAmount, Double conversion) {
        this.gbpEquivalent = gbpEquivalent;
        this.originalAmount = originalAmount;
        this.conversion = conversion;
    }
}
