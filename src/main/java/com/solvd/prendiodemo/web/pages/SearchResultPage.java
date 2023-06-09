package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.domain.ItemContents;
import com.solvd.prendiodemo.web.components.SearchResultsItemEntry;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.solvd.prendiodemo.constants.Constants.RETRIEVING_TIMEOUT;

public class SearchResultPage extends BasePage {

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

    public boolean isRetrieved() {
        return searchLoadingSign.waitUntilElementDisappear(RETRIEVING_TIMEOUT);
    }

    public boolean isItemsDisplayed() {
        return !searchResultsItemEntries.isEmpty();
    }

    public boolean isAllItemTitlesContainQuery(String query) {
        return searchResultsItemEntries.stream()
                .allMatch(searchResultsItemEntry -> searchResultsItemEntry.getTitleText().toLowerCase().contains(query.toLowerCase()));
    }

    public void clickAddToCartButton(int index) {
        searchResultsItemEntries.get(index).clickAddToCartButton();
        ensureLoaded();
    }

    public boolean isCreateNewCartButtonDisplayed(int index) {
        return searchResultsItemEntries.get(index).isCreateNewCartButtonDisplayed();
    }

    public boolean isAddInExistingCartButtonDisplayed(int index) {
        return searchResultsItemEntries.get(index).isAddInExistingCartButtonDisplayed();
    }

    public CartPage clickCreateNewCart(int index) {
        searchResultsItemEntries.get(index).clickAddToANewCartButton();
        ensureLoaded();
        return new CartPage(getDriver());
    }

    public ItemContents getItemContents(int index) {
        return searchResultsItemEntries.get(index).getItemContents();
    }
}
