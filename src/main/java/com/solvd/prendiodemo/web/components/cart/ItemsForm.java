package com.solvd.prendiodemo.web.components.cart;

import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.domain.ItemContents;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ItemsForm extends AbstractUIObject {

    @FindBy(tagName = "suppliercart")
    private List<SupplierItems> suppliers;

    public ItemsForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ItemContents getItemContents(int supplierIndex, int itemIndex) {
        return suppliers.get(supplierIndex).getItemContents(itemIndex);
    }

    public List<ItemContents> getAllItems() {
        return suppliers.stream()
                .flatMap(supplier -> supplier.getAllItemContents().stream())
                .collect(Collectors.toList());
    }
}
