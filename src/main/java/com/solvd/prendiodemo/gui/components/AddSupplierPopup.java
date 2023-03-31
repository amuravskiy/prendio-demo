package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.utils.Util;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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
    private ExtendedWebElement FOBSelect;

    @FindBy(id = "txtemail")
    private ExtendedWebElement emailField;

    @FindBy(id = "defaultuserphone")
    private ExtendedWebElement defaultUserPhoneField;

    @FindBy(id = "stxtstreet1")
    private ExtendedWebElement addressLine1;

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
}
