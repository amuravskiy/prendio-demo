package com.solvd.prendiodemo.utils;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.gui.pages.DashboardPage;
import com.solvd.prendiodemo.gui.pages.LoginPage;
import com.solvd.prendiodemo.gui.pages.OneLoginPortalPage;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static void switchToTabOne(WebDriver driver) {
        List<String> tabHandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabHandles.get(1));
    }

    public static DashboardPage loginAs(WebDriver driver) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.assertPageOpened();
        OneLoginPortalPage oneLoginPortalPage = loginPage.login(R.CONFIG.get("username"), R.CONFIG.get("password"));
        DashboardPage dashboardPage = oneLoginPortalPage.goToPrendio();
        dashboardPage.switchToTab(1);
        return dashboardPage;
    }

    public static void clickElementByName(List<ExtendedWebElement> elements, String name) {
        elements.stream()
                .filter(tab -> tab.getText().equals(name))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Element with " + name + " not found"))
                .click();
    }

    public static void selectByIndex(ExtendedWebElement element, int index) {
        Select select = new Select(element.getElement());
        select.selectByIndex(index);
    }
}
