package com.solvd.prendiodemo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(exclude = {"project", "department", "glAccount"})
public class ItemContents {

    private String title;
    private String supplier;
    private String itemNumber;
    private String price;
    private String currencyType;
    private String project;
    private String glAccount;
    private String department;
}
