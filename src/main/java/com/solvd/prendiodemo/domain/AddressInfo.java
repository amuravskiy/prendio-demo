package com.solvd.prendiodemo.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddressInfo {

    private String phone;
    private List<String> addressLines;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String addressCode;
    private boolean isDefault;
    private String addressType;
}
