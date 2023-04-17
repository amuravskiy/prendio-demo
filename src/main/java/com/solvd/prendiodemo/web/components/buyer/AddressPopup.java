package com.solvd.prendiodemo.web.components.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.web.components.BasePopup;
import com.solvd.prendiodemo.web.components.accountspayable.ShipToAddresses;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class AddressPopup extends BasePopup {

    @FindBy(xpath = "//table[@id='tblbilllist']//tbody//tr[1]")
    private ExtendedWebElement loadedMarket;

    @FindBy(xpath = "//table[@id='tblbilllist']//tbody//tr[1]")
    private ShipToAddresses firstShipToAddresses;

    public AddressPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setUiLoadedMarker(loadedMarket);
        setLoadingStrategy(ElementLoadingStrategy.BY_VISIBILITY);
    }

    public void clickFirstAddress() {
        firstShipToAddresses.click();
    }

    public String getAddressLine2Text(int index) {
        return firstShipToAddresses.getLine2();
    }

    @Override
    public boolean isVisible() {
        return loadedMarket.isVisible();
    }
}
