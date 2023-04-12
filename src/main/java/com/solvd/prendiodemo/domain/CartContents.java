package com.solvd.prendiodemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartContents {

    private List<ItemContents> items;
}
