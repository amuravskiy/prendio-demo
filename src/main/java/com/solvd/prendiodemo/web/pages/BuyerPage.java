package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.pages.buyer.AddressesPage;
import com.solvd.prendiodemo.web.pages.buyer.BuyerSuppliersPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class BuyerPage extends BasePage {

    @FindBy(xpath = "//li[@id and @class='active' and a[text()='Buyer']]")
    private ExtendedWebElement activeBuyerButton;

    @FindBy(xpath = "//a[text()='Suppliers']")
    private ExtendedWebElement suppliersButton;

    @FindBy(xpath = "//a[text()='Addresses']")
    private ExtendedWebElement addressesButton;

    public BuyerPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(activeBuyerButton);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public BuyerSuppliersPage clickSuppliers() {
        getNavigationTabs().clickTabByName("Suppliers");
        return new BuyerSuppliersPage(getDriver());
    }

    public AddressesPage clickAddresses() {
        getNavigationTabs().clickTabByName("Addresses");
        return new AddressesPage(getDriver());
    }
}
