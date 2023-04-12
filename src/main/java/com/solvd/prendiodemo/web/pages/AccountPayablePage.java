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
        navigationTabs.clickTabByName("Suppliers");
         return new AccountsPayableSuppliersPage(driver);
    }

    public VouchersPage clickVouchers() {
        navigationTabs.clickTabByName("Vouchers");
         return new VouchersPage(driver);
    }

    public DepartmentPage clickDepartment() {
        departmentButton.click();
         return new DepartmentPage(driver);
    }
}
