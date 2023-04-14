package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.CartTableEntry;
import com.solvd.prendiodemo.web.components.SearchFilter;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Optional;

public class AllCartsPage extends BasePage {

    @FindBy(xpath = "//table[@id='tblCartList']//td")
    private ExtendedWebElement cartsTabActive;

    @FindBy(xpath = "//div[@id='divcarts']")
    private SearchFilter searchFilter;

    @FindBy(xpath = "//tr[@cartid]")
    private List<CartTableEntry> cartEntries;

    public AllCartsPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(cartsTabActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public AllCartsPage search(String name) {
        searchFilter.search(name);
        if (!cartEntries.isEmpty()) {
            cartEntries.get(0).getRootExtendedElement().waitUntilElementDisappear(EXPLICIT_TIMEOUT);
        }
        return new AllCartsPage(getDriver());
    }

    public CartPage clickFirstCart() {
        return cartEntries.get(0).clickId();
    }

    public CartPage clickById(String id) {
        return cartEntries.stream()
                .filter(cartTableEntry -> cartTableEntry.getId().equals(id))
                .findFirst()
                .orElseThrow()
                .clickId();
    }

    public void assertCartWithNameFound(String name) {
        Assert.assertTrue(cartEntries.stream()
                        .anyMatch(cart -> cart.getName().equals(name)),
                "Cart with name " + name + " is not found");
    }

    private Optional<CartTableEntry> findCartEntryById(String id) {
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
