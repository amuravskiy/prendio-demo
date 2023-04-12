package com.solvd.prendiodemo.web.pages.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.buyer.AddressSetupPopup;
import com.solvd.prendiodemo.web.components.TableEntry;
import com.solvd.prendiodemo.web.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AddressesPage extends BasePage {

    @FindBy(xpath = "//li[a[text()='Addresses'] and @class='selected']")
    private ExtendedWebElement addressesTabActive;

    @FindBy(id = "Add_Address")
    private ExtendedWebElement addAddressButton;

    @FindBy(xpath = "//div[h2[text()='Address Setup']]")
    private AddressSetupPopup addressSetupPopup;

    @FindBy(xpath = "//div[@id='divaddresses']//input[@placeholder='<filter list>']")
    private ExtendedWebElement searchField;

    @FindBy(xpath = "//table[@id='tbladdress']//tbody//tr")
    private TableEntry firstAddress;

    public AddressesPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(addressesTabActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public AddressSetupPopup clickAddAddress() {
        addAddressButton.click();
        return addressSetupPopup;
    }

    public AddressesPage search(String query) {
        searchField.type(query);
        searchField.sendKeys(Keys.ENTER);
        return new AddressesPage(getDriver());
    }

    public void assertAddressFound(String line1) {
        Assert.assertEquals(firstAddress.getNameContainer().getText().split(",")[0], line1, "Address not found");
    }

    public AddressSetupPopup editFirstAddress() {
        firstAddress.getEditIcon().click();
        ensureLoaded();
        return addressSetupPopup;
    }
}
