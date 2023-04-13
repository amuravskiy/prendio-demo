package com.solvd.prendiodemo.service;

import com.solvd.prendiodemo.web.pages.DashboardPage;
import com.solvd.prendiodemo.web.pages.LoginPage;
import com.solvd.prendiodemo.web.pages.OneLoginPortalPage;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;

public class LoginService {

    public static final String USERNAME = R.TESTDATA.get("username");
    public static final String PASSWORD = R.TESTDATA.get("password");

    private final WebDriver driver;

    public LoginService(WebDriver driver) {
        this.driver = driver;
    }

    public DashboardPage login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.assertPageOpened();
        OneLoginPortalPage oneLoginPortalPage = loginPage.login(USERNAME, PASSWORD);
        DashboardPage dashboardPage = oneLoginPortalPage.goToPrendio();
        dashboardPage.switchToTab(1);
        return dashboardPage;
    }
}
