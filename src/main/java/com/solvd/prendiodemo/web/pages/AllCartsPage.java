package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.cart.CartTableEntry;
import com.solvd.prendiodemo.web.components.common.SearchFilter;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

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

    public AllCartsPage searchCartByName(String name) {
        searchFilter.search(name);
        if (!cartEntries.isEmpty()) {
            cartEntries.get(0).getRootExtendedElement().waitUntilElementDisappear(EXPLICIT_TIMEOUT);
        }
        return new AllCartsPage(getDriver());
    }

    public CartPage clickFirstCart() {
        return cartEntries.get(0).clickEntryId();
    }

    public CartPage clickById(String id) {
        return cartEntries.stream()
                .filter(cartTableEntry -> cartTableEntry.getCartId().equals(id))
                .findFirst()
                .orElseThrow()
                .clickEntryId();
    }

    public boolean isCartFound(String cartName) {
        return cartEntries.stream()
                .anyMatch(cart -> cart.getCartName().equals(cartName));
    }

    private Optional<CartTableEntry> findCartEntryById(String id) {
        return cartEntries.stream()
                .filter(cart -> cart.getCartId().equals(id))
                .findFirst();
    }

    public boolean isCartPresent(String id) {
        return findCartEntryById(id).isPresent();
    }

    public String getCartNameById(String id) {
        return findCartEntryById(id)
                .orElseThrow(() -> new NotFoundException("Cart with id " + id + " not found"))
                .getCartName();
    }
}
