package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class WatchersTableEntry extends AbstractUIObject {

    @FindBy(xpath = "./td[1]")
    private ExtendedWebElement watcherName;

    @FindBy(xpath = "./td[3]")
    private ExtendedWebElement watcherNotifyAt;

    public WatchersTableEntry(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getWatcherName() {
        return watcherName.getText();
    }

    public String getWatcherNotifyAt() {
        return watcherNotifyAt.getText();
    }
}
