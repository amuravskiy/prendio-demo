package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.web.pages.SearchResultPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SearchBlock extends AbstractUIObject {

    @FindBy(className = "searchtypemenu")
    private SearchTypeMenu searchTypeMenu;

    @FindBy(className = "searchbox_large")
    private ExtendedWebElement searchField;

    @FindBy(id = "btncatalogsearch")
    private ExtendedWebElement searchButton;

    public SearchBlock(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public SearchResultPage search(String text) {
        searchField.type(text);
        searchButton.click();
         return new SearchResultPage(driver);
    }

    public void chooseType(OrderType orderType) {
        switch (orderType) {
            case ORDER:
                searchTypeMenu.chooseOrderType();
                break;
            case CATALOG:
                searchTypeMenu.chooseCatalogType();
                break;
        }
    }

    public enum OrderType {
        ORDER, CATALOG
    }
}
