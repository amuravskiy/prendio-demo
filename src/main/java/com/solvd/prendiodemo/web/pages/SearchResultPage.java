package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.domain.ItemContents;
import com.solvd.prendiodemo.web.components.SearchResultsItemEntry;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class SearchResultPage extends BasePage {

    private static int retrievingTimeout = R.CONFIG.getInt("retrieving_timeout");

    @FindBy(id = "divsearchcontainer")
    private ExtendedWebElement searchResultsActive;

    @FindBy(className = "researchdiv")
    private ExtendedWebElement retrievingBlock;

    @FindBy(xpath = "//ul[@id='ultopserchresult']//li[@tag='mainCartLi']")
    private List<SearchResultsItemEntry> searchResultsItemEntries;

    @FindBy(className = "searchloading1")
    private ExtendedWebElement searchLoadingSign;

    public SearchResultPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(searchResultsActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public void assertRetrieved() {
        Assert.assertTrue(searchLoadingSign.waitUntilElementDisappear(retrievingTimeout), "Retrieving has not ended in " + retrievingTimeout + " sec");
    }

    public boolean isItemsDisplayed() {
        return !searchResultsItemEntries.isEmpty();
    }

    public boolean isAllItemTitlesContainQuery(String query) {
        return searchResultsItemEntries.stream()
                .allMatch(searchResultsItemEntry -> searchResultsItemEntry.getTitleText().toLowerCase().contains(query.toLowerCase()));
    }

    public void clickAddToCart(int index) {
        searchResultsItemEntries.get(index).clickAddToCart();
        ensureLoaded();
    }

    public boolean isCreateNewCartButtonDisplayed(int index) {
        return searchResultsItemEntries.get(index).isCreateNewCartButtonDisplayed();
    }

    public boolean isAddInExistingCartButtonDisplayed(int index) {
        return searchResultsItemEntries.get(index).isAddInExistingCartButtonDisplayed();
    }

    public CartPage clickCreateNewCart(int index) {
        searchResultsItemEntries.get(index).clickAddToANewCart();
        ensureLoaded();
         return new CartPage(driver);
    }

    public ItemContents getItemContents(int index) {
        return searchResultsItemEntries.get(index).getItemContents();
    }
}
