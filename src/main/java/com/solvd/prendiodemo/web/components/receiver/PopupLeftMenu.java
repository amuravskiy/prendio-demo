package com.solvd.prendiodemo.web.components.receiver;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.domain.PopupSections;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class PopupLeftMenu extends AbstractUIObject {

    @FindBy(xpath = ".//a[@type='account']")
    private ExtendedWebElement accountNumbersButton;

    @FindBy(xpath = ".//a[@type='catalog']")
    private ExtendedWebElement catalogItemsButton;

    @FindBy(xpath = ".//a[@type='deptWatcher']")
    private ExtendedWebElement watchersButton;

    @FindBy(xpath = ".//a[@type and text()='Users']")
    private ExtendedWebElement usersButton;

    public PopupLeftMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickPopupSection(PopupSections popupSectionToClick) {
        switch (popupSectionToClick) {
            case ACCOUNT_NUMBERS:
                accountNumbersButton.click();
                break;
            case CATALOG_ITEMS:
                catalogItemsButton.click();
                break;
            case WATCHERS:
                watchersButton.click();
                break;
            case USERS:
                usersButton.click();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + popupSectionToClick);
        }
    }
}
