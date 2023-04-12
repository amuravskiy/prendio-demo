package com.solvd.prendiodemo.web.components.receiver;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ScanItemContainer extends AbstractUIObject {

    @FindBy(className = "divselected")
    private ExtendedWebElement checkbox;

    public ScanItemContainer(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void selectItem() {
        checkbox.click();
    }
}
