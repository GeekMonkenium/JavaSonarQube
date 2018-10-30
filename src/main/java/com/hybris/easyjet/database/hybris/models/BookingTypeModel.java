package com.hybris.easyjet.database.hybris.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@Builder
public class BookingTypeModel {

    private String code;
    private List<String> channels;
    private List<String> disallowedUserGroup;

    public BookingTypeModel(String code, List<String> channels, List<String> disallowedUserGroup) {

        this.code = code;
        this.channels = channels;
        this.disallowedUserGroup = disallowedUserGroup;
    }
}
