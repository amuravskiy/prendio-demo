package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.gui.pages.AllCartsPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CartsTable extends AbstractUIObject {
    @FindBy(xpath = "./a[contains(text(),'View All')]")
    private ExtendedWebElement viewAllButton;

    public CartsTable(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public AllCartsPage clickViewAllButton() {
        viewAllButton.click();
        return new AllCartsPage(driver);
    }
}
