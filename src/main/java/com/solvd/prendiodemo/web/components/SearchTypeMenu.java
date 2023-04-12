package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SearchTypeMenu extends AbstractUIObject {

    @FindBy(xpath = "..//li[@type='Catalog']")
    private ExtendedWebElement catalogType;

    @FindBy(xpath = "..//li[@type='Order']")
    private ExtendedWebElement orderType;

    public SearchTypeMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void chooseCatalogType() {
        getRootExtendedElement().click();
        catalogType.click();
    }

    public void chooseOrderType() {
        getRootExtendedElement().click();
        orderType.click();
    }
}
