package com.solvd.prendiodemo.gui.pages.buyerpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.gui.components.BasePopup;
import com.solvd.prendiodemo.utils.Util;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressSetupPopup extends BasePopup {

    private final String partialLineId = "stxtstreet";

    @FindBy(id = "seladdrtype")
    private ExtendedWebElement addrTypeSelect;

    @FindBy(id = "txtcity")
    private ExtendedWebElement cityField;

    @FindBy(id = "txtstate")
    private ExtendedWebElement stateField;

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

    public AddressSetupPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickSave() {
        saveButton.click();
    }

    public Map<String, String> fillInfo() {
        addrTypeSelect.selectByPartialText("Shipping");
        for (int i = 1; i < 5; i++) {
            findExtendedWebElement(By.id(partialLineId + i)).type(RandomStringUtils.randomAlphabetic(15));
        }
        cityField.type(RandomStringUtils.randomAlphabetic(15));
        stateField.type(RandomStringUtils.randomAlphabetic(15));
        zipCodeField.type(RandomStringUtils.randomAlphabetic(10));
        Util.selectByIndex(countrySelect, 1);
        phoneField.type(RandomStringUtils.randomNumeric(10));
        addressCode.type(String.valueOf(RandomUtils.nextInt(1, 10_000)));
        defaultAddressContainer.click();
        return getInfo();
    }

    public Map<String, String> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("addressType", Util.getSelectedOptionText(addrTypeSelect));
        for (int i = 1; i < 5; i++) {
            info.put("line" + i, findExtendedWebElement(By.id(partialLineId + i)).getAttribute("value"));
        }
        info.put("city", cityField.getAttribute("value"));
        info.put("state", stateField.getAttribute("value"));
        info.put("zip", zipCodeField.getAttribute("value"));
        info.put("country", Util.getSelectedOptionText(countrySelect));
        info.put("phone", phoneField.getAttribute("value"));
        info.put("addressCode", addressCode.getAttribute("value"));
        info.put("default", String.valueOf(defaultAddressCheckbox.isChecked()));
        return info;
    }

    public void clickUsers() {
        getPopupLeftMenu().clickTabByName("Users");
    }

    public void checkAllDefault() {
        allDefaultChecker.click();
    }

    public void assertAllDefaultActive() {
        Assert.assertTrue(tableCheckboxes.stream()
                .filter(checkbox -> checkbox.getElement().isDisplayed())
                .allMatch(ExtendedWebElement::isChecked));
    }
}
