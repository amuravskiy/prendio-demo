package com.solvd.prendiodemo.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SupplierItemInfo {

    private String supplierPart;
    private String desc;
    private String genericDesc;
    private String deprecatedPart;
    private String manufacturer;
    private String manufactNumber;
    private String notes;
    private String status;
    private String safetyCheckboxesChecked;
    private String cas;
    private String unit;
    private String qtyEach;
    private List<ItemSpec> specs;
}
