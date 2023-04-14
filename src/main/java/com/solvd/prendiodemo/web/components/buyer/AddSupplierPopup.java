package com.solvd.prendiodemo.web.components.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.web.components.BasePopup;
import com.solvd.prendiodemo.web.components.TableEntry;
import com.solvd.prendiodemo.web.components.accountspayable.AccountNumbersTableEntry;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AddSupplierPopup extends BasePopup {

    @FindBy(id = "txtsuppliername")
    private ExtendedWebElement supplierNameField;

    @FindBy(id = "shippingmethod")
    private ExtendedWebElement shippingMethodSelect;

    @FindBy(id = "prepaidfreight")
    private ExtendedWebElement prepaidFreightSelect;

    @FindBy(id = "paymentterms")
    private ExtendedWebElement paymentTermsSelect;

    @FindBy(id = "fob")
    private ExtendedWebElement fobSelect;

    @FindBy(id = "txtemail")
    private ExtendedWebElement emailField;

    @FindBy(xpath = "//input[contains(@id,'stxtstreet')]")
    private List<ExtendedWebElement> addressLines;

    @FindBy(id = "defaultuserphone")
    private ExtendedWebElement defaultUserPhoneField;

    @FindBy(id = "Savesupplier")
    private ExtendedWebElement saveButton;

    @FindBy(id = "supname1")
    private ExtendedWebElement remitNameField;

    @FindBy(id = "stxtcity")
    private ExtendedWebElement cityField;

    @FindBy(id = "stxtstate")
    private ExtendedWebElement stateField;

    @FindBy(id = "stxtcode")
    private ExtendedWebElement zipCodeField;

    @FindBy(id = "textnotes")
    private ExtendedWebElement notesField;

    @FindBy(id = "tblaccount")
    private ExtendedWebElement accountNumbersSectionActive;

    @FindBy(id = "addaccount")
    private ExtendedWebElement addAccountButton;

    @FindBy(xpath = "//div[h2[text()='Add Account Numbers']]")
    private AddAccountNumbersPopup addAccountNumbersPopup;

    @FindBy(xpath = "//table[@id='tblaccount']//tbody//tr[1]")
    private AccountNumbersTableEntry firstAccountNumberTableEntry;

    @FindBy(id = "Inner_Add_Catalog")
    private ExtendedWebElement addItemButton;

    @FindBy(xpath = "..//div[@id='addsuppliercatelogitem']")
    private AddSupplierItemPopup addSupplierItemPopup;

    @FindBy(id = "tblsupcataloglist")
    private ExtendedWebElement catalogItemsSectionActive;

    @FindBy(xpath = "//table[@id='tblsupcataloglist']/tbody/tr")
    private TableEntry addedItem;

    public AddSupplierPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String fillRequiredFieldsRandomly() {
        String supplierName = RandomStringUtils.randomAlphabetic(10);
        supplierNameField.type(supplierName);
        List.of(shippingMethodSelect, prepaidFreightSelect, paymentTermsSelect, fobSelect)
                .forEach(select -> selectByIndex(select, 1));
        emailField.type(RandomStringUtils.randomAlphabetic(5) + "@" +
                RandomStringUtils.randomAlphabetic(4) + "." +
                RandomStringUtils.randomAlphabetic(3));
        defaultUserPhoneField.type(RandomStringUtils.randomNumeric(10));
        addressLines.get(0).type(RandomStringUtils.randomAlphabetic(20));
        return supplierName;
    }

    public Map<String, String> fillInfoRandomly() {
        supplierNameField.type(RandomStringUtils.randomAlphabetic(10));
        List.of(shippingMethodSelect, prepaidFreightSelect, paymentTermsSelect, fobSelect)
                .forEach(select -> selectByIndex(select, 1));
        String email = RandomStringUtils.randomAlphabetic(5) + "@" +
                RandomStringUtils.randomAlphabetic(4) + "." +
                RandomStringUtils.randomAlphabetic(3);
        emailField.type(email);
        defaultUserPhoneField.type(RandomStringUtils.randomNumeric(10));
        Stream.of(remitNameField, cityField, stateField, zipCodeField, notesField)
                .forEach(field -> field.type(RandomStringUtils.randomAlphabetic(20)));
        fillAddressLinesRandomly(addressLines);
        return getGeneralInfo();
    }

    public void clickSave() {
        saveButton.click();
    }

    public boolean isAccountsSectionDisplayed() {
        return accountNumbersSectionActive.isVisible();
    }

    public AddAccountNumbersPopup clickAdd() {
        addAccountButton.click();
        return addAccountNumbersPopup;
    }

    public Map<String, String> getGeneralInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", getValue(supplierNameField));
        info.put("shippingMethod", getSelectedOptionText(shippingMethodSelect));
        info.put("repaidFreight", getSelectedOptionText(prepaidFreightSelect));
        info.put("paymentTerms", getSelectedOptionText(paymentTermsSelect));
        info.put("FOB", getSelectedOptionText(fobSelect));
        info.put("email", getValue(emailField));
        info.put("defaultServicePhone", getValue(defaultUserPhoneField).replaceAll("\\D", ""));
        info.put("remitName", getValue(remitNameField));
        for (int i = 0; i < addressLines.size(); i++) {
            info.put("line" + (i + 1), getValue(addressLines.get(i)));
        }
        info.put("city", getValue(cityField));
        info.put("state", getValue(stateField));
        info.put("zip", getValue(zipCodeField));
        info.put("notes", getValue(notesField));
        return info;
    }

    public Map<String, String> getFullInfo() {
        Map<String, String> info = getGeneralInfo();
        getPopupLeftMenu().clickAccountNumbers();
        firstAccountNumberTableEntry.waitToBeClickable();
        info.put("shipToLine2", firstAccountNumberTableEntry.getShipToAddress().split(",")[0]);
        info.put("accountNumber", firstAccountNumberTableEntry.getAccountNumber());
        return info;
    }

    public AddSupplierPopup clickCatalogItems() {
        getPopupLeftMenu().clickCatalogItems();
        return this;
    }

    public AddSupplierItemPopup clickAddItem() {
        addItemButton.click();
        return addSupplierItemPopup;
    }

    public void assertCatalogItemsSectionOpened() {
        Assert.assertTrue(catalogItemsSectionActive.isVisible(), "Catalog Item section is not opened");
    }

    public AddSupplierItemPopup clickOnAddedItemEdit() {
        addedItem.getEditIcon().click();
        return addSupplierItemPopup;
    }
}
