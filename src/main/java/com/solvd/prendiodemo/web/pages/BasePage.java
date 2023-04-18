package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.prendiodemo.utils.ElementsUtil;
import com.solvd.prendiodemo.web.components.NavigationMenu;
import com.solvd.prendiodemo.web.components.NavigationTabs;
import com.solvd.prendiodemo.web.components.SearchBlock;
import com.solvd.prendiodemo.web.components.UserPhotoBlock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import static com.solvd.prendiodemo.constants.Constants.LOADING_BLOCK_APPEAR_TIMEOUT;

public class BasePage extends AbstractPage implements ElementsUtil {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(className = "logo_block")
    protected ExtendedWebElement logoBlock;

    @FindBy(className = "searchblock")
    protected SearchBlock searchBlock;

    @FindBy(className = "userstatusmenu_block")
    protected UserPhotoBlock userPhotoBlock;

    @FindBy(xpath = "//div[@id='myNavbar']")
    protected NavigationMenu navigationMenu;

    @FindBy(className = "rtabs")
    protected NavigationTabs navigationTabs;

    @FindBy(className = "successmsg")
    protected ExtendedWebElement successMessage;

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
        return navigationMenu.clickDashboardButton();
    }

    public BuyerPage clickBuyerTab() {
        return navigationMenu.clickBuyerButton();
    }

    public ReceiverPage clickReceiverTab() {
        return navigationMenu.clickReceiverButton();
    }

    public AccountPayablePage clickAccountsPayableTab() {
        return navigationMenu.clickAccountsPayableButton();
    }

    public NavigationTabs getNavigationTabs() {
        return navigationTabs;
    }

    public SearchResultPage searchCatalog(String query) {
        return searchBlock.search(query);
    }

    public void ensureLoaded() {
        waitUntil(ExpectedConditions.elementToBeClickable(loadingBlock.getElement()), LOADING_BLOCK_APPEAR_TIMEOUT);
        loadingBlock.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public String getFullName() {
        return userPhotoBlock.openProfile().getFullName();
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
