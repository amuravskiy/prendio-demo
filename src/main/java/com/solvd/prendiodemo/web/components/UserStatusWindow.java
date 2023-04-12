package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.web.pages.ProfilePage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UserStatusWindow extends AbstractUIObject {

    @FindBy(xpath = "//a[text()='View profile']")
    private ExtendedWebElement viewProfileButton;

    public UserStatusWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ProfilePage clickViewProfileButton() {
        viewProfileButton.click();
        return new ProfilePage(getDriver());
    }
}
