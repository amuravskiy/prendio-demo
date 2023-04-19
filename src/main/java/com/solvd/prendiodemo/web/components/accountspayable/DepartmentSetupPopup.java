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

public class DepartmentSetupPopup extends BasePopup {

    @FindBy(id = "txtdepname")
    private ExtendedWebElement departmentNameField;

    @FindBy(id = "txtdepdesc")
    private ExtendedWebElement departmentDescriptionField;

    @FindBy(id = "txtdepnotes")
    private ExtendedWebElement departmentNotesField;

    @FindBy(xpath = "//span[@id='deptwatchercount']//..")
    private ExtendedWebElement watchersBlock;

    @FindBy(xpath = "//span[@id='deptwatchercount']")
    private ExtendedWebElement watchersCountBlock;

    @FindBy(id = "Add_DeptWatcher")
    private ExtendedWebElement addWatcherButton;

    @FindBy(xpath = "//div[@id='divdeptWatcher']//td[@class='tdempty']")
    private ExtendedWebElement emptyWatchersTableMarker;

    @FindBy(xpath = "//div[@id='popupform1' and contains(@class, 'addprowatcherpopup')]")
    private DepartmentWatcherSetupPopup departmentWatcherSetupPopup;

    @FindBy(xpath = "//table[@id='tbldeptwatcher']/tbody/tr")
    private WatchersTableEntry firstWatcher;

    @FindBy(xpath = "//div[@id='autoconfirm']")
    private BasePopup confirmationPopup;

    @FindBy(id = "SaveDepartment")
    private ExtendedWebElement saveButton;

    @FindBy(xpath = "//div[contains(@class,'departmentpopup')]")
    private DepartmentUserPopup departmentUserPopup;

    public DepartmentSetupPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public DepartmentInfo fillFieldsRandomly() {
        departmentNameField.type(RandomStringUtils.randomAlphabetic(10));
        departmentDescriptionField.type(RandomStringUtils.randomAlphabetic(20));
        departmentNotesField.type(RandomStringUtils.randomAlphabetic(30));
        return getInfo();
    }

    public void clickWatchersSection() {
        getPopupLeftMenu().clickWatchersSection();
        waitUntil(ExpectedConditions.textToBePresentInElement(watchersBlock.getElement(), "("), EXPLICIT_TIMEOUT);
    }

    public DepartmentUserPopup clickUsersButton() {
        getPopupLeftMenu().clickUsersSection();
        ensureLoaded();
        return departmentUserPopup;
    }

    public String getWatchersText() {
        return watchersBlock.getText() + watchersCountBlock.getAttribute("innerText");
    }

    public boolean isWatchersTableEmpty() {
        return emptyWatchersTableMarker.isPresent(0);
    }

    public DepartmentWatcherSetupPopup clickAddWatcherButton() {
        addWatcherButton.click();
        return departmentWatcherSetupPopup;
    }

    public BasePopup close() {
        super.clickCloseButton();
        return confirmationPopup;
    }

    public void clickSaveButton() {
        saveButton.click();
    }

    public DepartmentInfo getInfo() {
        return DepartmentInfo.builder()
                .name(getValue(departmentNameField))
                .description(getValue(departmentDescriptionField))
                .notes(getValue(departmentNotesField))
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
