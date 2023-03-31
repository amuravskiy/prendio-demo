package com.solvd.prendiodemo.gui.pages.accountspayablepages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.utils.Util;
import com.solvd.prendiodemo.gui.components.BasePopup;
import com.solvd.prendiodemo.gui.pages.BasePage;
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

    public AccountsPayableSuppliersPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(suppliersTabActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public BasePopup clickSupplierByName(String name) {
        Util.clickElementByName(supplierNames, name);
        return supplierInfoPopup;
    }

    public String getCreatedTrailText() {
        return supplierInfoPopup.getCreatedTrailText();
    }
}
