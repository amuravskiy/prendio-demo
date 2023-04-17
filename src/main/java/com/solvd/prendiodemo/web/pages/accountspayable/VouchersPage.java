package com.solvd.prendiodemo.web.pages.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.accountspayable.VoucherEntry;
import com.solvd.prendiodemo.web.pages.AccountPayablePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

public class VouchersPage extends AccountPayablePage {

    @FindBy(xpath = "//li//a[text()='Vouchers']")
    private ExtendedWebElement vouchersSectionActive;

    @FindBy(xpath = "//tr[@ponumber and not(@style='display: none;')]")
    private List<VoucherEntry> vouchers;

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
        return new VouchersPage(getDriver());
    }

    private Optional<VoucherEntry> findVoucherEntryByInvNumber(String invNumber) {
        return vouchers.stream()
                .filter(voucher -> voucher.getInvNumberText().equals(invNumber))
                .findFirst();
    }

    public boolean isVoucherFound(String invNumber) {
        return findVoucherEntryByInvNumber(invNumber).isPresent();
    }

    public VoucherEntry getVoucherEntryByInvNumber(String invNumber) {
        return findVoucherEntryByInvNumber(invNumber).orElseThrow();
    }
}
