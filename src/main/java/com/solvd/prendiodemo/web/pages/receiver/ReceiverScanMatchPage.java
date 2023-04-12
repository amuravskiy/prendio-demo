package com.solvd.prendiodemo.web.pages.receiver;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.domain.SlipInfo;
import com.solvd.prendiodemo.utils.Util;
import com.solvd.prendiodemo.web.pages.ReceiverPage;
import com.zebrunner.carina.utils.R;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ReceiverScanMatchPage extends ReceiverPage {

    private static final int HOURS_OFFSET_FROM_UTC = R.CONFIG.getInt("hours_offset_from_utc");
    private static final DateTimeFormatter DATE_FORMATTER_SHORT = DateTimeFormatter.ofPattern("M/d/yyyy").withZone(ZoneOffset
            .ofHours(HOURS_OFFSET_FROM_UTC));

    @FindBy(xpath = "//a[@type='match' and @class='active']")
    private ExtendedWebElement scanMatchActive;

    @FindBy(className = "scanitem")
    private ExtendedWebElement firstScanItemContainer;

    @FindBy(className = "divselected")
    private ExtendedWebElement firstItemCheck;

    @FindBy(xpath = "//input[@id='porecdatetxt']/following::span[@class='calendar_icon'][1]")
    private ExtendedWebElement receivedDateIcon;

    @FindBy(id = "invoicenotxt")
    private ExtendedWebElement invNumverInput;

    @FindBy(xpath = "//input[@id='invoiceduedatetxt']/following::span[@class='calendar_icon'][1]")
    private ExtendedWebElement indDateIcon;

    @FindBy(id = "merge_total")
    private ExtendedWebElement invAmountInput;

    @FindBy(xpath = "//form[@id='inoiceform']//input[@type='checkbox']")
    private ExtendedWebElement noPOCheckbox;

    @FindBy(id = "selectuser")
    private ExtendedWebElement chooseUserSelect;

    @FindBy(id = "INVOICENext")
    private ExtendedWebElement nextButton;

    public ReceiverScanMatchPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(scanMatchActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public void assertFirstScanItemVisible() {
        Assert.assertTrue(firstScanItemContainer.isVisible(), "First scan item is not visible");
    }

    public void checkFirstItem() {
        firstItemCheck.click();
    }

    public SlipInfo fillSlipInfoRandomly() {
        //TODO: rework
        String currentDateFormatted = DATE_FORMATTER_SHORT.format(Instant.now());
        SlipInfo info = new SlipInfo.SlipInfoBuilder()
                .setRecDate(currentDateFormatted)
                .setInvoiceNumber(String.valueOf(RandomUtils.nextInt(1, 10_000)))
                .setInvDate(currentDateFormatted)
                .setInvoiceAmount(String.valueOf(RandomUtils.nextInt(1, 10_000)))
                .setDay(String.valueOf(Instant.now().atOffset(ZoneOffset.ofHours(HOURS_OFFSET_FROM_UTC)).getDayOfMonth()))
                .build();
        By dayLocator = By.xpath("//a[text()='" + info.getDay() + "']");
        receivedDateIcon.click();
        ExtendedWebElement dayToClickOn = findExtendedWebElement(dayLocator);
        waitUntil(ExpectedConditions.elementToBeClickable(dayToClickOn.getElement()), EXPLICIT_TIMEOUT);
        waitToBeClickable(dayToClickOn);
        dayToClickOn.click();
        invNumverInput.type(info.getInvoiceNumber());
        indDateIcon.click();
        dayToClickOn = findExtendedWebElement(dayLocator);
        waitToBeClickable(dayToClickOn);
        dayToClickOn.click();
        dayToClickOn.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
        invAmountInput.type(info.getInvoiceAmount());
        noPOCheckbox.clickByJs();
        Util.selectByIndex(chooseUserSelect, 1);
        return info;
    }

    public void clickNext() {
        nextButton.click();
    }
}
