package com.hybris.easyjet.database.hybris.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class BookingHistoryModel {
    private String eventType;
    private String requestingChannel;
    private String eventDate;
    private String userId;
}
