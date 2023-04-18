package com.solvd.prendiodemo.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DropDownOptions extends AbstractUIObject {

    @FindBy(xpath = "//div[@class='input_dialog abs']/ul/li[1]")
    private ExtendedWebElement loadedMarker;

    @FindBy(xpath = "//div[@class='input_dialog abs']/ul/li")
    private List<ExtendedWebElement> options;

    public DropDownOptions(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setUiLoadedMarker(loadedMarker);
        setLoadingStrategy(ElementLoadingStrategy.BY_VISIBILITY);
    }

    public void clickOptionByIndex(int index) {
        options.get(index).click();
    }
}
