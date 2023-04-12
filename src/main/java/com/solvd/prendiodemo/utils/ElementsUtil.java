package com.solvd.prendiodemo.utils;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public interface ElementsUtil {

    default String getValue(ExtendedWebElement element) {
        return element.getAttribute("value");
    }

    default void fillAddressLinesRandomly(List<ExtendedWebElement> addressLines) {
        if (addressLines.isEmpty()) {
            throw new RuntimeException("Address lines not found");
        }
        addressLines.forEach(line -> line.type(RandomStringUtils.randomAlphabetic(20)));
    }

    default void selectByIndex(ExtendedWebElement element, int index) {
        Select select = new Select(element.getElement());
        select.selectByIndex(index);
    }

    default String getSelectedOptionText(ExtendedWebElement select) {
        return new Select(select.getElement()).getOptions().stream()
                .filter(WebElement::isSelected)
                .findFirst()
                .orElseThrow()
                .getText();
    }

    default List<String> getAddressLines(List<ExtendedWebElement> addressLines) {
        return addressLines.stream().map(this::getValue)
                .collect(Collectors.toList());
    }
}

