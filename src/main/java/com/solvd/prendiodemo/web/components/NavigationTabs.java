package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class NavigationTabs extends AbstractUIObject {

    @FindBy(xpath = "..//li[@type='suppliers']//a")
    private ExtendedWebElement suppliersButton;

    @FindBy(xpath = "..//li[@type='vouchers']//a")
    private ExtendedWebElement vouchersButton;

    @FindBy(xpath = "..//li[@type='scan']//a")
    private ExtendedWebElement scanButton;

    @FindBy(xpath = "..//li[@type='addresses']//a")
    private ExtendedWebElement addressesButton;


    public NavigationTabs(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickSuppliers() {
        suppliersButton.click();
    }

    public void clickAddresses() {
        addressesButton.click();
    }

    public void clickVouchers() {
        vouchersButton.click();
    }

    public void clickScan() {
        scanButton.click();
    }
}
