package com.solvd.prendiodemo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentInfo {

    private String name;
    private String description;
    private String notes;
}
