package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PassengerTypeDbModel {

    private String type;
    private int minAge;
    private int maxAge;

    public PassengerTypeDbModel(String type, int minAge, int maxAge) {
        this.type = type;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

}