package com.solvd.prendiodemo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.Item;
import com.solvd.prendiodemo.values.ItemContents;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class SearchResultPage extends BasePage {

    @FindBy(id = "divsearchcontainer")
    private ExtendedWebElement searchResultsActive;

    @FindBy(className = "researchdiv")
    private ExtendedWebElement retrievingBlock;

    @FindBy(xpath = "//ul[@id='ultopserchresult']//li[@tag='mainCartLi']")
    private List<Item> items;

    @FindBy(className = "searchloading1")
    private ExtendedWebElement searchLoadingSign;

    public SearchResultPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(searchResultsActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public void assertRetrieved() {
        int timeout = R.CONFIG.getInt("retrieving_timeout");
        Assert.assertTrue(searchLoadingSign.waitUntilElementDisappear(timeout), "Retrieving has not ended in " + timeout + " sec");
    }

    public boolean isItemsDisplayed() {
        return !items.isEmpty();
    }

    public boolean isAllItemTitlesContainQuery(String query) {
        return items.stream()
                .allMatch(item -> item.getTitleText().toLowerCase().contains(query.toLowerCase()));
    }

    public void clickAddToCart(int index) {
        items.get(index).clickAddToCart();
        ensureLoaded();
    }

    public boolean isCreateNewCartButtonDisplayed(int index) {
        return items.get(index).isCreateNewCartButtonDisplayed();
    }

    public boolean isAddInExistingCartButtonDisplayed(int index) {
        return items.get(index).isAddInExistingCartButtonDisplayed();
    }

    public CartPage clickCreateNewCart(int index) {
        items.get(index).clickAddToANewCart();
        ensureLoaded();
        return new CartPage(driver);
    }

    public ItemContents getItemContents(int index) {
        return items.get(index).getItemContents();
    }
}
