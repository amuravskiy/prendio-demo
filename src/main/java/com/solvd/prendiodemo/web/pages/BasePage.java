package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.prendiodemo.utils.ElementsUtil;
import com.solvd.prendiodemo.web.components.common.SearchBlock;
import com.solvd.prendiodemo.web.components.common.UserPhotoBlock;
import com.solvd.prendiodemo.web.components.receiver.NavigationMenu;
import com.solvd.prendiodemo.web.components.receiver.NavigationTabs;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

import static com.solvd.prendiodemo.constants.Constants.LOADING_BLOCK_APPEAR_TIMEOUT;

public class BasePage extends AbstractPage implements ElementsUtil {

    @FindBy(className = "searchblock")
    private SearchBlock searchBlock;

    @FindBy(className = "userstatusmenu_block")
    private UserPhotoBlock userPhotoBlock;

    @FindBy(xpath = "//div[@id='myNavbar']")
    private NavigationMenu navigationMenu;

    @FindBy(className = "rtabs")
    private NavigationTabs navigationTabs;

    @FindBy(className = "successmsg")
    private ExtendedWebElement successMessage;

    @FindBy(className = "errormsg")
    private ExtendedWebElement errorMessage;

    @FindBy(className = "HiderText")
    private ExtendedWebElement loadingBlock;

    @FindBy(className = "ooolink")
    private ExtendedWebElement outLink;

    public BasePage(WebDriver driver) {
        super(driver);
        ensureLoaded();
    }

    public void switchToTab(int index) {
        List<String> tabHandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabHandles.get(index));
    }

    public DashboardPage clickDashboardTab() {
        return getNavigationMenu().clickDashboardButton();
    }

    public BuyerPage clickBuyerTab() {
        return getNavigationMenu().clickBuyerButton();
    }

    public ReceiverPage clickReceiverTab() {
        return getNavigationMenu().clickReceiverButton();
    }

    public AccountPayablePage clickAccountsPayableTab() {
        return getNavigationMenu().clickAccountsPayableButton();
    }

    public NavigationMenu getNavigationMenu() {
        return navigationMenu;
    }

    public NavigationTabs getNavigationTabs() {
        return navigationTabs;
    }

    public SearchResultPage searchCatalog(String query) {
        return searchBlock.search(query);
    }

    public void ensureLoaded() {
        if (waitUntil(ExpectedConditions.elementToBeClickable(loadingBlock.getElement()), LOADING_BLOCK_APPEAR_TIMEOUT)) {
            loadingBlock.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
        }
    }

    public String getCurrentUserFullName() {
        return userPhotoBlock.openProfile().getCurrentUserFullName();
    }

    public void waitSuccessMessageVisible() {
        waitToBeVisible(successMessage);
    }

    public UserPhotoBlock getUserPhotoBlock() {
        return userPhotoBlock;
    }

    public boolean isOutLinkVisible() {
        return outLink.isVisible();
    }

    public boolean isSuccessMessageVisible() {
        return successMessage.isVisible();
    }

    public boolean isErrorMessagePresent() {
        return waitUntil(ExpectedConditions.presenceOfElementLocated(errorMessage.getBy()), LOADING_BLOCK_APPEAR_TIMEOUT);
    }

    public String getSuccessMessageText() {
        return successMessage.getText();
    }

    public void waitToBeClickable(ExtendedWebElement element) {
        waitUntil(ExpectedConditions.elementToBeClickable(element.getElement()), EXPLICIT_TIMEOUT);
    }

    public void waitToBeVisible(ExtendedWebElement element) {
        waitUntil(ExpectedConditions.elementToBeClickable(element.getElement()), EXPLICIT_TIMEOUT);
    }
}
