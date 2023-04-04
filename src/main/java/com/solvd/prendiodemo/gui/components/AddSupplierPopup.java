package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.gui.pages.buyerpages.AddAccountNumbersPopup;
import com.solvd.prendiodemo.utils.Util;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddSupplierPopup extends BasePopup {

    private final String partialLineId = "stxtstreet";
    @FindBy(id = "txtsuppliername")
    private ExtendedWebElement supplierNameField;
    @FindBy(id = "shippingmethod")
    private ExtendedWebElement shippingMethodSelect;
    @FindBy(id = "prepaidfreight")
    private ExtendedWebElement prepaidFreightSelect;
    @FindBy(id = "paymentterms")
    private ExtendedWebElement paymentTermsSelect;
    @FindBy(id = "fob")
    private ExtendedWebElement FOBSelect;
    @FindBy(id = "txtemail")
    private ExtendedWebElement emailField;
    @FindBy(id = "defaultuserphone")
    private ExtendedWebElement defaultUserPhoneField;
    @FindBy(id = "stxtstreet1")
    private ExtendedWebElement addressLine1;
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
    @FindBy(id = "tdaddress")
    private ExtendedWebElement firstShipToAddress;

    @FindBy(xpath = "//td[@id='tdaddress']//following::td[1]")
    private ExtendedWebElement firstAccountNumber;

    public AddSupplierPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String fillRequiredFields() {
        String supplierName = RandomStringUtils.randomAlphabetic(10);
        supplierNameField.type(supplierName);
        List.of(shippingMethodSelect, prepaidFreightSelect, paymentTermsSelect, FOBSelect)
                .forEach(select -> Util.selectByIndex(select, 1));
        emailField.type(RandomStringUtils.randomAlphabetic(5) + "@" +
                RandomStringUtils.randomAlphabetic(4) + "." +
                RandomStringUtils.randomAlphabetic(3));
        defaultUserPhoneField.type(RandomStringUtils.randomNumeric(10));
        addressLine1.type(RandomStringUtils.randomAlphabetic(20));
        return supplierName;
    }

    public Map<String, String> fillInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", RandomStringUtils.randomAlphabetic(10));
        supplierNameField.type(info.get("name"));
        List.of(shippingMethodSelect, prepaidFreightSelect, paymentTermsSelect, FOBSelect)
                .forEach(select -> Util.selectByIndex(select, 1));
        info.put("shippingMethod", Util.getSelectedOptionText(shippingMethodSelect));
        info.put("repaidFreight", Util.getSelectedOptionText(prepaidFreightSelect));
        info.put("paymentTerms", Util.getSelectedOptionText(paymentTermsSelect));
        info.put("FOB", Util.getSelectedOptionText(FOBSelect));
        String email = RandomStringUtils.randomAlphabetic(5) + "@" +
                RandomStringUtils.randomAlphabetic(4) + "." +
                RandomStringUtils.randomAlphabetic(3);
        info.put("email", email);
        emailField.type(email);
        info.put("defaultServicePhone", RandomStringUtils.randomNumeric(10));
        defaultUserPhoneField.type(info.get("defaultServicePhone"));
        info.put("remitName", RandomStringUtils.randomAlphabetic(20));
        remitNameField.type(info.get("remitName"));
        for (int i = 1; i < 5; i++) {
            info.put("line" + i, RandomStringUtils.randomAlphabetic(20));
            findExtendedWebElement(By.id(partialLineId + i)).type(info.get("line" + i));
        }
        info.put("city", RandomStringUtils.randomAlphabetic(20));
        cityField.type(info.get("city"));
        info.put("state", RandomStringUtils.randomAlphabetic(20));
        stateField.type(info.get("state"));
        info.put("zip", RandomStringUtils.randomAlphabetic(20));
        zipCodeField.type(info.get("zip"));
        info.put("notes", RandomStringUtils.randomAlphabetic(50));
        notesField.type(info.get("notes"));
        return info;
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

    public Map<String, String> getFullInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", supplierNameField.getAttribute("value"));
        info.put("shippingMethod", Util.getSelectedOptionText(shippingMethodSelect));
        info.put("repaidFreight", Util.getSelectedOptionText(prepaidFreightSelect));
        info.put("paymentTerms", Util.getSelectedOptionText(paymentTermsSelect));
        info.put("FOB", Util.getSelectedOptionText(FOBSelect));
        info.put("email", emailField.getAttribute("value"));
        info.put("defaultServicePhone", defaultUserPhoneField.getAttribute("value").replaceAll("\\D",""));
        info.put("remitName", remitNameField.getAttribute("value"));
        for (int i = 1; i < 5; i++) {
            info.put("line" + i, findExtendedWebElement(By.id(partialLineId + i)).getAttribute("value"));
        }
        info.put("city", cityField.getAttribute("value"));
        info.put("state", stateField.getAttribute("value"));
        info.put("zip", zipCodeField.getAttribute("value"));
        info.put("notes", notesField.getAttribute("value"));
        getPopupLeftMenu().clickTabByName("Account Numbers");
        firstShipToAddress.isVisible();
        info.put("shipToLine2", firstShipToAddress.getText().split(",")[0]);
        info.put("accountNumber", firstAccountNumber.getText());
        return info;
    }
}
