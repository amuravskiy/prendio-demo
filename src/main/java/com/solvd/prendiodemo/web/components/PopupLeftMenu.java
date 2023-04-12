package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class PopupLeftMenu extends AbstractUIObject {

    @FindBy(xpath = "./a[@type='account']")
    private ExtendedWebElement accountNumbersButton;

    @FindBy(xpath = "./a[@type='catalog']")
    private ExtendedWebElement catalogItemsButton;

    @FindBy(xpath = "./a[@type='deptWatcher']")
    private ExtendedWebElement watchersButton;

    @FindBy(xpath = "./a[@type='deptUser']")
    private ExtendedWebElement usersButton;

    public PopupLeftMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickAccountNumbers() {
        accountNumbersButton.click();
    }

    public void clickCatalogItems() {
        catalogItemsButton.click();
    }

    public void clickWatchers() {
        watchersButton.click();
    }

    public void clickUsers() {
        usersButton.click();
    }
}
