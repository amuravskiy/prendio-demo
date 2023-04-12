package com.solvd.prendiodemo.web.pages.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.buyer.AddSupplierPopup;
import com.solvd.prendiodemo.web.components.TableEntry;
import com.solvd.prendiodemo.web.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Optional;

public class BuyerSuppliersPage extends BasePage {

    @FindBy(xpath = "//li[a[text()='Suppliers'] and @class='selected']")
    private ExtendedWebElement suppliersTabActive;

    @FindBy(id = "Add_Supplier")
    private ExtendedWebElement addSupplierButton;

    @FindBy(xpath = "//div[@id='popupform']")
    private AddSupplierPopup addSupplierPopup;

    @FindBy(xpath = "//div[@id='divsuppliers']//input[@placeholder='<filter list>']")
    private ExtendedWebElement searchField;

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

    public BuyerSuppliersPage search(String query) {
        searchField.type(query);
        searchField.sendKeys(Keys.ENTER);
        return new BuyerSuppliersPage(getDriver());
    }

    private Optional<TableEntry> findSupplierEntryByName(String name) {
        return suppliers.stream()
                .filter(supp -> supp.getNameContainer().getText().equals(name))
                .findFirst();
    }

    public void assertSupplierFound(String name) {
        Assert.assertTrue(findSupplierEntryByName(name).isPresent(), "Supplier with name " + name + " not found");
    }

    public AddSupplierPopup editSupplierByName(String name) {
        findSupplierEntryByName(name)
                .orElseThrow()
                .getEditIcon()
                .click();
        return addSupplierPopup;
    }

    public AddSupplierPopup getAddSupplierPopup() {
        return addSupplierPopup;
    }
}
