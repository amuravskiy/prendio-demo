package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.utils.ElementsUtil;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CalendarForm extends AbstractUIObject implements ElementsUtil {

    @FindBy(xpath = "//table//a[@href]")
    private ExtendedWebElement firstAvailableDay;

    @FindBy(xpath = "//a[@title='Next']")
    private ExtendedWebElement nextMonthButton;

    @FindBy(xpath = "//a[text()='%d']")
    private ExtendedWebElement dayToClick;

    public CalendarForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void waitDateToBeVisible() {
        waitUntil(ExpectedConditions.elementToBeClickable(firstAvailableDay.getElement()), EXPLICIT_TIMEOUT);
    }

    public void clickFirstAvailableDateButon() {
        firstAvailableDay.click();
    }

    public void clickSpecificDayButton(int day) {
        dayToClick.format(day).click();
    }

    public void clickNextMonthButton() {
        nextMonthButton.click();
    }
}
