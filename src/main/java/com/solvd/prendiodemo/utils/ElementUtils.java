package com.solvd.prendiodemo.utils;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;

public interface ElementUtils {

    default String getValue(ExtendedWebElement element) {
        return element.getAttribute("value");
    }
}

