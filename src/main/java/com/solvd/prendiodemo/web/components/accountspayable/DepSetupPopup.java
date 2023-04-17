package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.domain.DepartmentInfo;
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

    @FindBy(xpath = "//table[@id='tbldeptwatcher']/tbody/tr")
    private WatchersTableEntry firstWatcher;

    @FindBy(xpath = "//div[h2[text()='CONFIRMATION']]")
    private BasePopup confirmationPopup;

    @FindBy(id = "SaveDepartment")
    private ExtendedWebElement saveButton;

    @FindBy(xpath = "//div[contains(@class,'departmentpopup')]")
    private DepUserPopup depUserPopup;

    public DepSetupPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public DepartmentInfo fillFieldsRandomly() {
        depNameField.type(RandomStringUtils.randomAlphabetic(10));
        depDescField.type(RandomStringUtils.randomAlphabetic(20));
        depNotesField.type(RandomStringUtils.randomAlphabetic(30));
        return getInfo();
    }

    public void clickWatchers() {
        getPopupLeftMenu().clickWatchers();
        waitUntil(ExpectedConditions.textToBePresentInElement(watchersBlock.getElement(), "("), EXPLICIT_TIMEOUT);
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


    public DepartmentInfo getInfo() {
        return DepartmentInfo.builder()
                .name(getValue(depNameField))
                .description(getValue(depDescField))
                .notes(getValue(depNotesField))
                .build();
    }

    public WatcherInfo getWatcherInfo() {
        String watcherNotifyAt = firstWatcher.getWatcherNotifyAt().replace(".00", "").replace(",", "");
        return WatcherInfo.builder()
                .watcherName(firstWatcher.getWatcherName())
                .notifyAt(watcherNotifyAt)
                .build();
    }
}
