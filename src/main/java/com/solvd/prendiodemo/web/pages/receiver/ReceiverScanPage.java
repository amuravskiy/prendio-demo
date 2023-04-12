package com.solvd.prendiodemo.web.pages.receiver;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.web.pages.ReceiverPage;
import com.solvd.prendiodemo.utils.Util;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;

public class ReceiverScanPage extends ReceiverPage {

    private static final String SAMPLE_SLIP_URL = R.CONFIG.get("sample_slip_url");

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
        File file = Util.loadFile(SAMPLE_SLIP_URL, new File("sample_slip.pdf"));
        fileInput.attachFile(file.getAbsolutePath());
    }

    public void clickUpload() {
        uploadButton.click();
    }

    public void assertIconVisible() {
        Assert.assertTrue(uploadedIcon.isVisible(), "Icon of the uploaded file is not visible");
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
