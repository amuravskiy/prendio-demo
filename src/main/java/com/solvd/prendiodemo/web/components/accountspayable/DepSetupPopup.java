package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.domain.DepInfo;
import com.solvd.prendiodemo.domain.WatcherInfo;
import com.solvd.prendiodemo.web.components.BasePopup;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DepSetupPopup extends BasePopup {

    @FindBy(id = "txtdepname")
    private ExtendedWebElement depNameField;

    @FindBy(id = "txtdepdesc")
    private ExtendedWebElement depDescField;

    @FindBy(id = "txtdepnotes")
    private ExtendedWebElement depNotesField;

    @FindBy(xpath = "//span[@id='deptwatchercount']//..")
    private ExtendedWebElement watchersBlock;

    @FindBy(xpath = "//span[@id='deptwatchercount']")
    private ExtendedWebElement watchersCountBlock;

    @FindBy(id = "Add_DeptWatcher")
    private ExtendedWebElement addWatcherButton;

    @FindBy(xpath = "//div[@id='divdeptWatcher']//td[@class='tdempty']")
    private ExtendedWebElement emptyWatchersTableMarker;

    @FindBy(xpath = "//div[h2[text()='Department Watcher Setup']]")
    private DepWatcherSetupPopup depWatcherSetupPopup;

    @FindBy(xpath = "//table[@id='tbldeptwatcher']//td[1]")
    private ExtendedWebElement firstWatcherName;

    @FindBy(xpath = "//table[@id='tbldeptwatcher']//td[3]")
    private ExtendedWebElement firstWatcherNotifyAt;

    @FindBy(xpath = "//div[h2[text()='CONFIRMATION']]")
    private BasePopup confirmationPopup;

    @FindBy(id = "SaveDepartment")
    private ExtendedWebElement saveButton;

    @FindBy(xpath = "//div[contains(@class,'departmentpopup')]")
    private DepUserPopup depUserPopup;

    public DepSetupPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public DepInfo fillFieldsRandomly() {
        DepInfo depInfo = new DepInfo(RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(20),
                RandomStringUtils.randomAlphabetic(30));
        depNameField.type(depInfo.getName());
        depDescField.type(depInfo.getDesc());
        depNotesField.type(depInfo.getNotes());
        return depInfo;
    }

    public void clickWatchers() {
        getPopupLeftMenu().clickWatchers();
        waitUntil(ExpectedConditions.visibilityOf(watchersBlock.getElement()), EXPLICIT_TIMEOUT);
    }

    public DepUserPopup clickUsers() {
        getPopupLeftMenu().clickUsers();
        ensureLoaded();
        return depUserPopup;
    }

    public String getWatchersText() {
        return watchersBlock.getText() + watchersCountBlock.getAttribute("innerText");
    }

    public boolean isWatchersTableEmpty() {
        return emptyWatchersTableMarker.isPresent(0);
    }

    public DepWatcherSetupPopup clickAddWatcher() {
        addWatcherButton.click();
        return depWatcherSetupPopup;
    }

    public BasePopup close() {
        super.clickClose();
        return confirmationPopup;
    }

    public void clickSave() {
        saveButton.click();
    }


    public DepInfo getInfo() {
        return new DepInfo(getValue(depNameField),
                getValue(depDescField),
                getValue(depNotesField));
    }

    public WatcherInfo getWatcherInfo() {
        String watchedNotifyAtInteger = firstWatcherNotifyAt.getText().replace(".00", "").replace(",", "");
        return new WatcherInfo(firstWatcherName.getText(), watchedNotifyAtInteger);
    }
}
