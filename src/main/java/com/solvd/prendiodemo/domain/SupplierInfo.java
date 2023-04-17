package com.solvd.prendiodemo.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SupplierInfo {

    private String name;
    private String shippingMethod;
    private String prepaidFreight;
    private String paymentTerms;
    private String fob;
    private String email;
    private String defaultServicePhone;
    private String remitName;
    private List<String> addressLines;
    private String city;
    private String state;
    private String zip;
    private String notes;
    private String shipToLine2;
    private String accountNumber;
}
