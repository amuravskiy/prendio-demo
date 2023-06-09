package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.utils.ElementsUtil;
import com.solvd.prendiodemo.web.components.receiver.PopupLeftMenu;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static com.solvd.prendiodemo.constants.Constants.LOADING_BLOCK_APPEAR_TIMEOUT;

public class BasePopup extends AbstractUIObject implements ElementsUtil {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(className = "HiderText")
    private ExtendedWebElement loadingBlock;

    @FindBy(id = "popupheader")
    private ExtendedWebElement popupHeader;

    @FindBy(className = "popupbtn_close")
    private ExtendedWebElement closeButton;

    @FindBy(xpath = "//ul[@class='ulpopupleftmenu']")
    private PopupLeftMenu popupLeftMenu;

    @FindBy(xpath = "..//div[@class='popuplast_div' and contains(text(), 'Created')]")
    private ExtendedWebElement createdTrail;

    @FindBy(xpath = "//input[@value='OK' or @value='Ok' or @value='Yes' or @value='Submit']")
    private ExtendedWebElement confirmationButton;

    public BasePopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setUiLoadedMarker(popupHeader);
        setLoadingStrategy(ElementLoadingStrategy.BY_VISIBILITY);
        ensureLoaded();
    }

    public void ensureLoaded() {
        waitUntil(ExpectedConditions.elementToBeClickable(loadingBlock.getElement()), LOADING_BLOCK_APPEAR_TIMEOUT);
        loadingBlock.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public void waitJsToLoad() {
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return ((Long) ((JavascriptExecutor) getDriver()).executeScript("return jQuery.active") == 0);
            } catch (Exception ignore) {
                return false;
            }
        };
        waitUntil(jQueryLoad, EXPLICIT_TIMEOUT);
    }

    public String getHeaderText() {
        return popupHeader.getText();
    }

    public void clickCloseButton() {
        closeButton.click();
    }

    public PopupLeftMenu getPopupLeftMenu() {
        return popupLeftMenu;
    }

    public String getCreatedTrailText() {
        String text = createdTrail.getText();
        LOGGER.info("Trail record text: " + createdTrail.getText());
        return text;
    }

    public boolean isVisible() {
        return getRootExtendedElement().isVisible();
    }

    public boolean isDisappeared() {
        return getRootExtendedElement().waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public void clickConfirmationButton() {
        confirmationButton.click();
    }
}
