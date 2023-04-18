package com.solvd.prendiodemo.web.components.common;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.web.pages.ProfilePage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserPhotoBlock extends AbstractUIObject {

    @FindBy(className = "status_window")
    private UserStatusWindow userStatusWindow;

    @FindBy(xpath = "//a[img[@id='profileimage']]")
    private ExtendedWebElement userStatusOpener;

    public UserPhotoBlock(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public UserStatusWindow openUserStatus() {
        userStatusOpener.click();
        waitUntil(ExpectedConditions.visibilityOf(userStatusOpener.getElement()), EXPLICIT_TIMEOUT);
        return userStatusWindow;
    }

    public ProfilePage openProfile() {
        return openUserStatus().clickViewProfileButton();
    }
}
