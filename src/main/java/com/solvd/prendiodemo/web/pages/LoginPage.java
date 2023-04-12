package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    @FindBy(id = "username")
    private ExtendedWebElement usernameField;

    @FindBy(id = "password")
    private ExtendedWebElement passwordField;

    @FindBy(xpath = "//button[*[contains(text(),'Continue')]]")
    private ExtendedWebElement continueButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public OneLoginPortalPage login(String username, String password) {
        usernameField.type(username);
        continueButton.click();
        passwordField.type(password);
        continueButton.click();
        return new OneLoginPortalPage(getDriver());
    }
}
