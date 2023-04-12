package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class OrdersTable extends AbstractUIObject {

    @FindBy(xpath = "./tr[@cartid]")
    private List<OrderPreview> orderPreviews;

    public OrdersTable(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getCartPreviewName(int index) {
        return orderPreviews.get(index).getCartName();
    }
}
