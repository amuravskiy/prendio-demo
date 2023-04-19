package com.solvd.prendiodemo.web.pages.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.accountspayable.DepartmentSetupPopup;
import com.solvd.prendiodemo.web.components.common.SearchFilter;
import com.solvd.prendiodemo.web.components.common.TableEntry;
import com.solvd.prendiodemo.web.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DepartmentPage extends BasePage {

    @FindBy(xpath = "//li[a[text()='Department'] and @class='selected']")
    private ExtendedWebElement departmentActive;

    @FindBy(xpath = "//a[@id='Add_Department']")
    private ExtendedWebElement addDepartmentButton;

    @FindBy(xpath = "//div[contains(@class,'departmentpopup')]")
    private DepartmentSetupPopup departmentSetupPopup;

    @FindBy(xpath = "//tbody[@class='ui-sortable']/tr")
    private List<TableEntry> departments;

    @FindBy(xpath = "//div[@id='divdepartmentdepartment']")
    private SearchFilter searchFilter;

    public DepartmentPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(departmentActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public DepartmentSetupPopup clickAddDepartment() {
        addDepartmentButton.click();
        return departmentSetupPopup;
    }

    public DepartmentSetupPopup editDepartmentByName(String name) {
        departments.stream()
                .filter(dep -> dep.getContainerName().getText().equals(name))
                .findFirst()
                .orElseThrow()
                .clickEditIcon();
        return departmentSetupPopup;
    }

    public DepartmentPage searchDepartmentByDesc(String desc) {
        searchFilter.search(desc);
        ensureLoaded();
        waitForJSToLoad();
        return new DepartmentPage(getDriver());
    }
}
