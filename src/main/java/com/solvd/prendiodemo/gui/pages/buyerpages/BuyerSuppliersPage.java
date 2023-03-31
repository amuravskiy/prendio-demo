package com.solvd.prendiodemo.gui.pages.buyerpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.AddSupplierPopup;
import com.solvd.prendiodemo.gui.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class BuyerSuppliersPage extends BasePage {

    @FindBy(xpath = "//li[a[text()='Suppliers'] and @class='selected']")
    private ExtendedWebElement suppliersTabActive;

    @FindBy(id = "Add_Supplier")
    private ExtendedWebElement addSupplierButton;

    @FindBy(id = "popupform")
    private AddSupplierPopup addSupplierPopup;

    public BuyerSuppliersPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(suppliersTabActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public AddSupplierPopup clickAddSupplierButton() {
        addSupplierButton.click();
        return addSupplierPopup;
    }

    public void fillRequiredFields() {
        addSupplierPopup.fillRequiredFields();
    }
}
