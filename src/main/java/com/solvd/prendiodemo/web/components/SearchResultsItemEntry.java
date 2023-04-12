package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.domain.ItemContents;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SearchResultsItemEntry extends AbstractUIObject {

    @FindBy(xpath = "..//div[@class='searchtitle clamp2']")
    private ExtendedWebElement itemTitle;

    @FindBy(xpath = "..//span[@title='Add to cart']")
    private ExtendedWebElement addToCartButton;

    @FindBy(xpath = "..//a[@tag='addnewCart']")
    private ExtendedWebElement addNewCartButton;

    @FindBy(xpath = "..//a[@tag='addexitingcart']")
    private ExtendedWebElement addInExistingCartButton;

    @FindBy(xpath = "..//span[contains(text(), 'Supplier Part#')]")
    private ExtendedWebElement itemNumber;

    @FindBy(xpath = "..//div[@class='price_div']")
    private ExtendedWebElement priceContainer;//cprice attribute

    @FindBy(xpath = "..//span[@class='curtype']")
    private ExtendedWebElement currencyTypeText;

    @FindBy(xpath = "..//div[contains(@class,'divsupplierblock')]//div[b and @style]")
    private ExtendedWebElement supplierText;

    public SearchResultsItemEntry(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getTitleText() {
        return itemTitle.getText();
    }

    public void clickAddToCart() {
        addToCartButton.click();
    }

    public void clickAddToANewCart() {
        addNewCartButton.click();
    }

    public boolean isCreateNewCartButtonDisplayed() {
        return addNewCartButton.isVisible();
    }

    public boolean isAddInExistingCartButtonDisplayed() {
        return addInExistingCartButton.isVisible();
    }

    public ItemContents getItemContents() {
        return new ItemContents.ItemContentsBuilder()
                .setItemNumber(itemNumber.getText().replace("Supplier Part#", "").trim())
                .setSupplier(supplierText.getText())
                .setTitle(itemTitle.getText())
                .setCurrencyType(currencyTypeText.getText().replaceAll("[^a-zA-Z]", ""))
                .setPrice(priceContainer.getAttribute("cprice").replace("NA", "0.00"))
                .build();
    }
}
