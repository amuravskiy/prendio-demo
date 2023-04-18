package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.domain.WatcherInfo;
import com.solvd.prendiodemo.web.components.BasePopup;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DepWatcherSetupPopup extends BasePopup {

    @FindBy(xpath = "//select[@id='selectwatcher']")
    private ExtendedWebElement watcherSelect;

    @FindBy(xpath = "//input[@id='deptapproveliit']")
    private ExtendedWebElement notifyAtField;

    @FindBy(id = "Savedeptwatch")
    private ExtendedWebElement saveButton;

    public DepWatcherSetupPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public WatcherInfo selectFirstWatcher() {
        waitUntil(ExpectedConditions.textToBePresentInElement(watcherSelect.getElement(), "Select"), EXPLICIT_TIMEOUT);
        selectByIndex(watcherSelect, 1);
        WatcherInfo watcherInfo = WatcherInfo.builder()
                .watcherName(watcherSelect.getSelectedValue())
                .notifyAt(String.valueOf(RandomUtils.nextInt(2, 10000)))
                .build();
        notifyAtField.type(watcherInfo.getNotifyAt());
        return watcherInfo;
    }

    public void clickSaveButton() {
        saveButton.click();
    }
}
