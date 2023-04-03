package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.values.DepInfo;
import com.solvd.prendiodemo.values.WatcherInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class DepartmentSetupPopup extends BasePopup {

    private final By usernameOfTheCheckboxLocator = By.xpath("..//..//..//td");

    private final By membersCheckboxesLocator = By.xpath("//table[@id='tbldeptuser']//input[@class='memberdeptchk']");

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
    private YesNoPopup yesNoPopup;

    @FindBy(id = "SaveDepartment")
    private ExtendedWebElement saveButton;

    public DepartmentSetupPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public DepInfo fillFields() {
        DepInfo depInfo = new DepInfo(RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(20),
                RandomStringUtils.randomAlphabetic(30));
        depNameField.type(depInfo.getName());
        depDescField.type(depInfo.getDesc());
        depNotesField.type(depInfo.getNotes());
        return depInfo;
    }

    public void clickWatchers() {
        getPopupLeftMenu().clickTabByName("Watchers");
    }

    public void clickUsers() {
        getPopupLeftMenu().clickTabByName("Users");
        ensureLoaded();
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

    public YesNoPopup close() {
        super.clickClose();
        return yesNoPopup;
    }

    public void clickSave() {
        saveButton.click();
    }

    public String selectAnyUser() {
        ensureLoaded();
        ExtendedWebElement toCheck = findExtendedWebElements(membersCheckboxesLocator)
                .stream()
                .filter(ExtendedWebElement::isClickable)
                .findAny()
                .orElseThrow();
        toCheck.clickByJs();
        return toCheck.findExtendedWebElement(usernameOfTheCheckboxLocator).getText();
    }

    public DepInfo getInfo() {
        return new DepInfo(depNameField.getAttribute("value"),
                depDescField.getAttribute("value"),
                depNotesField.getAttribute("value"));
    }

    public WatcherInfo getWatcherInfo() {
        String watchedNotifyAtInteger = firstWatcherNotifyAt.getText().replace(".00", "").replace(",", "");
        return new WatcherInfo(firstWatcherName.getText(), watchedNotifyAtInteger);
    }

    public String getSelectedUserName() {
        ExtendedWebElement selected = findExtendedWebElements(membersCheckboxesLocator)
                .stream()
                .filter(ExtendedWebElement::isClickable)
                .filter(ExtendedWebElement::isChecked)
                .findAny()
                .orElseThrow();
        return selected.findExtendedWebElement(usernameOfTheCheckboxLocator).getText();
    }
}
