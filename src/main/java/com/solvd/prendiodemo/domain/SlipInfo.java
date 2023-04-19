package com.solvd.prendiodemo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlipInfo {

    private String receivedDate;
    private String invoiceNumber;
    private String invoiceDate;
    private String invoiceAmount;
    private int day;
}
