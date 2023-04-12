package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.prendiodemo.web.components.NavigationMenu;
import com.solvd.prendiodemo.web.components.NavigationTabs;
import com.solvd.prendiodemo.web.components.SearchBlock;
import com.solvd.prendiodemo.web.components.UserPhotoBlock;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class BasePage extends AbstractPage {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final long LOADING_BLOCK_APPEAR_TIMEOUT = R.CONFIG.getLong("loading_block_appear_timeout");

    @FindBy(className = "logo_block")
    protected ExtendedWebElement logoBlock;

    @FindBy(className = "searchblock")
    protected SearchBlock searchBlock;

    @FindBy(className = "userstatusmenu_block")
    protected UserPhotoBlock userPhotoBlock;

    @FindBy(className = "navigationmenu")
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

    public DashboardPage clickDashboard() {
        return navigationMenu.clickDashboardButton();
    }

    public BuyerPage clickBuyer() {
        return navigationMenu.clickBuyerButton();
    }

    public ReceiverPage clickReceiver() {
        return navigationMenu.clickReceiverButton();
    }

    public AccountPayablePage clickAccountsPayable() {
        return navigationMenu.clickAccountsPayableButton();
    }

    public NavigationTabs getNavigationTabs() {
        return navigationTabs;
    }

    public SearchResultPage searchCatalog(String query) {
        return searchBlock.search(query);
    }

    public void ensureLoaded() {
        loadingBlock.isVisible(LOADING_BLOCK_APPEAR_TIMEOUT);
        loadingBlock.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public String getFullName() {
        String fullName = userPhotoBlock.openProfile().getFullName();
        LOGGER.info("Fullname from user profile: " + fullName);
        return fullName;
    }

    public void waitSuccessMessageVisible() {
        waitUntil(ExpectedConditions.visibilityOf(successMessage.getElement()), EXPLICIT_TIMEOUT);
    }

    public UserPhotoBlock getUserPhotoBlock() {
        return userPhotoBlock;
    }

    public boolean isOutLinkVisible() {
        return outLink.isVisible();
    }

    public void assertSuccessMessageVisibleWithText(String expectedText, SoftAssert softAssert) {
        softAssert.assertTrue(successMessage.isVisible(), "Success message is not visible");
        softAssert.assertEquals(successMessage.getText(), expectedText);
    }
}
