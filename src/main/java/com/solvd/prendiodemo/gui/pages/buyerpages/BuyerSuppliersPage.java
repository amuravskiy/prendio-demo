package com.solvd.prendiodemo.gui.pages.buyerpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.AddSupplierPopup;
import com.solvd.prendiodemo.gui.components.TableEntry;
import com.solvd.prendiodemo.gui.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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

    public void fillRequiredFields() {
        addSupplierPopup.fillRequiredFields();
    }

    public BuyerSuppliersPage search(String query) {
        searchField.type(query);
        searchField.sendKeys(Keys.ENTER);
        return new BuyerSuppliersPage(driver);
    }

    public AddSupplierPopup editSupplierByName(String name) {
        suppliers.stream()
                .filter(supp -> supp.getNameContainer().getText().equals(name))
                .findFirst()
                .orElseThrow()
                .getEditIcon()
                .click();
        return addSupplierPopup;
    }

    public AddSupplierPopup getAddSupplierPopup() {
        return addSupplierPopup;
    }
}
