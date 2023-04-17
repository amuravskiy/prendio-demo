package com.solvd.prendiodemo.web.components.cart;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.domain.ItemContents;
import com.solvd.prendiodemo.utils.ElementsUtil;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

/**
 * Represents an item in a cart.
 */
public class CartItem extends AbstractUIObject implements ElementsUtil {

    @FindBy(id = "txtPartNo")
    private ExtendedWebElement partNumber;

    @FindBy(id = "txtPartDesc")
    private ExtendedWebElement partDesc;

    @FindBy(id = "ttlPrice")
    private ExtendedWebElement totalPrice;

    @FindBy(id = "ddCurrency")
    private ExtendedWebElement currencySelect;

    @FindBy(id = "ddDeptCart")
    private ExtendedWebElement department;

    @FindBy(id = "ddGLAccount")
    private ExtendedWebElement glAccount;

    @FindBy(id = "ddProjectCart")
    private ExtendedWebElement project;

    public CartItem(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ItemContents getItemContents() {
        return new ItemContents.ItemContentsBuilder()
                .setItemNumber(partNumber.getText())
                .setTitle(partDesc.getText())
                .setCurrencyType(getSelectedOptionText(currencySelect).replaceAll("[^a-zA-Z]", ""))
                .setPrice(totalPrice.getText().replace("NA", "0.00"))
                .setDepartment(getSelectedOptionText(department))
                .setGlAccount(getSelectedOptionText(glAccount))
                .setProject(getSelectedOptionText(project))
                .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNumber, partDesc, totalPrice, currencySelect);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return Objects.equals(partNumber, cartItem.partNumber) && Objects.equals(partDesc, cartItem.partDesc) && Objects.equals(totalPrice, cartItem.totalPrice) && Objects.equals(currencySelect, cartItem.currencySelect);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "partNumber=" + partNumber +
                ", partDesc=" + partDesc +
                ", totalPrice=" + totalPrice +
                ", currencySelect=" + currencySelect +
                '}';
    }
}
