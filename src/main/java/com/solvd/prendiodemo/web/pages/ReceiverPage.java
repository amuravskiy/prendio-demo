package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.pages.receiver.ReceiverScanMatchPage;
import com.solvd.prendiodemo.web.pages.receiver.ReceiverScanPage;
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

    public ReceiverScanPage clickScanSection() {
        navigationTabs.clickScanSection();
        return new ReceiverScanPage(getDriver());
    }

    public ReceiverScanMatchPage clickScanMatchSection() {
        scanMatchButton.click();
        return new ReceiverScanMatchPage(getDriver());
    }
}
