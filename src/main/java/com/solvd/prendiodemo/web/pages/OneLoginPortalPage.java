package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class OneLoginPortalPage extends AbstractPage {

    @FindBy(xpath = "//div[@class='app-cell-appname' and text()='Prendio_DEV_SSO']")
    private ExtendedWebElement prendioCompanyButton;

    @FindBy(xpath = "//button[text()='skip']")
    private ExtendedWebElement skipExtensionButton;

    public OneLoginPortalPage(WebDriver driver) {
        super(driver);
    }

    public DashboardPage goToPrendio() {
        skipExtensionButton.click();
        prendioCompanyButton.click();
        return new DashboardPage(getDriver());
    }
}
