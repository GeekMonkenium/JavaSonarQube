package com.hybris.easyjet.database.hybris.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpectedLocalizedDescription {
    private String description;
    private String locale;

    public ExpectedLocalizedDescription(String description, String locale){
        this.description=description;
        this.locale=locale;
    }
}
