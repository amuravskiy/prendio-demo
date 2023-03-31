package com.solvd.prendiodemo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    @FindBy(id = "username")
    private ExtendedWebElement usernameField;

    @FindBy(xpath = "//button[*[contains(text(),'Not you?')]]")
    private ExtendedWebElement notYouButton;

    @FindBy(id = "password")
    private ExtendedWebElement passwordField;

    @FindBy(id = "rememberUsername")
    private ExtendedWebElement rememberUsernameCheckbox;

    @FindBy(xpath = "//button[*[contains(text(),'Continue')]]")
    private ExtendedWebElement continueButton;

    @FindBy(xpath = "//button[*[contains(text(),'Forgot Password')]]")
    private ExtendedWebElement forgotPasswordButton;

    @FindBy(linkText = "Powered by OneLogin")
    private ExtendedWebElement poweredByOneLoginLink;

    @FindBy(linkText = "Terms")
    private ExtendedWebElement TermsLink;

    @FindBy(linkText = "Privacy Policy")
    private ExtendedWebElement PrivacyPolicyLink;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public OneLoginPortalPage login(String username, String password) {
        usernameField.type(username);
        continueButton.click();
        passwordField.type(password);
        continueButton.click();
        return new OneLoginPortalPage(driver);
    }
}
