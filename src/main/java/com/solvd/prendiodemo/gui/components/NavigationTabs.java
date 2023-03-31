package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.utils.Util;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NavigationTabs extends AbstractUIObject {

    @FindBy(xpath = "..//li//a")
    private List<ExtendedWebElement> tabs;

    public NavigationTabs(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickTabByName(String name) {
        Util.clickElementByName(tabs, name);
    }
}
