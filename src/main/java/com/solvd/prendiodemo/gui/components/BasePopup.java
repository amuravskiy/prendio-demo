package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.lang.invoke.MethodHandles;

public class BasePopup extends AbstractUIObject {

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
        loadingBlock.isVisible(R.CONFIG.getLong("loading_block_appear_timeout"));
        loadingBlock.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public String getHeaderText() {
        return popupHeader.getText();
    }

    public void clickClose() {
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

    public void assertVisible() {
        Assert.assertTrue(getRootExtendedElement().isVisible(), "Popup \"" + getHeaderText() + "\" is not visible");
    }

    public void assertVisibleWithTitle(String expected) {
        Assert.assertTrue(getRootExtendedElement().isVisible(), "Popup \"" + getHeaderText() + "\" is not visible");
        Assert.assertEquals(getHeaderText(), expected);
    }

    public boolean isVisible() {
        return getRootExtendedElement().isVisible();
    }

    public void assertDisappeared() {
        Assert.assertTrue(this.getRootExtendedElement().waitUntilElementDisappear(EXPLICIT_TIMEOUT));
    }

    public void clickConfirmationButton() {
        confirmationButton.click();
    }
}
