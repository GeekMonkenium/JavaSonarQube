package com.hybris.easyjet.database.hybris.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
public class VoucherModel {
    private String voucherCode;
    private String currency;
    private Double amount;
    private Double remainingBalance;
    private String emailId;
    private LocalDate activeFromDate;
    private LocalDate activeToDate;
    private String customerName;
    private String bookingReference;
    private String primaryReasonCode;
    private String secondaryReasonCode;
    private String userId;
    private String channel;
}
