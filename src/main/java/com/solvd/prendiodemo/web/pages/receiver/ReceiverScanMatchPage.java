package com.solvd.prendiodemo.web.pages.receiver;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.domain.SlipInfo;
import com.solvd.prendiodemo.utils.DateUtil;
import com.solvd.prendiodemo.web.components.common.CalendarForm;
import com.solvd.prendiodemo.web.components.receiver.ScanItemContainer;
import com.solvd.prendiodemo.web.components.receiver.SupplierSelectPopup;
import com.solvd.prendiodemo.web.pages.ReceiverPage;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.solvd.prendiodemo.constants.Constants.LOADING_BLOCK_APPEAR_TIMEOUT;

public class ReceiverScanMatchPage extends ReceiverPage {

    @FindBy(xpath = "//a[@type='match' and @class='active']")
    private ExtendedWebElement scanMatchActive;

    @FindBy(className = "scanitem")
    private List<ScanItemContainer> scanItemContainers;

    @FindBy(xpath = "//input[@id='porecdatetxt']/following::span[@class='calendar_icon'][1]")
    private ExtendedWebElement receivedDateIcon;

    @FindBy(id = "invoicenotxt")
    private ExtendedWebElement invoiceNumberInput;

    @FindBy(xpath = "//input[@id='invoiceduedatetxt']/following::span[@class='calendar_icon'][1]")
    private ExtendedWebElement invoiceDateIcon;

    @FindBy(id = "merge_total")
    private ExtendedWebElement invoiceAmountInput;

    @FindBy(xpath = "//form[@id='inoiceform']//input[@type='checkbox']")
    private ExtendedWebElement noPoCheckbox;

    @FindBy(id = "selectuser")
    private ExtendedWebElement chooseUserSelect;

    @FindBy(id = "INVOICENext")
    private ExtendedWebElement nextButton;

    @FindBy(id = "ui-datepicker-div")
    private CalendarForm calendarForm;

    @FindBy(xpath = "//div[@id='popupform' and //div[@class='message_information']]")
    private SupplierSelectPopup supplierSelectPopup;

    public ReceiverScanMatchPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(scanMatchActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public boolean isFirstScanItemVisible() {
        return scanItemContainers.get(0).isUIObjectPresent();
    }

    public void checkFirstItem() {
        scanItemContainers.get(0).selectItem();
    }

    public SlipInfo fillSlipInfoRandomly() {
        SlipInfo info = SlipInfo.builder()
                .receivedDate(DateUtil.getCurrentDateScanMatch())
                .invoiceNumber(String.valueOf(RandomUtils.nextInt(1, 10_000)))
                .invoiceDate(DateUtil.getCurrentDateScanMatch())
                .invoiceAmount(String.valueOf(RandomUtils.nextInt(1, 10_000)))
                .day(DateUtil.getDayOfTheMonth())
                .build();
        fillSlipInfo(info);
        return info;
    }

    private void fillSlipInfo(SlipInfo info) {
        invoiceNumberInput.type(info.getInvoiceNumber());
        selectReceivedDate(info);
        selectInvoiceDueDate(info);
        invoiceAmountInput.type(info.getInvoiceAmount());
        noPoCheckbox.clickByJs();
        selectByIndex(chooseUserSelect, 1);
    }

    private void selectReceivedDate(SlipInfo info) {
        receivedDateIcon.click();
        calendarForm = new CalendarForm(getDriver(), getDriver());
        calendarForm.waitDateToBeVisible();
        calendarForm.clickSpecificDayButton(info.getDay());
    }

    private void selectInvoiceDueDate(SlipInfo info) {
        invoiceDateIcon.click();
        calendarForm = new CalendarForm(getDriver(), getDriver());
        calendarForm.waitDateToBeVisible();
        calendarForm.clickSpecificDayButton(info.getDay());
    }

    public boolean isSupplierSelectPopupVisible() {
        return supplierSelectPopup.getRootExtendedElement().isVisible(LOADING_BLOCK_APPEAR_TIMEOUT);
    }

    public SupplierSelectPopup getSupplierSelectPopup() {
        return supplierSelectPopup;
    }

    public void clickNextButton() {
        nextButton.click();
    }
}
