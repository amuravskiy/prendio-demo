package com.solvd.prendiodemo.gui.pages.accountspayablepages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.DepartmentEntry;
import com.solvd.prendiodemo.gui.components.DepartmentSetupPopup;
import com.solvd.prendiodemo.gui.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DepartmentPage extends BasePage {

    @FindBy(xpath = "//li[a[text()='Department'] and @class='selected']")
    private ExtendedWebElement departmentActive;

    @FindBy(xpath = "//a[@id='Add_Department']")
    private ExtendedWebElement addDepButton;

    @FindBy(xpath = "//div[contains(@class,'departmentpopup')]")
    private DepartmentSetupPopup depSetupPopup;

    @FindBy(xpath = "//tbody[@class='ui-sortable']/tr")
    private List<DepartmentEntry> departments;

    public DepartmentPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(departmentActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public boolean isAddButtonVisible() {
        return addDepButton.isVisible();
    }

    public DepartmentSetupPopup clickAddDep() {
        addDepButton.click();
        return depSetupPopup;
    }

    public DepartmentSetupPopup editDepByName(String name) {
        departments.stream()
                .filter(dep -> dep.getNameContainer().getText().equals(name))
                .findFirst()
                .orElseThrow()
                .getEditIcon()
                .click();
        return new DepartmentPage(driver).depSetupPopup;
    }
}
