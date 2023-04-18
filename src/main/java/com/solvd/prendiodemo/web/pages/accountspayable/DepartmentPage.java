package com.solvd.prendiodemo.web.pages.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.components.SearchFilter;
import com.solvd.prendiodemo.web.components.TableEntry;
import com.solvd.prendiodemo.web.components.accountspayable.DepSetupPopup;
import com.solvd.prendiodemo.web.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DepartmentPage extends BasePage {

    @FindBy(xpath = "//li[a[text()='Department'] and @class='selected']")
    private ExtendedWebElement departmentActive;

    @FindBy(xpath = "//a[@id='Add_Department']")
    private ExtendedWebElement addDepButton;

    @FindBy(xpath = "//div[contains(@class,'departmentpopup')]")
    private DepSetupPopup depSetupPopup;

    @FindBy(xpath = "//tbody[@class='ui-sortable']/tr")
    private List<TableEntry> departments;

    @FindBy(xpath = "//div[@id='divdepartmentdepartment']")
    private SearchFilter searchFilter;

    public DepartmentPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(departmentActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public boolean isAddButtonVisible() {
        return addDepButton.isVisible();
    }

    public DepSetupPopup clickAddDep() {
        addDepButton.click();
        return depSetupPopup;
    }

    public DepSetupPopup editDepByName(String name) {
        departments.stream()
                .filter(dep -> dep.getNameContainer().getText().equals(name))
                .findFirst()
                .orElseThrow()
                .clickEditIcon();
        return depSetupPopup;
    }

    public DepartmentPage searchDepartmentByDesc(String desc) {
        searchFilter.search(desc);
        return new DepartmentPage(getDriver());
    }
}
