package com.hybris.easyjet.database.hybris.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpectedLocalizedName {
    private String name;
    private String locale;

    public ExpectedLocalizedName(String name, String locale){
        this.name = name;
        this.locale = locale;
    }
}
