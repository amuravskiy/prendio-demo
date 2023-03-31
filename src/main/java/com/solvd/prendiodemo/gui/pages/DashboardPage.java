package com.solvd.prendiodemo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.CartsTable;
import com.solvd.prendiodemo.gui.components.OrdersTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(xpath = "//li[@id and @class='active' and a[text()='Dashboard']]")
    private ExtendedWebElement activeDashboardButton;

    @FindBy(id = "tbltoporder")
    private OrdersTable ordersTable;

    @FindBy(id = "tblncart")
    private CartsTable cartsTable;

    public DashboardPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(activeDashboardButton);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public AllCartsPage clickViewAllCarts() {
        return cartsTable.clickViewAllButton();
    }

    public String getOrderPreviewsCartName(int index) {
        return ordersTable.getCartPreviewName(index);
    }
}
