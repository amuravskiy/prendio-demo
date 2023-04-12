package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.OrdersTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(xpath = "tbltoporder")
    private OrdersTable ordersTable;

    @FindBy(xpath = "//table[@id='tbltoporder']/tbody/tr")
    private OrdersTable ordersLoaded;

    @FindBy(xpath = "//div[@id='tblncart']//a[contains(text(),'View All')]")
    private ExtendedWebElement viewAllCarts;

    public DashboardPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(ordersLoaded.getRootExtendedElement());
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public AllCartsPage clickViewAllCarts() {
        viewAllCarts.click();
        return new AllCartsPage(getDriver());
    }

    public String getOrderPreviewsCartName(int index) {
        return ordersTable.getCartPreviewName(index);
    }
}
