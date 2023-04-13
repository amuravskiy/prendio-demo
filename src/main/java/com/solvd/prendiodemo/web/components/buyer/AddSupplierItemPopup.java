package com.solvd.prendiodemo.web.components.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.web.components.BasePopup;
import com.solvd.prendiodemo.web.components.accountspayable.SafetyDetail;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class AddSupplierItemPopup extends BasePopup {

    @FindBy(xpath = "//div[@id='safetydetail']//label")
    private ExtendedWebElement loadedMarker;

    @FindBy(id = "txtsuppart")
    private ExtendedWebElement supplierPartField;

    @FindBy(id = "txtdescription")
    private ExtendedWebElement descField;

    @FindBy(xpath = "//a[text()='Copy Down']")
    private ExtendedWebElement copyDownButton;

    @FindBy(id = "txtGdescription")
    private ExtendedWebElement genericDesc;

    @FindBy(id = "txtdeprecatedpart")
    private ExtendedWebElement deprecatedPartField;

    @FindBy(id = "txtMfr")
    private ExtendedWebElement manufacturerField;

    @FindBy(id = "txtMfrPart")
    private ExtendedWebElement manufactNumberField;

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

    @FindBy(id = "txtmaterialname")
    private ExtendedWebElement firstMaterialNameField;

    @FindBy(id = "txtmaterialvalue")
    private ExtendedWebElement fistMaterialValue;

    @FindBy(id = "savecatalogitem")
    private ExtendedWebElement saveButton;

    @FindBy(xpath = "//div[@id='safetydetail']")
    private SafetyDetail safetyDetail;

    @FindBy(xpath = "//div[@id='divSpecifications']")
    private SpecificationsSection specificationsSection;

    public AddSupplierItemPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public Map<String, String> fillInfoRandomly() {
        supplierPartField.type(RandomStringUtils.randomAlphabetic(15));
        descField.type(RandomStringUtils.randomAlphabetic(30));
        copyDownButton.click();
        deprecatedPartField.type(RandomStringUtils.randomAlphabetic(15));
        manufacturerField.type(RandomStringUtils.randomAlphabetic(15));
        manufactNumberField.type(RandomStringUtils.randomAlphabetic(15));
        notesField.type(RandomStringUtils.randomAlphabetic(45));
        safetyDetail.fillRandomly();
        casNumberField.type(RandomStringUtils.randomAlphabetic(15));
        selectByIndex(unitSelect, 1);
        qtyEachField.type(String.valueOf(RandomUtils.nextInt(1, 10_000 + 1)));
        specificationsSection.addRandomSpec();
        return getInfo();
    }

    public Map<String, String> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("supplierPart", getValue(supplierPartField));
        info.put("desc", getValue(descField));
        info.put("genericDesc", getValue(genericDesc));
        info.put("deprecatedPart", getValue(deprecatedPartField));
        info.put("manufacturer", getValue(manufacturerField));
        info.put("manufactNumber", getValue(manufactNumberField));
        info.put("notes", getValue(notesField));
        info.put("status", getSelectedOptionText(statusSelect));
        String safetyCheckboxesChecked = safetyDetail.getCheckboxesChecked();
        info.put("safetyCheckboxesChecked", safetyCheckboxesChecked);
        info.put("cas", getValue(casNumberField));
        info.put("unit", getSelectedOptionText(unitSelect));
        info.put("qtyEach", getValue(qtyEachField));
        specificationsSection.getSpecs().forEach(pair -> info.put(pair.left, pair.right));
        return info;
    }

    public void clickAddSpec() {
        specificationsSection = specificationsSection.addSpec();
    }

    public int getSpecListSize() {
        return specificationsSection.getSize();
    }

    public void clickSave() {
        saveButton.click();
    }
}
