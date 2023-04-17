package com.solvd.prendiodemo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlipInfo {

    private String recDate;
    private String invoiceNumber;
    private String invDate;
    private String invoiceAmount;
    private int day;
}
