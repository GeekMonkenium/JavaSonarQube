package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoucherInfo {
    private String email;
    private String code;
}