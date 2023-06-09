package com.solvd.prendiodemo.web.components.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.domain.SupplierItemInfo;
import com.solvd.prendiodemo.web.components.BasePopup;
import com.solvd.prendiodemo.web.components.accountspayable.SafetyDetail;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.stream.Stream;

public class AddSupplierItemPopup extends BasePopup {

    @FindBy(id = "txtsuppart")
    private ExtendedWebElement supplierPartField;

    @FindBy(id = "txtdescription")
    private ExtendedWebElement descriptionField;

    @FindBy(xpath = "//a[text()='Copy Down']")
    private ExtendedWebElement copyDownButton;

    @FindBy(id = "txtGdescription")
    private ExtendedWebElement genericDescription;

    @FindBy(id = "txtdeprecatedpart")
    private ExtendedWebElement deprecatedPartField;

    @FindBy(id = "txtMfr")
    private ExtendedWebElement manufacturerField;

    @FindBy(id = "txtMfrPart")
    private ExtendedWebElement manufacturerNumberField;

    @FindBy(id = "supnotes")
    private ExtendedWebElement notesField;

    @FindBy(id = "selectcatalogitem")
    private ExtendedWebElement statusSelect;

    @FindBy(id = "supcasno")
    private ExtendedWebElement casNumberField;

    @FindBy(id = "selectum")
    private ExtendedWebElement unitSelect;

    @FindBy(id = "txtQty")
    private ExtendedWebElement qtyEachField;

    @FindBy(id = "savecatalogitem")
    private ExtendedWebElement saveButton;

    @FindBy(xpath = "//div[@id='safetydetail']")
    private SafetyDetail safetyDetail;

    @FindBy(xpath = "//div[@id='divSpecifications']")
    private SpecificationsSection specificationsSection;

    public AddSupplierItemPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public SupplierItemInfo fillInfoRandomly() {
        Stream.of(supplierPartField, descriptionField, deprecatedPartField, manufacturerField, manufacturerNumberField, notesField, casNumberField)
                .forEach(field -> field.type(RandomStringUtils.randomAlphabetic(15)));
        copyDownButton.click();
        safetyDetail.fillRandomValues();
        selectByIndex(unitSelect, 1);
        qtyEachField.type(String.valueOf(RandomUtils.nextInt(1, 10_000 + 1)));
        specificationsSection.addRandomSpecification();
        return getInfo();
    }

    public SupplierItemInfo getInfo() {
        return SupplierItemInfo.builder()
                .supplierPart(getValue(supplierPartField))
                .desc(getValue(descriptionField))
                .genericDesc(getValue(genericDescription))
                .deprecatedPart(getValue(deprecatedPartField))
                .manufacturer(getValue(manufacturerField))
                .manufactNumber(getValue(manufacturerNumberField))
                .notes(getValue(notesField)).
                status(getSelectedOptionText(statusSelect)).
                safetyCheckboxesChecked(safetyDetail.getCheckboxesChecked())
                .cas(getValue(casNumberField))
                .unit(getSelectedOptionText(unitSelect))
                .qtyEach(getValue(qtyEachField))
                .specs(specificationsSection.getSpecifications())
                .build();
    }

    public void addSpecification() {
        specificationsSection = specificationsSection.addSpecification();
    }

    public int getSize() {
        return specificationsSection.getSize();
    }

    public void clickSaveButton() {
        saveButton.click();
    }
}
