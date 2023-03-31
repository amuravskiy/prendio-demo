package com.solvd.prendiodemo.gui.pages.receiverpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.pages.ReceiverPage;
import com.zebrunner.carina.utils.R;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        File file = new File("sample_slip.pdf");
        try {
            FileUtils.copyURLToFile(new URL(R.CONFIG.get("sample_slip_url")), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileInput.type(file.getAbsolutePath());
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
