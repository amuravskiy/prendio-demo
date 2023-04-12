package com.solvd.prendiodemo.gui.pages.buyerpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.gui.components.BasePopup;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class AddressPopup extends BasePopup {

    @FindBy(xpath = "//table[@id='tblbilllist']//tbody//tr")
    private ExtendedWebElement firstAddressesTableLine;

    @FindBy(xpath = "//table[@id='tblbilllist']//tbody//tr//td[2]")
    private List<ExtendedWebElement> line2s;

    @FindBy(xpath = "//table[@id='tblbilllist']//tbody//tr")
    private List<ExtendedWebElement> addresses;

    public AddressPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickAddress(int index) {
        addresses.get(index).click();
    }

    public String getAddressLine2Text(int index) {
        return line2s.get(index).getText();
    }

    @Override
    public void assertVisible() {
        Assert.assertTrue(firstAddressesTableLine.isVisible(), "Addresses not found in address popup");
    }
}
