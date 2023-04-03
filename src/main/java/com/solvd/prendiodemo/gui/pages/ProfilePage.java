package com.solvd.prendiodemo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends BasePage {

    @FindBy(xpath = "//h2[text()='PROFILE']")
    private ExtendedWebElement profileTitle;

    @FindBy(id = "txtfirstname")
    private ExtendedWebElement firstName;

    @FindBy(id = "txtlastname")
    private ExtendedWebElement lastName;

    public ProfilePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(profileTitle);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public String getFullName() {
        return firstName.getAttribute("value") + " " + lastName.getAttribute("value");
    }
}
