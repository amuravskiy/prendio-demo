package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.domain.WatcherInfo;
import com.solvd.prendiodemo.web.components.BasePopup;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class DepWatcherSetupPopup extends BasePopup {

    @FindBy(id = "selectwatcher")
    private ExtendedWebElement watcherSelect;

    @FindBy(xpath = "//input[@id='deptapproveliit']")
    private ExtendedWebElement notifyAtField;

    @FindBy(id = "Savedeptwatch")
    private ExtendedWebElement saveButton;

    public DepWatcherSetupPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public WatcherInfo selectFirstWatcher() {
        selectByIndex(watcherSelect, 1);
        WatcherInfo watcherInfo = new WatcherInfo(watcherSelect.getSelectedValue(), String.valueOf(RandomUtils.nextInt(2, 10000)));
        notifyAtField.type(watcherInfo.getNotifyAt());
        return watcherInfo;
    }

    public void clickSave() {
        saveButton.click();
    }
}
