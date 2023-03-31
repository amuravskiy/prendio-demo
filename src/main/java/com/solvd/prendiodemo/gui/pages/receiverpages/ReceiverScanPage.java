package com.solvd.prendiodemo.gui.pages.receiverpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.pages.ReceiverPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ReceiverScanPage extends ReceiverPage {

    @FindBy(xpath = "//li//a[text()='Scan']")
    private ExtendedWebElement scanSectionActive;

    @FindBy(xpath = "//input[@type='file']")
    private ExtendedWebElement fileInput;

    @FindBy(id = "uploader_start")
    private ExtendedWebElement uploadButton;

    @FindBy(xpath = "//li[contains(@class,'plupload_file')]")
    private ExtendedWebElement uploadedIcon;

    @FindBy(xpath = "//div[@role='progressbar']")
    private ExtendedWebElement progressBar;

    @FindBy(className = "plupload_total_status")
    private ExtendedWebElement uploadStatus;


    public ReceiverScanPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(scanSectionActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public void addUploadFile() {
        fileInput.type("sample_slip.pdf");
    }

    public void clickUpload() {
        uploadButton.click();
    }

    public boolean isIconVisible() {
        return uploadedIcon.isVisible();
    }

    public boolean isProgressBarVisible() {
        return progressBar.isVisible();
    }

    public boolean isProgressBarDisappeared() {
        return progressBar.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public String getUploadStatusText() {
        return uploadStatus.getText();
    }
}
