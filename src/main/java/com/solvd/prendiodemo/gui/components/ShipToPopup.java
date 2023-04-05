package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ShipToPopup extends BasePopup {

    @FindBy(xpath = "..//table[@id='tblcartlistpopup']//tbody//tr//td[1]")
    private List<ExtendedWebElement> line1s;

    @FindBy(xpath = "..//table[@id='tblcartlistpopup']//tbody//tr")
    private List<ExtendedWebElement> addresses;

    public ShipToPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String chooseShipToAddress(int index) {
        String address = line1s.get(index).getText();
        addresses.get(index).click();
        return address;
    }
}
