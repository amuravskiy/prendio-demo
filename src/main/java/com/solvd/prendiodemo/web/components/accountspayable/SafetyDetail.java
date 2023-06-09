package com.solvd.prendiodemo.web.components.accountspayable;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SafetyDetail extends AbstractUIObject {

    @FindBy(xpath = "..//div[@id='safetydetail']//label/preceding-sibling::input")
    private List<ExtendedWebElement> checkboxes;

    @FindBy(xpath = "..//div[@id='safetydetail']//label")
    private List<ExtendedWebElement> labels;

    @FindBy(xpath = "..//div[@id='safetydetail']//label[1]")
    private ExtendedWebElement loadedMarker;

    public SafetyDetail(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setUiLoadedMarker(loadedMarker);
        setLoadingStrategy(ElementLoadingStrategy.BY_VISIBILITY);
    }

    public void fillRandomValues() {
        if (labels.isEmpty()) {
            throw new RuntimeException("Labels not found");
        }
        labels.stream()
                .filter(label -> RandomUtils.nextBoolean())
                .forEach(ExtendedWebElement::click);
    }

    public String getCheckboxesChecked() {
        return IntStream.range(0, checkboxes.size())
                .filter(i -> checkboxes.get(i).isChecked())
                .mapToObj(i -> labels.get(i).getText())
                .collect(Collectors.joining(", "));
    }
}
