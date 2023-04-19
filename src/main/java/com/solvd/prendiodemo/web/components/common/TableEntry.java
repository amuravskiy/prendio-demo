package com.solvd.prendiodemo.web.components.common;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TableEntry extends AbstractUIObject {

    @FindBy(xpath = ".//td[1]")
    private ExtendedWebElement nameContainer;

    @FindBy(xpath = ".//a[@class='edit_icon']")
    private ExtendedWebElement editIcon;

    public TableEntry(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getContainerName() {
        return nameContainer;
    }

    public void clickEditIcon() {
        editIcon.click();
    }
}
