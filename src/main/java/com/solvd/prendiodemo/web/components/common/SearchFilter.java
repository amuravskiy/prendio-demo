package com.solvd.prendiodemo.web.components.common;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SearchFilter extends AbstractUIObject {

    @FindBy(xpath = ".//input[@placeholder='<filter list>']")
    private ExtendedWebElement searchField;

    public SearchFilter(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void search(String query) {
        searchField.type(query);
        searchField.sendKeys(Keys.ENTER);
    }
}
