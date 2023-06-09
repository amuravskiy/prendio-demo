package com.solvd.prendiodemo.web.components.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.domain.AddressInfo;
import com.solvd.prendiodemo.domain.PopupSections;
import com.solvd.prendiodemo.web.components.BasePopup;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AddressSetupPopup extends BasePopup {

    @FindBy(id = "seladdrtype")
    private ExtendedWebElement addressTypeSelect;

    @FindBy(id = "txtcity")
    private ExtendedWebElement cityField;

    @FindBy(id = "txtstate")
    private ExtendedWebElement stateField;

    @FindBy(xpath = "//input[contains(@id,'stxtstreet')]")
    private List<ExtendedWebElement> addressLines;

    @FindBy(id = "txtcode")
    private ExtendedWebElement zipCodeField;

    @FindBy(id = "dropdownCountry")
    private ExtendedWebElement countrySelect;

    @FindBy(id = "txtmainphone")
    private ExtendedWebElement phoneField;

    @FindBy(id = "txtaddresscode")
    private ExtendedWebElement addressCode;

    @FindBy(id = "isdefaultaddress")
    private ExtendedWebElement defaultAddressCheckbox;

    @FindBy(xpath = "//input[@id='isdefaultaddress']//parent::div")
    private ExtendedWebElement defaultAddressContainer;

    @FindBy(id = "SaveAddress")
    private ExtendedWebElement saveButton;

    @FindBy(xpath = "//input[@id='all_defaultShippingAddress']//parent::div")
    private ExtendedWebElement allDefaultChecker;

    @FindBy(xpath = "..//table//input[@type='checkbox']")
    private List<ExtendedWebElement> tableCheckboxes;

    @FindBy(id = "tbladdressuser")
    private ExtendedWebElement userTable;

    public AddressSetupPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickSaveButton() {
        saveButton.click();
    }

    public AddressInfo fillInfoRandomly() {
        addressTypeSelect.selectByPartialText("Shipping");
        waitUntil(ExpectedConditions.attributeToBeNotEmpty(addressCode.getElement(), "value"), EXPLICIT_TIMEOUT);
        fillAddressLinesRandomly(addressLines);
        cityField.type(RandomStringUtils.randomAlphabetic(15));
        stateField.type(RandomStringUtils.randomAlphabetic(15));
        zipCodeField.type(RandomStringUtils.randomAlphabetic(10));
        selectByIndex(countrySelect, 1);
        phoneField.getElement().clear();
        phoneField.type(RandomStringUtils.randomNumeric(10));
        addressCode.type(String.valueOf(RandomUtils.nextInt(1, 10_000)));
        defaultAddressContainer.click();
        return getInfo();
    }

    public AddressInfo getInfo() {
        return AddressInfo.builder()
                .addressType(getSelectedOptionText(addressTypeSelect))
                .addressLines(getAddressLines(addressLines))
                .city(getValue(cityField))
                .state(getValue(stateField))
                .zip(getValue(zipCodeField))
                .country(getSelectedOptionText(countrySelect))
                .phone(getValue(phoneField))
                .addressCode(getValue(addressCode))
                .isDefault(defaultAddressCheckbox.isChecked())
                .build();
    }

    public void clickUsersSection() {
        getPopupLeftMenu().clickPopupSection(PopupSections.USERS);
    }

    public boolean isUserSectionVisible() {
        return userTable.isVisible();
    }

    public void checkAllDefault() {
        allDefaultChecker.click();
    }

    public boolean isAllDefaultActive() {
        return tableCheckboxes.stream()
                .filter(checkbox -> checkbox.getElement().isDisplayed())
                .allMatch(ExtendedWebElement::isChecked);
    }
}
