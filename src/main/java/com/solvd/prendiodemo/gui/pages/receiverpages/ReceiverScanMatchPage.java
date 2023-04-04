package com.solvd.prendiodemo.gui.pages.receiverpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.utils.Util;
import com.solvd.prendiodemo.gui.pages.ReceiverPage;
import com.solvd.prendiodemo.values.SlipInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ReceiverScanMatchPage extends ReceiverPage {

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

    @FindBy(xpath = "//div[h2[text()='CONFIRMATION']]")
    private SupplierSelectPopup supplierSelectPopup;

    public ReceiverScanMatchPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(scanMatchActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public boolean isScanItemVisible() {
        return firstScanItemContainer.isVisible();
    }

    public void checkFirstItem() {
        firstItemCheck.click();
    }

    public void fillSlipInfo(SlipInfo slipInfo) {
        By dayLocator = By.xpath("//a[text()='" + slipInfo.getDay() + "']");
        receivedDateIcon.click();
        ExtendedWebElement dayToClickOn = findExtendedWebElement(dayLocator);
        dayToClickOn.isClickable();
        dayToClickOn.click();
        invNumverInput.type(slipInfo.getInvoiceNumber());
        indDateIcon.click();
        dayToClickOn = findExtendedWebElement(dayLocator);
        dayToClickOn.isClickable();
        dayToClickOn.click();
        dayToClickOn.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
        invAmountInput.type(slipInfo.getInvoiceAmount());
        noPOCheckbox.clickByJs();
        Util.selectByIndex(chooseUserSelect, 1);
    }

    public SupplierSelectPopup clickNextButton() {
        nextButton.click();
        return supplierSelectPopup;
    }
}
