package com.solvd.prendiodemo.web.pages.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.SearchFilter;
import com.solvd.prendiodemo.web.components.TableEntry;
import com.solvd.prendiodemo.web.components.buyer.AddressSetupPopup;
import com.solvd.prendiodemo.web.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class AddressesPage extends BasePage {

    @FindBy(xpath = "//li[a[text()='Addresses'] and @class='selected']")
    private ExtendedWebElement addressesTabActive;

    @FindBy(id = "Add_Address")
    private ExtendedWebElement addAddressButton;

    @FindBy(xpath = "//div[@id='popupform' and contains(@class, 'addresstpopup')]")
    private AddressSetupPopup addressSetupPopup;

    @FindBy(xpath = "//div[@id='divaddresses']")
    private SearchFilter searchFilter;

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

    public AddressesPage searchAddressByAddressLine(String query) {
        searchFilter.search(query);
        return new AddressesPage(getDriver());
    }

    public boolean isAddressFound(String line1) {
        return firstAddress.getNameContainer().getText().split(",")[0].equals(line1);
    }

    public AddressSetupPopup editFirstAddress() {
        firstAddress.clickEditIcon();
        ensureLoaded();
        return addressSetupPopup;
    }
}
