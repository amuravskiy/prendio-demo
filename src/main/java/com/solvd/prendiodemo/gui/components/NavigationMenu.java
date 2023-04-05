package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.gui.pages.*;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class NavigationMenu extends AbstractUIObject {

    @FindBy(xpath = "//a[text()='Dashboard']")
    private ExtendedWebElement dashboardButton;

    @FindBy(xpath = "//a[text()='Buyer']")
    private ExtendedWebElement buyerButton;

    @FindBy(xpath = "//a[text()='Receiver']")
    private ExtendedWebElement receiverButton;

    @FindBy(xpath = "//a[text()='Accounts Payable']")
    private ExtendedWebElement accountsPayableButton;

    @FindBy(xpath = "//a[text()='AP Management']")
    private ExtendedWebElement APManagementButton;

    public NavigationMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public DashboardPage clickDashboardButton() {
        dashboardButton.click();
        return new DashboardPage(driver);
    }

    public BuyerPage clickBuyerButton() {
        buyerButton.click();
        return new BuyerPage(driver);
    }

    public ReceiverPage clickReceiverButton() {
        receiverButton.click();
        return new ReceiverPage(driver);
    }

    public AccountPayablePage clickAccountsPayableButton() {
        accountsPayableButton.click();
        return new AccountPayablePage(driver);
    }
}
