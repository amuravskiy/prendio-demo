package com.solvd.prendiodemo.web.components.cart;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.domain.CartContents;
import com.solvd.prendiodemo.domain.ItemContents;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a list of items for a supplier in a cart
 */
public class SupplierItems extends AbstractUIObject {

    @FindBy(id = "spnsuppName")
    private ExtendedWebElement supplierName;

    @FindBy(tagName = "suppliercartdetailitem")
    private List<CartItem> items;

    public SupplierItems(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getSupplierName() {
        return supplierName.getText();
    }

    public ItemContents getItemContents(int index) {
        ItemContents itemContents = items.get(index).getItemContents();
        itemContents.setSupplier(supplierName.getText());
        return itemContents;
    }

    public List<ItemContents> getAllItemContents() {
        return items.stream()
                .map(CartItem::getItemContents)
                .peek(itemContents -> itemContents.setItemNumber(getSupplierName()))
                .collect(Collectors.toList());
    }
}
