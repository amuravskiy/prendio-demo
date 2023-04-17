package com.solvd.prendiodemo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileInfo {

    private String title;
    private String phoneNumber;
    private String carrier;
    private String startDate;
    private String endDate;
}
