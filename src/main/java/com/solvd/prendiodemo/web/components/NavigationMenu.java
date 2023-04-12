package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.web.pages.AccountPayablePage;
import com.solvd.prendiodemo.web.pages.BuyerPage;
import com.solvd.prendiodemo.web.pages.DashboardPage;
import com.solvd.prendiodemo.web.pages.ReceiverPage;
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

    public NavigationMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public DashboardPage clickDashboardButton() {
        dashboardButton.click();
        return new DashboardPage(getDriver());
    }

    public BuyerPage clickBuyerButton() {
        buyerButton.click();
        return new BuyerPage(getDriver());
    }

    public ReceiverPage clickReceiverButton() {
        receiverButton.click();
        return new ReceiverPage(getDriver());
    }

    public AccountPayablePage clickAccountsPayableButton() {
        accountsPayableButton.click();
        return new AccountPayablePage(getDriver());
    }
}
