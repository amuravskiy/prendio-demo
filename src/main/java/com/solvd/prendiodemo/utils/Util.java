package com.solvd.prendiodemo.utils;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.web.pages.DashboardPage;
import com.solvd.prendiodemo.web.pages.LoginPage;
import com.solvd.prendiodemo.web.pages.OneLoginPortalPage;
import com.zebrunner.carina.utils.R;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
        OneLoginPortalPage oneLoginPortalPage = loginPage.login(R.TESTDATA.get("username"), R.TESTDATA.get("password"));
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

    public static String getSelectedOptionText(ExtendedWebElement select) {
        return new Select(select.getElement()).getOptions().stream()
                .filter(WebElement::isSelected)
                .findFirst()
                .orElseThrow()
                .getText();
    }

    public static File loadFile(String url, File file) {
        try {
            FileUtils.copyURLToFile(new URL(url), file);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatDate(String date) {
        return StringUtils.stripStart(date, "0")
                .replace("/0", "/");
    }
}
