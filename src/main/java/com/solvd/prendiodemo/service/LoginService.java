package com.solvd.prendiodemo.service;

import com.solvd.prendiodemo.web.pages.DashboardPage;
import com.solvd.prendiodemo.web.pages.LoginPage;
import com.solvd.prendiodemo.web.pages.OneLoginPortalPage;
import org.openqa.selenium.WebDriver;

public class LoginService {

    private final WebDriver driver;

    public LoginService(WebDriver driver) {
        this.driver = driver;
    }

    public DashboardPage login(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.assertPageOpened();
        OneLoginPortalPage oneLoginPortalPage = loginPage.login(username, password);
        oneLoginPortalPage.assertPageOpened();
        DashboardPage dashboardPage = oneLoginPortalPage.goToPrendio();
        dashboardPage.switchToTab(1);
        dashboardPage.assertPageOpened();
        return dashboardPage;
    }
}
