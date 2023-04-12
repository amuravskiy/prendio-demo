package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.pages.accountspayable.AccountsPayableSuppliersPage;
import com.solvd.prendiodemo.web.pages.accountspayable.DepartmentPage;
import com.solvd.prendiodemo.web.pages.accountspayable.VouchersPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class AccountPayablePage extends BasePage {

    @FindBy(xpath = "//li[@id and @class='active' and a[text()='Accounts Payable']]")
    private ExtendedWebElement accountsPayableActive;

    @FindBy(xpath = "//li[a[text()='Department']]")
    private ExtendedWebElement departmentButton;

    public AccountPayablePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(accountsPayableActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public AccountsPayableSuppliersPage clickSuppliers() {
        navigationTabs.clickSuppliers();
        return new AccountsPayableSuppliersPage(getDriver());
    }

    public VouchersPage clickVouchers() {
        navigationTabs.clickVouchers();
        return new VouchersPage(getDriver());
    }

    public DepartmentPage clickDepartment() {
        departmentButton.click();
        return new DepartmentPage(getDriver());
    }
}
