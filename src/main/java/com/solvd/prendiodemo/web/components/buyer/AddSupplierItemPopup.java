package com.solvd.prendiodemo.web.components.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.utils.Util;
import com.solvd.prendiodemo.web.components.BasePopup;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddSupplierItemPopup extends BasePopup {

    private final By labelsCheckLocator = By.xpath("./preceding-sibling::input");

    private final By materialNameLocator = By.id("txtmaterialname");

    private final By materialValueLocator = By.xpath("../following::input[@id='txtmaterialvalue']");

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

    @FindBy(xpath = "//div[@id='safetydetail']//label")
    private List<ExtendedWebElement> safetyLabels;

    @FindBy(id = "supcasno")
    private ExtendedWebElement casNumberField;

    @FindBy(id = "selectum")
    private ExtendedWebElement unitSelect;

    @FindBy(id = "txtQty")
    private ExtendedWebElement qtyEachField;

    @FindBy(className = "dbaddnew_icon")
    private ExtendedWebElement addSpecButton;

    @FindBy(id = "txtmaterialname")
    private ExtendedWebElement firstMaterialNameField;

    @FindBy(id = "txtmaterialvalue")
    private ExtendedWebElement fistMaterialValue;

    @FindBy(id = "savecatalogitem")
    private ExtendedWebElement saveButton;

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
        safetyLabels.stream()
                .filter(label -> RandomUtils.nextBoolean())
                .forEach(ExtendedWebElement::click);
        casNumberField.type(RandomStringUtils.randomAlphabetic(15));
        Util.selectByIndex(unitSelect, 1);
        qtyEachField.type(String.valueOf(RandomUtils.nextInt(1, 10_000 + 1)));
        firstMaterialNameField.type(RandomStringUtils.randomAlphabetic(30));
        fistMaterialValue.type(RandomStringUtils.randomAlphabetic(30));
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
        info.put("status", Util.getSelectedOptionText(statusSelect));
        String safetyCheckboxesChecked = safetyLabels.stream()
                .filter(label -> label.findExtendedWebElement(labelsCheckLocator).isChecked())
                .map(ExtendedWebElement::getText).collect(Collectors.joining(", "));
        info.put("safetyCheckboxesChecked", safetyCheckboxesChecked);
        info.put("cas", getValue(casNumberField));
        info.put("unit", Util.getSelectedOptionText(unitSelect));
        info.put("qtyEach", getValue(qtyEachField));
        findExtendedWebElements(materialNameLocator).stream()
                .filter(material -> !getValue(material).isBlank())
                .forEach(material -> info.put(
                        "material_name(" + getValue(material) + ")",
                        material.findExtendedWebElement(materialValueLocator).getAttribute("value"
                        )));
        return info;
    }

    public void clickAddSpec() {
        addSpecButton.click();
    }

    public int getSpecNumber() {
        return findExtendedWebElements(materialNameLocator).size();
    }

    public void clickSave() {
        saveButton.click();
    }
}
