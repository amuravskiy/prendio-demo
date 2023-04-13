package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.web.components.BasePopup;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DepUserPopup extends BasePopup {

    @FindBy(xpath = "//table[@id='tbldeptuser']//input[@class='memberdeptchk']")
    private List<ExtendedWebElement> membersCheckboxes;

    @FindBy(xpath = "//table[@id='tbldeptuser']//input[@class='memberdeptchk']//..//..//td[1]")
    private List<ExtendedWebElement> checkboxUsername;

    public DepUserPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String selectAnyUser() {
        ensureLoaded();
        ExtendedWebElement toCheck = membersCheckboxes
                .stream()
                .filter(checkbox -> waitUntil(ExpectedConditions.elementToBeClickable(checkbox.getElement()), 0))
                .findFirst()
                .orElseThrow();
        toCheck.clickByJs();
        return getSelectedUserName();
    }

    public String getSelectedUserName() {
        ExtendedWebElement selected = membersCheckboxes
                .stream()
                .filter(checkbox -> waitUntil(ExpectedConditions.elementToBeClickable(checkbox.getElement()), 0))
                .filter(checkbox -> checkbox.getElement().isSelected())
                .findAny()
                .orElseThrow();
        return membersCheckboxes.stream()
                .filter(checkbox -> checkbox.getElement().getRect().equals(selected.getElement().getRect()))
                .findFirst()
                .orElseThrow()
                .getText();
    }
}
