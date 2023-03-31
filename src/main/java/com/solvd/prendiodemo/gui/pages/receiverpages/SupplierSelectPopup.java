package com.solvd.prendiodemo.gui.pages.receiverpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.gui.components.ConfirmationPopup;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SupplierSelectPopup extends ConfirmationPopup {

    @FindBy(id = "scansuplier")
    private ExtendedWebElement searchSupplierInput;

    @FindBy(className = "input_dialog abs")
    private ExtendedWebElement supplierDropdown;

    @FindBy(xpath = "//div[@class='input_dialog abs']//li")
    private ExtendedWebElement firstSupplier;

    public SupplierSelectPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void searchSupplier(String query) {
        searchSupplierInput.type(query);
    }

    public boolean isSupplierDropdownVisible() {
        return supplierDropdown.isVisible();
    }

    public void clickFirstSupplier() {
        firstSupplier.click();
    }
}
