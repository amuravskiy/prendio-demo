package com.solvd.prendiodemo.web.components.receiver;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.web.components.BasePopup;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SupplierSelectPopup extends BasePopup {

    @FindBy(id = "scansuplier")
    private ExtendedWebElement supplierField;

    @FindBy(xpath = "//div[@class='input_dialog abs']/ul/li")
    private DropDownOptions options;

    public SupplierSelectPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void typeSupplierSearchQuery(String query) {
        supplierField.type(query);
    }

    public void selectFirstAvailableSupplier() {
        options.clickOptionByIndex(0);
    }
}
