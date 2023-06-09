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
    private ExtendedWebElement loadedMarker;

    @FindBy(xpath = "//table[@id='tblbilllist']//tbody//tr[1]")
    private ShipToAddresses firstShipToAddresses;

    public AddressPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setUiLoadedMarker(loadedMarker);
        setLoadingStrategy(ElementLoadingStrategy.BY_VISIBILITY);
    }

    public void clickFirstAddress() {
        waitJsToLoad();
        firstShipToAddresses.click();
    }

    public String getAddressLineTwoText(int index) {
        return firstShipToAddresses.getLineTwo();
    }

    @Override
    public boolean isVisible() {
        return loadedMarker.isVisible();
    }
}
