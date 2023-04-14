package com.solvd.prendiodemo.web.pages.receiver;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.domain.SlipInfo;
import com.solvd.prendiodemo.utils.DateUtil;
import com.solvd.prendiodemo.web.components.CalendarForm;
import com.solvd.prendiodemo.web.components.ScanItemContainer;
import com.solvd.prendiodemo.web.pages.ReceiverPage;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ReceiverScanMatchPage extends ReceiverPage {

    @FindBy(xpath = "//a[@type='match' and @class='active']")
    private ExtendedWebElement scanMatchActive;

    @FindBy(className = "scanitem")
    private List<ScanItemContainer> scanItemContainers;

    @FindBy(xpath = "//input[@id='porecdatetxt']/following::span[@class='calendar_icon'][1]")
    private ExtendedWebElement receivedDateIcon;

    @FindBy(id = "invoicenotxt")
    private ExtendedWebElement invNumberInput;

    @FindBy(xpath = "//input[@id='invoiceduedatetxt']/following::span[@class='calendar_icon'][1]")
    private ExtendedWebElement indDateIcon;

    @FindBy(id = "merge_total")
    private ExtendedWebElement invAmountInput;

    @FindBy(xpath = "//form[@id='inoiceform']//input[@type='checkbox']")
    private ExtendedWebElement noPoCheckbox;

    @FindBy(id = "selectuser")
    private ExtendedWebElement chooseUserSelect;

    @FindBy(id = "INVOICENext")
    private ExtendedWebElement nextButton;

    @FindBy(id = "ui-datepicker-div")
    private CalendarForm calendarForm;

    public ReceiverScanMatchPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(scanMatchActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public void assertFirstScanItemVisible() {
        scanItemContainers.get(0).assertUIObjectPresent();
    }

    public void checkFirstItem() {
        scanItemContainers.get(0).selectItem();
    }

    public SlipInfo fillSlipInfoRandomly() {
        //TODO: rework
        SlipInfo info = new SlipInfo.SlipInfoBuilder()
                .setRecDate(DateUtil.getCurrentDateScanMatch())
                .setInvoiceNumber(String.valueOf(RandomUtils.nextInt(1, 10_000)))
                .setInvDate(DateUtil.getCurrentDateScanMatch())
                .setInvoiceAmount(String.valueOf(RandomUtils.nextInt(1, 10_000)))
                .setDay(DateUtil.getDayOfTheMonth())
                .build();
        invNumberInput.type(info.getInvoiceNumber());
        selectReceivedDate(info);
        selectInvDate(info);
        invAmountInput.type(info.getInvoiceAmount());
        noPoCheckbox.clickByJs();
        selectByIndex(chooseUserSelect, 1);
        return info;
    }

    private void selectReceivedDate(SlipInfo info) {
        receivedDateIcon.click();
        calendarForm = new CalendarForm(getDriver(), getDriver());
        calendarForm.waitDateToBeVisible();
        calendarForm.clickSpecificDay(info.getDay());
    }

    private void selectInvDate(SlipInfo info) {
        indDateIcon.click();
        calendarForm = new CalendarForm(getDriver(), getDriver());
        calendarForm.waitDateToBeVisible();
        calendarForm.clickSpecificDay(info.getDay());
    }

    public void clickNext() {
        nextButton.click();
    }
}
