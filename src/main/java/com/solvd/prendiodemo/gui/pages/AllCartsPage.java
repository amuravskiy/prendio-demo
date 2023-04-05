package com.solvd.prendiodemo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.CartEntry;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void assertCartWithNameFound(String name) {
        Assert.assertTrue(cartEntries.stream()
                .anyMatch(cart -> cart.getName().equals(name)), "Cart with name " + name + " is not found");
    }

    private Optional<CartEntry> findCartEntryById(String id) {
        return cartEntries.stream()
                .filter(cart -> cart.getId().equals(id))
                .findFirst();
    }

    public void assertCartPresent(String id) {
        Assert.assertTrue(findCartEntryById(id).isPresent(), "Cart with id " + id + " not found");
    }

    public String getCartNameById(String id) {
        return findCartEntryById(id)
                .orElseThrow(() -> new NotFoundException("Cart with id " + id + " not found"))
                .getName();
    }
}
