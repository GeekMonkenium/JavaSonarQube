package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SectorsModel {

    private String code;
    private String departureAirport;
    private String arrivalAirport;
    private Double distance;
    private Boolean apis;
    private Boolean worldWide;
    private LocalDate apisActiveFromDate;
    @Builder
    public SectorsModel(String code, String distance, Boolean apis, Boolean worldWide, LocalDate apisActiveFromDate) {
        this.code = code;
        this.departureAirport = code.substring(0, 3);
        this.arrivalAirport = code.substring(3);
        try {
            this.distance = BigDecimal.valueOf(Double.valueOf(distance))
                    .setScale(2, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
        } catch (Exception e) {
            this.distance = null;
        }
        this.apis = apis;
        this.worldWide = worldWide;
        this.apisActiveFromDate = apisActiveFromDate;
    }

}