package com.solvd.prendiodemo.gui.pages.accountspayablepages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.DepSetupPopup;
import com.solvd.prendiodemo.gui.components.TableEntry;
import com.solvd.prendiodemo.gui.pages.BasePage;
import org.openqa.selenium.Keys;
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

    @FindBy(xpath = "//div[@id='divdepartmentdepartment']//input[@placeholder='<filter list>']")
    private ExtendedWebElement searchField;

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
                .getEditIcon()
                .click();
        return new DepartmentPage(driver).depSetupPopup;
    }

    public DepartmentPage searchDepartmentByDesc(String desc) {
        searchField.type(desc);
        searchField.sendKeys(Keys.ENTER);
        return new DepartmentPage(driver);
    }
}
