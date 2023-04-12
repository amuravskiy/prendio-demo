package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.web.pages.CartPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CartEntry extends AbstractUIObject {

    @FindBy(xpath = ".//a[@type='cart']")
    private ExtendedWebElement idLink;

    @FindBy(xpath = ".//td[2]")
    private ExtendedWebElement name;

    public CartEntry(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public CartPage clickId() {
        idLink.click();
        return new CartPage(getDriver());
    }

    public String getName() {
        return name.getText();
    }

    public String getId() {
        return idLink.getText();
    }
}
