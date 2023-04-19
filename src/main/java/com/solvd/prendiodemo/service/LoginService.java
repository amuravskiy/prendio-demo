package com.solvd.prendiodemo.service;

import com.solvd.prendiodemo.web.pages.DashboardPage;
import com.solvd.prendiodemo.web.pages.LoginPage;
import com.solvd.prendiodemo.web.pages.OneLoginPortalPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginService {

    private final WebDriver driver;

    public LoginService(WebDriver driver) {
        this.driver = driver;
    }

    public DashboardPage login(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        OneLoginPortalPage oneLoginPortalPage = loginPage.login(username, password);
        Assert.assertTrue(oneLoginPortalPage.isPageOpened(), "One Login Portal page is not opened");
        DashboardPage dashboardPage = oneLoginPortalPage.goToPrendio();
        dashboardPage.switchToTab(1);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        return dashboardPage;
    }
}
