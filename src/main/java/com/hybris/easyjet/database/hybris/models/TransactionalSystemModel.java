package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;


/**
 * Created by giuseppecioce on 10/11/2017.
 */
@Builder
@Getter
@EqualsAndHashCode
public class TransactionalSystemModel {
    private String code;
    private String name;
}
