package com.solvd.prendiodemo.web.pages.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.buyer.AddSupplierPopup;
import com.solvd.prendiodemo.web.components.common.SearchFilter;
import com.solvd.prendiodemo.web.components.common.TableEntry;
import com.solvd.prendiodemo.web.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

public class BuyerSuppliersPage extends BasePage {

    @FindBy(xpath = "//li[a[text()='Suppliers'] and @class='selected']")
    private ExtendedWebElement suppliersTabActive;

    @FindBy(id = "Add_Supplier")
    private ExtendedWebElement addSupplierButton;

    @FindBy(xpath = "//div[@id='popupform']")
    private AddSupplierPopup addSupplierPopup;

    @FindBy(xpath = "//div[@id='divsuppliers']")
    private SearchFilter searchFilter;

    @FindBy(xpath = "//table[@id='tblsuplist']/tbody/tr")
    private List<TableEntry> suppliers;

    public BuyerSuppliersPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(suppliersTabActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public AddSupplierPopup clickAddSupplierButton() {
        addSupplierButton.click();
        return addSupplierPopup;
    }

    public BuyerSuppliersPage searchSupplierByName(String name) {
        searchFilter.search(name);
        return new BuyerSuppliersPage(getDriver());
    }

    private Optional<TableEntry> findSupplierEntryByName(String name) {
        return suppliers.stream()
                .filter(supp -> supp.getContainerName().getText().equals(name))
                .findFirst();
    }

    public boolean isSupplierFound(String name) {
        return findSupplierEntryByName(name).isPresent();
    }

    public AddSupplierPopup editSupplierByName(String name) {
        findSupplierEntryByName(name)
                .orElseThrow()
                .clickEditIcon();
        return addSupplierPopup;
    }

    public AddSupplierPopup getAddSupplierPopup() {
        return addSupplierPopup;
    }
}
