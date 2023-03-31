package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.utils.Util;
import com.solvd.prendiodemo.values.WatcherInfo;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class DepWatcherSetupPopup extends BasePopup {

    @FindBy(xpath = "//select[@id='selectwatcher']")
    private ExtendedWebElement watcherSelect;

    @FindBy(xpath = "//input[@id='deptapproveliit']")
    private ExtendedWebElement notifyAtField;

    public DepWatcherSetupPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public WatcherInfo fillWatcher() {
        Util.selectByIndex(watcherSelect, 1);
        WatcherInfo watcherInfo = new WatcherInfo(watcherSelect.getText(), String.valueOf(RandomUtils.nextInt(2, 10000)));
        notifyAtField.type(watcherInfo.getNotifyAt());
        return watcherInfo;
    }
}
