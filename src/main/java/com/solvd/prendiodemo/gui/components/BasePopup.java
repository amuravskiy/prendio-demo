package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class BasePopup extends AbstractUIObject {

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

    @FindBy(id = "Savedeptwatch")
    private ExtendedWebElement saveButton;

    public BasePopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setUiLoadedMarker(popupHeader);
        setLoadingStrategy(ElementLoadingStrategy.BY_VISIBILITY);
        ensureLoaded();
    }

    public void ensureLoaded() {
        loadingBlock.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public String getHeaderText() {
        return popupHeader.getText();
    }

    public void clickClose() {
        closeButton.click();
    }

    public void clickSave() {
        saveButton.click();
    }

    public PopupLeftMenu getPopupLeftMenu() {
        return popupLeftMenu;
    }

    public String getCreatedTrailText() {
        return createdTrail.getText();
    }

    public boolean isVisible() {
        return getRootExtendedElement().isVisible();
    }

    public boolean isDisappeared() {
        return this.getRootExtendedElement().waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }
}
