package com.solvd.prendiodemo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.CartEntry;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AllCartsPage extends BasePage {

    @FindBy(xpath = "//table[@id='tblCartList']//td")
    private ExtendedWebElement cartsTabActive;

    @FindBy(xpath = "//div[@id='divcarts']//input[@placeholder='<filter list>']")
    private ExtendedWebElement searchField;

    @FindBy(xpath = "//tr[@cartid]")
    private List<CartEntry> cartEntries;

    public AllCartsPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(cartsTabActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public AllCartsPage search(String name) {
        searchField.type(name);
        searchField.sendKeys(Keys.ENTER);
        if (cartEntries.size() > 0) {
            cartEntries.get(0).getRootExtendedElement().waitUntilElementDisappear(EXPLICIT_TIMEOUT);
        }
        return new AllCartsPage(driver);
    }

    public CartPage clickFirstCart() {
        return cartEntries.get(0).clickId();
    }

    public CartPage clickById(String id) {
        return cartEntries.stream()
                .filter(cartEntry -> cartEntry.getId().equals(id))
                .findFirst()
                .orElseThrow()
                .clickId();
    }

    public boolean isCartWithNameFound(String name) {
        return cartEntries.stream()
                .anyMatch(cart -> cart.getName().equals(name));
    }

    public String getTopCartName() {
        return cartEntries.get(0).getName();
    }

    public String getTopCartId() {
        return cartEntries.get(0).getId();
    }

    public String getCartNameById(String id) {
        return cartEntries.stream()
                .filter(cartEntry -> cartEntry.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Cart with id " + id + " not found"))
                .getName();
    }
}
