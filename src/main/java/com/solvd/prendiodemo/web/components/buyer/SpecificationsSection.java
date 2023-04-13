package com.solvd.prendiodemo.web.components.buyer;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.prendiodemo.utils.ElementsUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class SpecificationsSection extends AbstractUIObject implements ElementsUtil {

    @FindBy(xpath = "..//input[@id='txtmaterialname']")
    private List<ExtendedWebElement> materials;

    @FindBy(xpath = "//input[@id='txtmaterialname']/following::input[@id='txtmaterialvalue']")
    private List<ExtendedWebElement> materialValues;

    @FindBy(className = "dbaddnew_icon")
    private ExtendedWebElement addSpecButton;

    public SpecificationsSection(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public int getSize() {
        return materials.size();
    }

    public List<ImmutablePair<String, String>> getSpecs() {
        return IntStream.range(0, materials.size())
                .filter(i -> !getValue(materials.get(i)).isBlank())
                .mapToObj(i -> ImmutablePair.of(getValue(materials.get(i)), getValue(materialValues.get(i))))
                .collect(Collectors.toList());
    }

    public SpecificationsSection addSpec() {
        addSpecButton.click();
        return new SpecificationsSection(getDriver(), getRootElement());
    }

    public void addRandomSpec() {
        materials.get(0).type(RandomStringUtils.randomAlphabetic(30));
        materialValues.get(0).type(RandomStringUtils.randomAlphabetic(30));
    }
}
