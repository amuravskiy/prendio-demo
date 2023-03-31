package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class OrderPreview extends AbstractUIObject {

    @FindBy(xpath = "//tr[@cartid]//td[1]")
    private ExtendedWebElement cartName;

    public OrderPreview(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getCartName() {
        return cartName.getText();
    }
}
