package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class VoucherEntry extends AbstractUIObject {

    @FindBy(xpath = ".//td[@id='filterdate']")
    private ExtendedWebElement date;

    @FindBy(xpath = ".//td[@id='filterinvoice']")
    private ExtendedWebElement invNumber;

    public VoucherEntry(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getDateText() {
        return date.getText();
    }

    public String getInvNumberText() {
        return invNumber.getText();
    }
}
