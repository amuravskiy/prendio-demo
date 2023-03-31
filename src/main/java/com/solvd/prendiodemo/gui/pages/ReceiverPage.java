package com.solvd.prendiodemo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.pages.receiverpages.ReceiverScanMatchPage;
import com.solvd.prendiodemo.gui.pages.receiverpages.ReceiverScanPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ReceiverPage extends BasePage {

    @FindBy(xpath = "//li[@id and @class='active' and a[text()='Receiver']]")
    private ExtendedWebElement receiverActive;

    @FindBy(xpath = "//a[@type='match']")
    private ExtendedWebElement scanMatchButton;

    public ReceiverPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(receiverActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public ReceiverScanPage clickScan() {
        navigationTabs.clickTabByName("Scan");
        return new ReceiverScanPage(driver);
    }

    public ReceiverScanMatchPage clickScanMatch() {
        scanMatchButton.click();
        return new ReceiverScanMatchPage(driver);
    }
}
