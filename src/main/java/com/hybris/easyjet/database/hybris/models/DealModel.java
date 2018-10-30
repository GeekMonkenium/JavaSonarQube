package com.hybris.easyjet.database.hybris.models;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by giuseppedimartino on 27/02/17.
 */
@Data
public class DealModel {

    private String systemName;
    private String officeId;
    private String corporateId;
    private List<String> posFees;
    private List<String> discounts;
    private String bookingType;
    private String fareType;

    public DealModel(String systemName, String officeId, String corporateId, String posFees, String discounts, String bookingType, String fareType) {
        this.systemName = systemName;
        this.officeId = officeId;
        this.corporateId = corporateId;
        if (StringUtils.isNotBlank(posFees)) {
            this.posFees = Arrays.asList(posFees.replace(",#1,", "").split(","));
        } else {
            this.posFees = null;
        }
        if (StringUtils.isNotBlank(discounts)) {
            this.discounts = Arrays.asList(discounts.replace(",#1,", "").split(","));
        } else {
            this.discounts = null;
        }
        this.bookingType = bookingType;
        this.fareType = fareType;
    }

}