package com.solvd.prendiodemo.gui.pages.accountspayablepages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.pages.AccountPayablePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class VouchersPage extends AccountPayablePage {

    private final By invDateSelector = By.xpath(".//td[@id='filterdate']");

    private final By invNumberSelector = By.xpath(".//td[@id='filterinvoice']");

    @FindBy(xpath = "//li//a[text()='Vouchers']")
    private ExtendedWebElement vouchersSectionActive;

    @FindBy(xpath = "//tr[@ponumber and not(@style='display: none;')]")
    private ExtendedWebElement firstVoucherEntry;

    @FindBy(id = "voucherfilter")
    private ExtendedWebElement searchField;

    public VouchersPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(vouchersSectionActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public VouchersPage search(String query) {
        searchField.type(query);
        searchField.sendKeys(Keys.ENTER);
        return new VouchersPage(driver);
    }

    public String getFirstVoucherInvDateText() {
        return firstVoucherEntry.findExtendedWebElement(invDateSelector).getAttribute("innerText");
    }

    public String getFirstVoucherInvNumberText() {
        return firstVoucherEntry.findExtendedWebElement(invNumberSelector).getAttribute("innerText");
    }
}
