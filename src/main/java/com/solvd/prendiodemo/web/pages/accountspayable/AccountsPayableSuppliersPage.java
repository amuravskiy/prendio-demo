package com.solvd.prendiodemo.web.pages.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.BasePopup;
import com.solvd.prendiodemo.web.components.SearchFilter;
import com.solvd.prendiodemo.web.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AccountsPayableSuppliersPage extends BasePage {

    @FindBy(xpath = "//tr[@class='trhighlight']//td//a[text()]")
    private List<ExtendedWebElement> supplierNames;

    @FindBy(id = "popupform")
    private BasePopup supplierInfoPopup;

    @FindBy(xpath = "//li[a[text()='Suppliers'] and @class='selected']")
    private ExtendedWebElement suppliersTabActive;

    @FindBy(xpath = "//div[@id='synsupplier']")
    private SearchFilter searchFilter;

    public AccountsPayableSuppliersPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(suppliersTabActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public BasePopup clickSupplierByName(String name) {
        supplierNames.stream()
                .filter(tab -> tab.getText().equals(name))
                .findFirst()
                .orElseThrow()
                .click();
        return supplierInfoPopup;
    }

    public AccountsPayableSuppliersPage search(String query) {
        searchFilter.search(query);
        return new AccountsPayableSuppliersPage(getDriver());
    }
}
