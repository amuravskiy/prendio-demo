package com.solvd.prendiodemo.gui.pages.accountspayablepages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.BasePopup;
import com.solvd.prendiodemo.gui.pages.BasePage;
import com.solvd.prendiodemo.utils.Util;
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

    @FindBy(xpath = "//div[@id='synsupplier']//input[@placeholder='<filter list>']")
    private ExtendedWebElement searchField;

    public AccountsPayableSuppliersPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(suppliersTabActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public BasePopup clickSupplierByName(String name) {
        Util.clickElementByName(supplierNames, name);
        return supplierInfoPopup;
    }

    public AccountsPayableSuppliersPage search(String query) {
        searchField.type(query);
        searchField.sendKeys(Keys.ENTER);
        return new AccountsPayableSuppliersPage(driver);
    }
}
