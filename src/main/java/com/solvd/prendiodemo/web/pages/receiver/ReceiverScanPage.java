package com.solvd.prendiodemo.web.pages.receiver;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.utils.FileUtil;
import com.solvd.prendiodemo.web.pages.ReceiverPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;

public class ReceiverScanPage extends ReceiverPage {

    private static final String SAMPLE_SLIP_URL = "https://fdotwww.blob.core.windows.net/sitefinity/docs/default-source/programmanagement/productevaluation/ipl/files/sample-packing-slip.pdf";

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
        fileInput.attachFile(FileUtil.loadFileAndGetPath(SAMPLE_SLIP_URL, new File("sample_slip.pdf")));
    }

    public void clickUploadButton() {
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
