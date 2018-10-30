package com.hybris.easyjet.database.hybris.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by daniel on 11/11/2016.
 */
@Getter
@Setter
public class ExpectedMarketGroup {
    String code;
    String type;

    public ExpectedMarketGroup(String code, String type) {
        this.code=code;
        this.type=type;
    }
}
